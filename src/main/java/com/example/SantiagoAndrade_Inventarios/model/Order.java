package com.example.SantiagoAndrade_Inventarios.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="order_")
public class Order {
	
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  
  @ManyToOne()
  private Client client;
  
  
  @OneToMany(mappedBy = "order")
  Set<ProductOrder> productOrders;
  
  private Date date;
  
  public Order(Client client) {
	 this.client = client;
	 this.date = new Date();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }


}