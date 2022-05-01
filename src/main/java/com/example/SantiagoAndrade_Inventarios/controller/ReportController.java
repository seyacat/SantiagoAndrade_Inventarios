package com.example.SantiagoAndrade_Inventarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.OrderRepository;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller 
@RequestMapping(path="/report") 
public class ReportController {
  @Autowired         
  private OrderRepository orderRepository;
  
  @GetMapping(path="/orders")
  public @ResponseBody String getOrderCountByStoreDate() throws JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(orderRepository.getOrderCountByStoreDate()) ;
  }
  
  @GetMapping(path="/sells")
  public @ResponseBody JsonNode getCountStoreProduct() throws JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	return mapper.valueToTree(orderRepository.getCountStoreProduct()) ;
    
  }
  
  /*@PostMapping(path="/stock/{id}") // Map ONLY POST Requests
  public @ResponseBody  String updateStock (@PathVariable("id") Integer id, @RequestBody JsonNode payload) {
    Integer stock = payload.get("stock").asInt();
    if( stock==null || stock <= 0 ) {
    	throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid Stock");
    }
    Product product;
    try {
    	product = productRepository.findById(id).get();
    }catch(Exception e) {
    	throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Product not found");
    }
    
    product.setStock(stock);
    productRepository.save(product);
    throw new ResponseStatusException( HttpStatus.OK, "OK");
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Product> getAllProducts() {
    return productRepository.findAll();
  }*/
}