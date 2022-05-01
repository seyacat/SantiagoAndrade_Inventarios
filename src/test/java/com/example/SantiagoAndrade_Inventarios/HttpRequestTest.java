package com.example.SantiagoAndrade_Inventarios;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.example.SantiagoAndrade_Inventarios.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	private static final Logger log = LoggerFactory.getLogger(SantiagoAndradeInventariosApplicationTests.class);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		String result = this.restTemplate.getForObject("http://localhost:" + port + "/product/all",String.class);
		assertThat(result).contains("id");
	}
	
	
	@Test
    public void testStockChange() throws URISyntaxException  
    {
		
		//TEST OK PRODUCT 3
		assertThat(postRequest("http://localhost:" + port +"/product/stock/3","{\"stock\":10}").get("status").asInt())
		.isEqualTo(200);
		//TEST INVALID OR NON PRODUCT
		assertThat(postRequest("http://localhost:" + port +"/product/stock/-50","{\"stock\":10}").get("status").asInt())
		.isEqualTo(400);
		//TEST STOCK LESS OR SAME THAN 0
		assertThat(postRequest("http://localhost:" + port +"/product/stock/2","{\"stock\":0}").get("status").asInt())
		.isEqualTo(400);
		assertThat(postRequest("http://localhost:" + port +"/product/stock/2","{\"stock\":-14}").get("status").asInt())
		.isEqualTo(400);
		
    }
	 
    @Test
    public void testListClients( ) throws URISyntaxException 
    {
    	String uri = "http://localhost:" + port + "/client/list";
        assertThat(getRequest(uri).size()).isGreaterThan(0);        
    }
    
    @SuppressWarnings("unused")
	@Test
    public void testCrudClient( ) throws URISyntaxException 
    {
    	assertThat(getRequest("http://localhost:" + port + "/client/id/1").get("id").asInt()).isEqualTo(1);
    	//CLIENT NOT EXIST
    	assertThat(getRequest("http://localhost:" + port + "/client/id/-1").get("status").asInt()).isEqualTo(404);
    	//CREATE CLIENT
    	//MISSING CI
    	assertThat(postRequest("http://localhost:" + port + "/client/create","{\"name\":\"Pedro\"}").get("status").asInt()).isEqualTo(400);
    	//MISSING NAME
    	assertThat(postRequest("http://localhost:" + port + "/client/create","{\"ci\":\"1700000001\"}").get("status").asInt()).isEqualTo(400);
    	//CREATE FOR UPDDATE
    	JsonNode idNode = getRequest("http://localhost:" + port + "/client/ci/1700000001").get("id");
    	if( idNode != null ) {
    		//TEST DELETE
    		assertThat(getRequest("http://localhost:" + port + "/client/delete/"+idNode.asInt()).get("status").asInt()).isEqualTo(200);
    	} 
    	//TEST CREATE
    	Integer id = postRequest("http://localhost:" + port + "/client/create","{\"name\":\"Pedro\",\"ci\":\"1700000001\"}").get("id").asInt();
    	assertThat(id).isNotNull();
    	//TEST UPDATE
    	assertThat(postRequest("http://localhost:" + port + "/client/update/"+id,"{\"name\":\"Pedro\",\"photo\":\"/photo/path/01.jpg\"}").get("status").asInt()).isEqualTo(200);
    }
    
    
    @Test
    public void testOrder( ) throws URISyntaxException 
    {
    	//SET STOCKS TO 10 PRODUCT 1,2
    	assertThat(postRequest("http://localhost:" + port +"/product/stock/1","{\"stock\":10}").get("status").asInt())
		.isEqualTo(200);
    	assertThat(postRequest("http://localhost:" + port +"/product/stock/2","{\"stock\":10}").get("status").asInt())
		.isEqualTo(200);
    	
    	//ONE PRODUCT ORDER
    	assertThat(postRequest("http://localhost:" + port + "/order/create",
    			"{\"client_id\":1,\"stores\":[{\"store_id\":1,\"products\":[{\"id\":1,\"qty\":8}]}]}",
    			true)
    			.get("status")
    			.asInt())
    	.isEqualTo(200);
    	
    	//MULTI PRODUCT STORE ORDER
    	assertThat(postRequest("http://localhost:" + port + "/order/create",
    			"{\"client_id\":1,\"stores\":[{\"store_id\":1,\"products\":[{\"id\":1,\"qty\":2}]},{\"store_id\":2,\"products\":[{\"id\":1,\"qty\":2}]}]}",
    			true)
    			.get("status")
    			.asInt())
    	.isEqualTo(200);
    	
    	//SET STOCKS TO 10 PRODUCT 1
    	assertThat(postRequest("http://localhost:" + port +"/product/stock/1","{\"stock\":10}").get("status").asInt())
		.isEqualTo(200);
    	assertThat(productRepository.findById(1).get().getStock()).isEqualTo(10);
    	//ASYNC REQUEST TEST
    	assertThat(postRequest("http://localhost:" + port + "/order/create",
    			"{\"client_id\":1,\"stores\":[{\"store_id\":1,\"products\":[{\"id\":1,\"qty\":13}]}]}",
    			true)
    			.get("status")
    			.asInt())
    	.isEqualTo(200);
    	assertThat(productRepository.findById(1).get().getStock()).isEqualTo(-3);
    	
    	//SET STOCKS TO 8 PRODUCT 2
    	assertThat(postRequest("http://localhost:" + port +"/product/stock/2","{\"stock\":8}").get("status").asInt())
		.isEqualTo(200);
    	assertThat(productRepository.findById(2).get().getStock()).isEqualTo(8);
    	//SYNC REQUEST TEST
    	assertThat(postRequest("http://localhost:" + port + "/order/create",
    			"{\"client_id\":1,\"stores\":[{\"store_id\":1,\"products\":[{\"id\":2,\"qty\":15}]}]}",
    			true)
    			.get("status")
    			.asInt())
    	.isEqualTo(200);
    	assertThat(productRepository.findById(2).get().getStock()).isEqualTo(3);
    	
    }
    
    public JsonNode getRequest( String uri ) {
    	return getRequest( uri, false );
    }
    
    public JsonNode getRequest( String uri, Boolean printLog ) {
    	JsonNode result = this.restTemplate.getForObject(uri, JsonNode.class);
    	if(printLog) {
    		log.info(uri);
    		log.info(result.toPrettyString());
    	}
    	return result;
    	
    }
    
    public JsonNode postRequest( String uri, String body ) {
    	return postRequest( uri, body,  false );
    }
    public JsonNode postRequest( String uri, String body, Boolean printLog ) {
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json");      
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        JsonNode result = this.restTemplate.postForObject(uri, request, JsonNode.class);  
        if(printLog) {
        	log.info(uri);
            log.info(body);
            log.info(result.toPrettyString());
    	}       
        return result;
    }
   
	
}