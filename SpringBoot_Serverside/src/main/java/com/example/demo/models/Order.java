package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.example.demo.validations.OrderPOSTValidations;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "ordertable")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	@NotNull(message = "oid must be specified", groups = OrderPOSTValidations.class)
	private String oid;
	private String orderDate;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "spid_FK")
	// @Null(message = "Salesperson must no be specified", groups =
	// OrderPOSTValidations.class)
	private Salesperson orderSalesperson;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "pid_FK")
	@NotNull(message = "orderproduct.o.orderproduct: order product must be provided", groups = OrderPOSTValidations.class)
	private Product orderProduct;
	private int orderQuantity;

	public Order() {
		super();
	}

	public Order(String orderDate) {
		super();
		this.orderDate = orderDate;
	}

	public Order(String orderDate, Salesperson orderSalesperson) {
		super();
		this.orderDate = orderDate;
		this.orderSalesperson = orderSalesperson;
	}

	public Order(String orderDate, Salesperson orderSalesperson, Product orderProduct) {
		super();
		this.orderDate = orderDate;
		this.orderSalesperson = orderSalesperson;
		this.orderProduct = orderProduct;
	}

	public Order(String oid, String orderDate, Salesperson orderSalesperson) {
		super();
		this.oid = oid;
		this.orderDate = orderDate;
		this.orderSalesperson = orderSalesperson;
	}

	public Order(String oid, String orderDate, Salesperson orderSalesperson, Product orderProduct) {
		super();
		this.oid = oid;
		this.orderDate = orderDate;
		this.orderSalesperson = orderSalesperson;
		this.orderProduct = orderProduct;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Salesperson getOrderSalesperson() {
		return orderSalesperson;
	}

	public void setOrderSalesperson(Salesperson orderSalesperson) {
		this.orderSalesperson = orderSalesperson;
	}

	public Product getorderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(Product orderProduct) {
		this.orderProduct = orderProduct;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
}
