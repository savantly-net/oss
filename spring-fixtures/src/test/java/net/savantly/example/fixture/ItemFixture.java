package net.savantly.example.fixture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.savantly.example.entity.Item;
import net.savantly.example.repository.ItemRepository;
import net.savantly.spring.fixture.AbstractBaseFixture;
import net.savantly.spring.fixture.Fixture;
import static net.savantly.spring.fixture.util.RandomGenerator.*;

@Service
public class ItemFixture extends AbstractBaseFixture<Item, ItemRepository>{

	@Autowired
	public ItemFixture(ItemRepository repository) {
		super(repository);
	}

	@Override
	public void addEntities(List<Item> entityList) {
		log.info("Adding Item Entities to Fixture");
		for (int i = 0; i < 20; i++) {
			Item item = new Item();
			item.setDescription(getRandomAlphaWordString(20, 10));
			item.setPrice(getRandomMoneyValue(8000, 2, false));
			entityList.add(item);
			log.debug(String.format("Entity added: %s", item));
		}
	}

	@Override
	public void addDependencies(List<Fixture<?>> dependencies) {
		// There are no fixture dependencies
	}

}
