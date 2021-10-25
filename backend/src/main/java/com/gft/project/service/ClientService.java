package com.gft.project.service;

import com.gft.project.entities.Client;

public interface ClientService {

	Iterable<Client> buscarTodos();
 
	Client buscarPorId(Long id);

	void inserir(Client client);

	void atualizar(Long id, Client client);

	void deletar(Long id);

	
	
}
