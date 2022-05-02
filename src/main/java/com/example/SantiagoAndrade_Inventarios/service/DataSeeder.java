package com.example.SantiagoAndrade_Inventarios.service;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.SantiagoAndrade_Inventarios.model.Client;
import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.model.Store;
import com.example.SantiagoAndrade_Inventarios.repository.ClientRepository;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.example.SantiagoAndrade_Inventarios.repository.StoreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataSeeder {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;

	public DataSeeder() {
		System.out.println("PostContructImpl Constructor called");
	}

	@PostConstruct
	public void runAfterObjectCreated() throws JsonMappingException, JsonProcessingException {

		// GENERATE CLIENTS

		if (clientRepository.findByCi("1700000000").size() == 0) {
			clientRepository.save(new Client("Juan Perez", "1700000000"));
		}

		// GENERATE STORES
		storeRepository.save(new Store(1, "T001", "Don Pepe"));
		storeRepository.save(new Store(2, "T002", "Mi Tiendita Cool"));
		storeRepository.save(new Store(3, "T003", "Todo a Dolar"));
		storeRepository.save(new Store(4, "T004", "El Oferton"));

		// RETRIVE PRODUCTS FROM EXTERNAL MOCH.IO
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> prodsresponse = restTemplate
				.getForEntity("https://mocki.io/v1/efe8471e-7c67-48f0-b3fb-6e2b339e2da6", String.class);

		JsonNode prodsRoot = mapper.readTree(prodsresponse.getBody());

		JsonNode prodsJson = prodsRoot.path("prods");

		Iterator<JsonNode> prodsit = prodsJson.elements();
		while (prodsit.hasNext()) {
			JsonNode prodJson = prodsit.next();
			Product prod = mapper.readValue(prodJson.toString(), Product.class);
			productRepository.save(prod);
		}

		// ASSIGN RELATIONS
		Store store1 = storeRepository.findById(1).get();
		Store store2 = storeRepository.findById(2).get();
		Store store3 = storeRepository.findById(3).get();

		Product product1 = productRepository.findById(1).get();
		Product product2 = productRepository.findById(2).get();
		Product product3 = productRepository.findById(3).get();
		Product product4 = productRepository.findById(4).get();
		Product product5 = productRepository.findById(5).get();
		Product product6 = productRepository.findById(6).get();

		store1.getProducts().add(product1);
		store1.getProducts().add(product2);
		store1.getProducts().add(product3);
		store1.getProducts().add(product4);
		store1.getProducts().add(product5);
		store1.getProducts().add(product6);

		store2.getProducts().add(product1);
		store2.getProducts().add(product3);
		store2.getProducts().add(product5);

		store3.getProducts().add(product1);

		storeRepository.save(store1);
		storeRepository.save(store2);
		storeRepository.save(store3);

	}
}