package com.example.SantiagoAndrade_Inventarios.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Product {
	@Id
	private Integer id;
	private String cod;
	private String name;
	private BigDecimal price;
	private Integer stock;

	@ManyToMany(mappedBy = "products")
	Set<Store> stores;

	@OneToMany(mappedBy = "product")
	Set<ProductOrder> productOrders;


	public Product() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void addStock(Integer stock) {
		this.stock += stock;
	}

}