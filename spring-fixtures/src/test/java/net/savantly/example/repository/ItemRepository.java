package net.savantly.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.savantly.example.entity.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, String>{

}
