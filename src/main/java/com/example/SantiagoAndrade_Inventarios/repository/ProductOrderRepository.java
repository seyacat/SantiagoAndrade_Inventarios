package com.example.SantiagoAndrade_Inventarios.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.SantiagoAndrade_Inventarios.model.ProductOrder;

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Integer> {

}