package com.example.SantiagoAndrade_Inventarios.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ProductOrder {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private Integer qty;
  
  @ManyToOne
  private Order order;
  
  @ManyToOne
  private Product product;
  
  @ManyToOne()
  private Store store;
  
  public ProductOrder() {

  }
  
  public Product getProduct() {
	  return product;
  }
  
  public ProductOrder(Integer qty, Product product, Store store ,Order order ) {
	  this.qty = qty;
	  this.product = product;
	  this.order = order; 
	  this.store = store;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

}