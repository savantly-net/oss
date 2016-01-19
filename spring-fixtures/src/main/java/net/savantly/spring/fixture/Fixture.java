package net.savantly.spring.fixture;

import java.util.List;

public interface Fixture<T> {

	void install();
	void uninstall();
	boolean isInstalled();
	T getRandomEntity();
	List<T> getEntityList();
}
