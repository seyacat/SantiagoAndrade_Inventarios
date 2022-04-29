package com.example.SantiagoAndrade_Inventarios.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity // This tells Hibernate to make a table out of this class
public class Store {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private String name;
  
  @ManyToMany
  Set<Product> products;
  
  public Set<Product> getProducts() {
	  return products;
  }
  
  public Store() {
	 
  }
  
  public Store(Integer id, String name) {
		 this.id = id;
		 this.name = name;
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}