package com.example.SantiagoAndrade_Inventarios.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity // This tells Hibernate to make a table out of this class
public class Product {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private String cod;
  private String name;
  private BigDecimal price;
  private Integer stock;
  
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

  public void setName(Integer stock) {
    this.stock = stock;
  }
}