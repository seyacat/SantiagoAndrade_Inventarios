package com.example.SantiagoAndrade_Inventarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;


@Controller 
@RequestMapping(path="/product") 
public class ProductController {
		
  @Autowired         
  private ProductRepository productRepository;
  
  @PostMapping(path="/stock/{id}") // Map ONLY POST Requests
  
  public @ResponseBody  String updateStock (@PathVariable("id") Integer id, @RequestBody Stock payload) {
    Integer stock = payload.stock;
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
  }
}

class Stock{
	public Integer stock;
}