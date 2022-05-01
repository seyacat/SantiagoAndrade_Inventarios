package com.example.SantiagoAndrade_Inventarios.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockService {
		
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	@Async
	public void RequestStockAsync(Integer id, Boolean largeStock) throws JsonMappingException, JsonProcessingException {
		RequestStock( id, largeStock );
	}
	
	public void RequestStock (Integer id, Boolean largeStock) throws JsonMappingException, JsonProcessingException {
	  String uri ;
	  if(largeStock) {
		  uri = "https://mocki.io/v1/302a4d0c-3bf9-4faa-a267-37ed878667a8";
	  }else {
		  uri = "https://mocki.io/v1/f8947dc7-889b-437e-89b7-fbaa3ea2d488";
	  }
	  ResponseEntity<String> stockresponse = restTemplate.getForEntity(uri, String.class);
	  ObjectMapper mapper = new ObjectMapper();	
	  JsonNode stockJson = mapper.readTree(stockresponse.getBody());
	  Integer newStock = stockJson.get("stock").asInt();
	  
	  Product product = productRepository.findById(id).get();
	  
	  product.addStock(newStock);
	  productRepository.save(product);
		  
  }
	
}