package net.savantly.example.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.savantly.example.config.SpringConfiguration;
import net.savantly.spring.fixture.Fixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfiguration.class })
public abstract class AbstractFixtureTest<T, F extends Fixture<T>, R extends CrudRepository<T, ?>> {

	@Autowired
	F itemFixture;
	@Autowired
	R repository;

	@Test
	public void test() {
		itemFixture.install();
		ArrayList<T> expecteds = iteratorToArray(repository.findAll());
		
		Assert.assertTrue("The DB does not contain all the items in the fixture", expecteds.containsAll(itemFixture.getEntityList()));

		itemFixture.uninstall();

		Assert.assertTrue("The Item fixture is not empty", itemFixture.getEntityList().isEmpty());

		expecteds.clear();
		expecteds.addAll(iteratorToArray(repository.findAll()));
		Assert.assertTrue("The Items were not removed from the DB", expecteds.isEmpty());
	}

	private ArrayList<T> iteratorToArray(Iterable<T> findAll) {
		ArrayList<T> expecteds = new ArrayList<T>();
		for (T item : repository.findAll()) {
			expecteds.add(item);
		}
		return expecteds;
	}

}
