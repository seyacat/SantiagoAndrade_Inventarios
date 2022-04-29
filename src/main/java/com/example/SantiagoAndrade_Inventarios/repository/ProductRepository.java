package com.example.SantiagoAndrade_Inventarios.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.SantiagoAndrade_Inventarios.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}