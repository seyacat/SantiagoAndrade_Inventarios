package com.example.SantiagoAndrade_Inventarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Controller 
@RequestMapping(path="/product") 
public class ProductController {
  @Autowired         
  private ProductRepository productRepository;
  
  @PostMapping(path="/stock/{id}") // Map ONLY POST Requests
  public @ResponseBody  String updateStock (@PathVariable("id") Integer id, @RequestBody JsonNode payload) {
    Integer stock = payload.get("stock").asInt();
    if( stock==null || stock <= 0 ) {
    	return "{\"status\":\"400\"}";
    }
    Product product;
    try {
    	product = productRepository.findById(id).get();
    }catch(Exception e) {
    	return "{\"status\":\"400\"}";
    }
    
    product.setStock(stock);
    productRepository.save(product);
    	return "{\"status\":\"200\"}";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Product> getAllProducts() {
    return productRepository.findAll();
  }
}