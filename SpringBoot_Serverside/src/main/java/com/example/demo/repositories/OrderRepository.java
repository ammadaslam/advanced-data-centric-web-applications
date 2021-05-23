package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Order;
import com.example.demo.models.Salesperson;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	
	List<Order> findByOrderSalesperson(Salesperson salesperson);
	
	@Query(value = "select * FROM ordertable WHERE order_date like %:order_date% AND order_quantity=:order_quantity", nativeQuery = true)
	public Iterable<Order> findByOrderDateAndOrderQuantityContaining(int order_date, int order_quantity );
	
	
}
