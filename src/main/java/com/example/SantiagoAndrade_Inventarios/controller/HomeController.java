package com.example.SantiagoAndrade_Inventarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
