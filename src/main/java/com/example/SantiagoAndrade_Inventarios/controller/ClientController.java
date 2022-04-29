package com.example.SantiagoAndrade_Inventarios.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SantiagoAndrade_Inventarios.model.Client;
import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.ClientRepository;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller 
@RequestMapping(path="/client") 
public class ClientController {
  @Autowired         
  private ClientRepository clientRepository;
  @GetMapping(path="/{id}")
  public @ResponseBody Client getClientById(@PathVariable("id") Integer id) {
    return clientRepository.findById(id).get();
  }
  
  @PostMapping(path="/stock") // Map ONLY POST Requests
  public @ResponseBody  Client createStock ( @RequestBody JsonNode payload) throws JsonMappingException, JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	Client client = mapper.readValue(payload.toString(),Client.class);
	clientRepository.save(client);
	return client;
  }

  @PutMapping(path="/stock") // Map ONLY POST Requests
  public @ResponseBody  Client updateStock ( @RequestBody JsonNode payload) throws JsonMappingException, JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	Client client = mapper.readValue(payload.toString(),Client.class);
	clientRepository.save(client);
	return client;
  }
  
  @DeleteMapping(path="/stock/{id}") // Map ONLY POST Requests
  public @ResponseBody  void deleteStock (@PathVariable("id") Integer id )  {
	clientRepository.deleteById(id);
  }

  /*@GetMapping(path="/all")
  public @ResponseBody Iterable<Product> getAllProducts() {
    return productRepository.findAll();
  }*/
}