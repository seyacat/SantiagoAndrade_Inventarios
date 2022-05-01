package com.example.SantiagoAndrade_Inventarios.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity 
public class Store {
  @Id
  private Integer id;
  private String name;
  private String cod;
  
  @ManyToMany
  Set<Product> products;
  
  public Set<Product> getProducts() {
	  return products;
  }
  
  public Store() {
	 
  }
  
  public Store(Integer id, String cod, String name) {
		 this.id = id;
		 this.cod = cod;
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
  public String getCod() {
	    return cod;
  }

  public void setCod(String cod) {
    this.cod = cod;
  }

}