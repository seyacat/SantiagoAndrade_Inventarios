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
@RequestMapping(path="/") 
public class HomeController {
	
  @GetMapping
  public @ResponseBody String getHome() {
    return "<!DOCTYPE html>\r\n"
    		+ "<html>\r\n"
    		+ "  <head>\r\n"
    		+ "    <meta http-equiv=\"refresh\" content=\"0; url='/swagger-ui/index.html'\" />\r\n"
    		+ "  </head>\r\n"
    		+ "  <body>\r\n"
    		+ "    <p>Please follow <a href=\"/swagger-ui/index.html\">this link</a>.</p>\r\n"
    		+ "  </body>\r\n"
    		+ "</html>";
  }
  
}
