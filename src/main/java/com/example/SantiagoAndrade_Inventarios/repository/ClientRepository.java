package com.example.SantiagoAndrade_Inventarios.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.SantiagoAndrade_Inventarios.model.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
	
	List<Client> findByCi(String ci);

}