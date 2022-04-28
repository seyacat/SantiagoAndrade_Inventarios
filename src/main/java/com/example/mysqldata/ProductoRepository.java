package com.example.mysqldata;

import org.springframework.data.repository.CrudRepository;

import com.example.mysqldata.Producto;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductoRepository extends CrudRepository<Producto, Integer> {

}