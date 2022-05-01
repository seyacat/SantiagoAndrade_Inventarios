package com.example.SantiagoAndrade_Inventarios;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.SantiagoAndrade_Inventarios.controller.ClientController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private ClientController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}