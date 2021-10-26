package com.gft.project.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gft.project.entities.Address;



@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

	
	@GetMapping("/{cep}/json/")
	Address searchforCep(@PathVariable("cep") String cep);
	
}



