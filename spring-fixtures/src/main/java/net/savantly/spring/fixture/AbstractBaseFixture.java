package net.savantly.spring.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

public abstract class AbstractBaseFixture<T, R extends CrudRepository<T, ?>> implements Fixture<T>{

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private boolean installed = false;
	private Object lock = new Object();
	private List<T> entityList = new ArrayList<T>();
	private List<Fixture<T>> dependencies = new ArrayList<Fixture<T>>();
	private Random random = new Random();
	private R repository;
	
	public abstract void addEntities(List<T> entityList);
	public abstract void addDependencies(List<Fixture<T>> dependencies);
	
	public AbstractBaseFixture(R repository){
		this.repository = repository;
	}

	public void install() {
		log.info("Beginning Fixture Install");
		synchronized (lock) {
			ensureDependenciesAreInstalled();
			addEntities(entityList);
			repository.save(entityList);
			installed = true;
		}
		log.info("Finished Fixture Install");
	}

	private void ensureDependenciesAreInstalled() {
		log.info("Beginning Fixture Dependencies Install");
		for (Fixture<T> fixture : dependencies) {
			if (!fixture.isInstalled()) {
				fixture.install();
			}
		}
	}

	public void uninstall() {
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
		int position = random.nextInt(entityList.size());
		return entityList.get(position);
	}
}
