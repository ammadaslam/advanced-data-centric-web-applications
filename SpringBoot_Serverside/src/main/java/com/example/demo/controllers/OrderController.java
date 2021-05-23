package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.demo.exceptions.OrderException;
import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
import com.example.demo.validations.OrderPOSTValidations;

@RestController
@Validated
public class OrderController {

	@Autowired
	private OrderService os;

	@GetMapping("api/orders")
	public Iterable<Order> getAllOrders() {
		return os.getAllOrders();
	}

	@GetMapping("api/specificOrders")
	public Iterable<Order> findSpecificOrder(@RequestParam("year") int orderDate,
			@RequestParam("qty") int orderQuantity, HttpServletResponse response) throws IOException {
		if (orderDate == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required int paramter 'year' is not present");

		} else if (orderQuantity == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required int paramter 'qty' is not present");
		}
		Iterable<Order> order = os.getSpecificOrder(orderDate, orderQuantity);
		return order;

	}

	@Validated(OrderPOSTValidations.class)
	@PostMapping(path = "api/orders")
	public void addOrder(@Valid @RequestBody Order order) {
		// System.out.println(order.getOid()+ ", " +order.getOrderDate());
		try {
			os.addOrder(order);
		} catch (OrderException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

}
