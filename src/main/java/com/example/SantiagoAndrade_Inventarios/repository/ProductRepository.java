package com.example.SantiagoAndrade_Inventarios.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.SantiagoAndrade_Inventarios.model.Product;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {

}