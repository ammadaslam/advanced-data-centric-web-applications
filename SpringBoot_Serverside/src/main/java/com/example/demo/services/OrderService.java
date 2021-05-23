package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.exceptions.OrderException;
import com.example.demo.models.Order;
import com.example.demo.models.Product;
import com.example.demo.models.Salesperson;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.SalespersonRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

	@Autowired
	private SalespersonRepository sr;
	@Autowired
	private OrderRepository or;
	@Autowired
	private ProductRepository pr;

	public Iterable<Order> getAllOrders() {
		return or.findAll();
	}

	public Iterable<Order> getSpecificOrder(int orderDate, int orderQuantity) {
		Iterable<Order> order = or.findByOrderDateAndOrderQuantityContaining(orderDate, orderQuantity);
		return order;
	}

	public void save(Order order) throws OrderException {
		long numOrder = or.count();
		if (numOrder >= 7) {
			or.save(order);
		} else {
			throw new OrderException("Stock on hand: " + numOrder + " is less than order quantity: = 7");
		}
	}

	public Order addOrder(Order order) throws OrderException {
		String salespersionSPID = order.getOrderSalesperson().getSpid();
		if (salespersionSPID == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"salesperson.spid: spid must be provided");
		}
		String productPID = order.getorderProduct().getPid();
		Optional<Product> productPidRepo = pr.findAllByPid(productPID);
		if (!productPidRepo.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Product: " + productPID + " does not exist");
		}
		int pQty = productPidRepo.get().getQuantity();
		int oQty = order.getOrderQuantity();
		if (pQty < oQty) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Stock on hand: " + pQty + " is less than order quantity: " + oQty);
		}
		Optional<Salesperson> salespersonSpidRepo = sr.findAllBySpid(salespersionSPID);
		if (salespersonSpidRepo.isPresent()) {
			return or.save(order);
		} else {
			throw new OrderException("Salesperson: " + salespersionSPID + "does not exist");

		}
	}

}