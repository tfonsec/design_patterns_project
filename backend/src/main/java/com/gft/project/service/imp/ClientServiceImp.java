package com.gft.project.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.project.entities.Address;
import com.gft.project.entities.Client;
import com.gft.project.repository.AddressRepository;
import com.gft.project.repository.ClientRepository;
import com.gft.project.service.ClientService;
import com.gft.project.service.ViaCepService;

@Service
public class ClientServiceImp implements ClientService {

	
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	

	@Override
	public Iterable<Client> findAll() {
			return clientRepository.findAll();
	}

	@Override
	public Client findById(Long id) {
		
		Optional<Client> client = clientRepository.findById(id);
		return client.get();
	}

	@Override
	public void insert(Client client) {
		saveClientAndCep(client);
	}

	@Override
	public void update(Long id, Client client) {
		
		Optional<Client> clientBd = clientRepository.findById(id);
		if (clientBd.isPresent()) {
			saveClientAndCep(client);
		}
	}

	@Override
	public void delete(Long id) {
		
		clientRepository.deleteById(id);
	}

	private void saveClientAndCep(Client client) {
		
		String cep = client.getAddress().getCep();
		Address address = addressRepository.findById(cep).orElseGet(() -> {
			
			Address newAddress = viaCepService.searchforCep(cep);
			addressRepository.save(newAddress);
			return newAddress;
		});
		client.setAddress(address);
		
		clientRepository.save(client);
	}




}