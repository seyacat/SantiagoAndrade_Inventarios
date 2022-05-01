package com.example.SantiagoAndrade_Inventarios.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

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

import com.example.SantiagoAndrade_Inventarios.model.Client;
import com.example.SantiagoAndrade_Inventarios.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller 
@RequestMapping(path="/client") 
public class ClientController {
	@Autowired
	Validator validator;
	
  @Autowired         
  private ClientRepository clientRepository;
  
  @GetMapping(path="/list")
  public @ResponseBody Iterable<Client> getListClients() {
    return clientRepository.findAll();
  }
  
  @GetMapping(path="/id/{id}")
  public @ResponseBody Client getClientById(@PathVariable("id") Integer id) {
	try {
		return clientRepository.findById(id).get();
	}
	catch(Exception e) {
		throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Client not found",e);
	}
  }
  @GetMapping(path="/ci/{ci}")
  public @ResponseBody Client getClientByCi(@PathVariable("ci") String ci) {
	List<Client> clients = clientRepository.findByCi(ci);
	if( clients.size() > 0 ) {
		return clients.get(0);
	}
	throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Client not found");
  }
  
  @PostMapping(path="/create") 
  public @ResponseBody  Client createClient ( @Valid @RequestBody NewClient newclient ) throws JsonMappingException, JsonProcessingException {
	//ObjectMapper mapper = new ObjectMapper();
	/*Client client;
	try {
		client = mapper.readValue(payload.toString(),Client.class);
	}catch(Exception e) {
		throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Validation Fail",e);
	}*/
	Client client = new Client( newclient.ci,newclient.name,newclient.photo );
	Set<ConstraintViolation<Client>> violations = validator.validate(client);
	if (!violations.isEmpty()) {
		throw new ResponseStatusException( HttpStatus.BAD_REQUEST, violations.toString());
	  //throw new ConstraintViolationException(violations);
	}
	if(clientRepository.findByCi(client.getCi()).size() > 0){
		throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Client Duplicated");
	}
	clientRepository.save(client);
	return client;
  }
  

  @PostMapping(path="/update/{id}") 
  public @ResponseBody  Client updateClient ( @PathVariable("id") Integer id, @RequestBody UpdateClient newclient) throws JsonMappingException, JsonProcessingException {
	  Client client;
	  try {
		  client = clientRepository.findById(id).get();
	  }catch(Exception e) {
		  throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Client not found");
	  }
	  String photo =  newclient.photo;
	  if( photo != null ) {
		  client.setPhoto(photo);
	  }
	  clientRepository.save(client);
	  throw new ResponseStatusException( HttpStatus.OK, "OK");
  }
  
  @GetMapping(path="/delete/{id}") 
  public @ResponseBody  void deleteClient (@PathVariable("id") Integer id )  {
	 Client client = clientRepository.findById(id).get();
	 client.setDeletedAt( new Date() );
	 clientRepository.save(client);
	 throw new ResponseStatusException( HttpStatus.OK, "OK");
  }

}

class NewClient{
	public String ci;
	public String name;
	public String photo;
}
class UpdateClient{
	public String photo;
}