package net.savantly.example.fixture;

import static net.savantly.spring.fixture.util.RandomGenerator.getRandomAddress;
import static net.savantly.spring.fixture.util.RandomGenerator.getRandomAlphaString;
import static net.savantly.spring.fixture.util.RandomGenerator.getRandomNumericString;
import static net.savantly.spring.fixture.util.RandomGenerator.getRandomString;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.savantly.example.entity.Customer;
import net.savantly.example.repository.CustomerRepository;
import net.savantly.spring.fixture.AbstractBaseFixture;
import net.savantly.spring.fixture.Fixture;

@Service
public class CustomerFixture extends AbstractBaseFixture<Customer, CustomerRepository>{

	@Autowired
	public CustomerFixture(CustomerRepository repository) {
		super(repository);
	}
	
	@Override
	public void addEntities(List<Customer> entityList) {
		log.info("Adding Entities to Fixture");
		for (int i = 0; i < 20; i++) {
			Customer item = new Customer();
			
			item.setAddress(getRandomAddress());
			item.setCity(getRandomString("Stephenville", "Keller", "Hot Springs", "Watauga"));
			item.setFirstName(getRandomAlphaString(10));
			item.setLastName(getRandomAlphaString(10));
			item.setState(getRandomAlphaString(2));
			item.setZipcode(getRandomNumericString(5));
			
			entityList.add(item);
			log.debug(String.format("Entity added: %s", item));
		}
	}

	@Override
	public void addDependencies(List<Fixture<?>> dependencies) {
		// This fixture doesn't depend on other fixtures
	}


}
