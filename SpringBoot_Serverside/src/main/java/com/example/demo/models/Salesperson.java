package com.example.demo.models;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.example.demo.validations.ProductPOSTValidations;
import com.example.demo.validations.SalespersonPOSTValidations;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity (name="salesperson")
public class Salesperson {
	@Id		
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	@NotNull(message = "spid must be specified", groups = SalespersonPOSTValidations.class)
	@Column (unique = true)
	private String spid;
	@NotNull(message = "name must be specified", groups = SalespersonPOSTValidations.class)
	private String name;
	@OneToMany(mappedBy = "orderSalesperson")
	@JsonIgnore
	private List<Order> salespersonOrders;
	
	
	
	public Salesperson(String spid, String name) {
		super();
		this.spid = spid;
		this.name = name;
	}
	
	public Salesperson() {
		super();
	}
	public Salesperson(String name) {
		super();
		this.name = name;
	}
	
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Order> getSalespersonOrders() {
		return salespersonOrders;
	}

	public void setSalespersonOrders(List<Order> salespersonOrders) {
		this.salespersonOrders = salespersonOrders;
	}
		
	
}
