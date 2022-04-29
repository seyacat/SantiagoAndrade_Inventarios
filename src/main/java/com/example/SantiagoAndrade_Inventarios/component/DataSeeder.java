package com.example.SantiagoAndrade_Inventarios.component;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.SantiagoAndrade_Inventarios.SantiagoAndradeInventariosApplication;
import com.example.SantiagoAndrade_Inventarios.model.Product;
import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PostInit {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ProductRepository productRepository;
	
	private static final Logger log = LoggerFactory.getLogger(SantiagoAndradeInventariosApplication.class);
	public PostInit() {
		System.out.println("PostContructImpl Constructor called");
	}
	@PostConstruct
	public void runAfterObjectCreated() throws JsonMappingException, JsonProcessingException {
		System.out.println("PostContruct method called");
		//ProductRepository productRepository;
		ResponseEntity<String> prodsresponse = restTemplate.getForEntity(
				"https://mocki.io/v1/efe8471e-7c67-48f0-b3fb-6e2b339e2da6", String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(prodsresponse.getBody());
		
		JsonNode prods = root.path("prods");
		log.info (prodsresponse.getBody());
		log.info (root.toString());
		log.info (prods.toString());
		
		Iterator<JsonNode> it = prods.elements();
	    while (it.hasNext()) {
	    	Product prod = mapper.readValue(it.next().toString(),Product.class);
	    	//productRepository.save(prod);
	    	log.info (prod.toString());
	    	log.info (it.next().toString());
	    	productRepository.save(prod);
	    }
		
		/*ResponseEntity<String> responseEntity =
   		restTemplate.getForEntity("https://mocki.io/v1/efe8471e-7c67-48f0-b3fb-6e2b339e2da6", 
   				String.class);
		String jsonstring = responseEntity.getBody();
		
		Products prods = new Products();
		
		prods.setProducts(jsonstring);*/
	}

}