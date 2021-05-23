package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Order;
import com.example.demo.models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	public Optional<Product> findAllByPid(String pid);
}
