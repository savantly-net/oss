package net.savantly.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.savantly.example.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, String>{

}
