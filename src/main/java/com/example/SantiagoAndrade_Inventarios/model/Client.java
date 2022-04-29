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

@Entity 
public class Client {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private String ci;
  private String name;
  private String photo;
  
  public Client() {
	 
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getCi() {
    return ci;
  }

  public void setCi(String ci) {
    this.ci = ci;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

}