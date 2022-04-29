package com.example.SantiagoAndrade_Inventarios.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Controller 
@RequestMapping(path="/product") 
public class ProductController {
  @Autowired         
  private ProductRepository productRepository;
  
  @PutMapping(path="/stock/{id}") // Map ONLY POST Requests
  public @ResponseBody  Integer updateStock (@PathVariable("id") Integer id, @RequestBody JsonNode payload) {
    Integer stock = payload.get("stock").asInt();
    Product product = productRepository.findById(id).get();
    product.setStock(stock);
    productRepository.save(product);
    return 1;
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Product> getAllProducts() {
    return productRepository.findAll();
  }
}