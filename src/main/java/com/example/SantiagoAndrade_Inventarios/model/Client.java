package com.example.SantiagoAndrade_Inventarios.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Where(clause = "deleted_at IS NULL")
public class Client {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  @NotBlank(message = "CI is mandatory")
  private String ci;
  @NotBlank(message = "Name is mandatory")
  private String name;
  private String photo;
  @JsonIgnore
  private Date deletedAt;
  
  @OneToMany(mappedBy="client")
  private Set<Order> orders;
  
  public Client() {}

  public Client(String ci, String name, String photo) {
		 this.ci = ci;
		 this.name= name;
		 this.photo=photo;
  }
  
  public Client(String name, String ci ) {
	 this.name = name;
	 this.ci = ci;
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
  
  public Date getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }
  
  public void delete() {
    this.deletedAt = new Date();
  }

}