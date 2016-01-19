#spring-fixtures

Spring-fixtures is a library that makes integration testing easier.
It is also helpful when prototyping and demonstrating an application.

###Fixtures

A fixture can implement the [Fixture](src/main/java/net/savantly/spring/fixture/Fixture.java) interface, or extend the provided [AbstractBaseFixture](src/main/java/net/savantly/spring/fixture/AbstractBaseFixture.java)

The AbstractBaseFixture manages fixture dependencies on other fixtures. Suppose you have an "Order" entity that requires instances of "Item" and "Customer" entities. The "Item" and "Customer" fixtures can be added as dependencies of the "Order" fixture so the installation of the prerequisites are ensured.
An example of this can be found [here](src/test/java/net/savantly/example/fixture/OrderFixture.java)


The AbstractBaseFixture expects an Entity, and a Repository class that extends [CrudReposity](http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)

Examples of fixtures and tests are included 
[src/test/java/](src/test/java/)

###Fixture Tests

An abstract fixture test is available. No implementation is required.
[src/test/java/net/savantly/example/test/AbstractFixtureTest.java](src/test/java/net/savantly/example/test/AbstractFixtureTest.java)

    public class ItemFixtureTest extends AbstractFixtureTest<Item, ItemFixture, ItemRepository> {}


###RandomGenerator
A RandomGenerator class is provided to mock data for your entities

The RandomGenerator can produce varying lengths of strings or paragraphs, and provides other functions like Address generation, and random date generation in the future or past. 




###Example Fixture -


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
    
