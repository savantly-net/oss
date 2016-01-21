package net.savantly.spring.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.repository.CrudRepository;

public abstract class AbstractBaseFixture<T, R extends CrudRepository<T, ?>> implements Fixture<T>, InitializingBean{

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private boolean installed = false;
	private Object lock = new Object();
	private List<T> entityList = new ArrayList<T>();
	private List<Fixture<?>> dependencies = new ArrayList<Fixture<?>>();
	private Random random = new Random();
	private R repository;
	
	public abstract void addEntities(List<T> entityList);
	public abstract void addDependencies(List<Fixture<?>> dependencies);
	
	public AbstractBaseFixture(R repository){
		this.repository = repository;
	}

	public void install() {
		if(installed){
			log.warn("Fixture already installed");
			return;
		}
		log.info("Beginning Fixture Install");
		synchronized (lock) {
			ensureDependenciesAreInstalled();
			addEntities(entityList);
			repository.save(entityList);
			installed = true;
		}
		log.info("Finished Fixture Install");
	}

	public void afterPropertiesSet() throws Exception {
		addDependencies(dependencies);
	}
	
	private void ensureDependenciesAreInstalled() {
		log.info("Beginning Fixture Dependencies Install");
		for (Fixture<?> fixture : this.dependencies) {
			if (!fixture.isInstalled()) {
				fixture.install();
			}
		}
	}

	public void uninstall() {
		if(!installed){
			log.warn("Fixture not installed");
			return;
		}
		log.info("Beginning Fixture Uninstall");
		synchronized (lock) {
			repository.delete(entityList);
			entityList.clear();
			installed = false;
		}
		log.info("Finished Fixture Uninstall");
	}

	public boolean isInstalled() {
		return installed;
	}
	
	public List<T> getEntityList() {
		return entityList;
	}
	
	public T getRandomEntity(){
		if(entityList.size() == 0){
			log.warn(String.format("entityList is empty", entityList));
			return null;
		}
		int position = random.nextInt(entityList.size());
		return entityList.get(position);
	}
}
