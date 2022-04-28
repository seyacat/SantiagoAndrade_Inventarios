package com.example.SantiagoAndrade_inventarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.requestdata.Quote;

@SpringBootApplication
public class SantiagoAndradeInventariosApplication {
	private static final Logger log = LoggerFactory.getLogger(SantiagoAndradeInventariosApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SantiagoAndradeInventariosApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"https://mocki.io/v1/efe8471e-7c67-48f0-b3fb-6e2b339e2da6", Quote.class);
			log.info(quote.toString());
		};
	}
}
