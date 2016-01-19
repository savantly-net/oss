package net.savantly.example.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity(name="CUSTOMER_ORDER")
public class Order {
	
	private static final Logger log = LoggerFactory.getLogger(Order.class);
	
	@Id
	private String id = UUID.randomUUID().toString();
	@ManyToMany
	@JoinTable(
			joinColumns={@JoinColumn(name="orderId")},
			inverseJoinColumns={@JoinColumn(name="itemId")})
	private Collection<Item> items = new ArrayList<Item>();
	private Customer customer;
	
	public void addItem(Item item){
		if(!items.contains(item)){
			items.add(item);
		}
		else {
			log.warn("Attemped to add the same Item Entity to the collection");
		}
	}
	
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
	
	
}
