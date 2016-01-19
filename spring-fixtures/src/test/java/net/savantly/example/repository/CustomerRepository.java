package net.savantly.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.savantly.example.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, String>{

}
