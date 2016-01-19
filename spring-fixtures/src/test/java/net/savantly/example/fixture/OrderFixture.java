package net.savantly.example.fixture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.savantly.example.entity.Order;
import net.savantly.example.repository.OrderRepository;
import net.savantly.spring.fixture.AbstractBaseFixture;
import net.savantly.spring.fixture.Fixture;

@Service
public class OrderFixture extends AbstractBaseFixture<Order, OrderRepository>{
	
	@Autowired
	ItemFixture itemFixture;
	@Autowired
	CustomerFixture customerFixture;

	@Autowired
	public OrderFixture(OrderRepository repository) {
		super(repository);
	}

	@Override
	public void addEntities(List<Order> entityList) {
		log.info("Adding Entities to Fixture");
		for (int i = 0; i < 20; i++) {
			Order item = new Order();
			
			item.setCustomer(customerFixture.getRandomEntity());
			item.addItem(itemFixture.getRandomEntity());
			item.addItem(itemFixture.getRandomEntity());
			item.addItem(itemFixture.getRandomEntity());
			
			entityList.add(item);
			log.debug(String.format("Entity added: %s", item));
		}
	}

	@Override
	public void addDependencies(List<Fixture<?>> dependencies) {
		dependencies.add(itemFixture);
		dependencies.add(customerFixture);
	}


}
