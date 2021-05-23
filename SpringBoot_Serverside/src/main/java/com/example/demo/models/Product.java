package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.validation.annotation.Validated;

import com.example.demo.validations.OrderPOSTValidations;
import com.example.demo.validations.ProductPOSTValidations;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "pid must be specified", groups = ProductPOSTValidations.class)
	private String pid;
	@NotNull(message = "product must be specified", groups = ProductPOSTValidations.class)
	private String product;
	private int quantity;
	String orderable;
	//@ManyToMany(mappedBy = "orderProduct")
	@OneToMany(mappedBy = "orderProduct")
	private List<Order> productOrders = new ArrayList<Order>();

	public Product() {
		super();
	}
	public Product(String pid, String product, int quantity, String orderable) {
		super();
		this.pid = pid;
		this.product = product;
		this.quantity = quantity;
		this.orderable = orderable;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public List<Order> getProductOrders() {
		return productOrders;
	}
	public void setProductOrders(List<Order> productOrders) {
		this.productOrders = productOrders;
	}
	public String getOrderable() {
		return orderable;
	}
	public void setOrderable(String orderable) {
		this.orderable = orderable;
	}
}
