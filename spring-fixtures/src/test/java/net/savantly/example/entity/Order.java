package net.savantly.example.entity;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="CUSTOMER_ORDER")
public class Order {
	
	@Id
	private String id = UUID.randomUUID().toString();
	@OneToMany
	private Collection<Item> items;
	
	public String getId() {
		return id;
	}
	public Collection<Item> getItems() {
		return items;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setItems(Collection<Item> items) {
		this.items = items;
	}
}
