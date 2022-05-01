package com.example.SantiagoAndrade_Inventarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.SantiagoAndrade_Inventarios.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	
}