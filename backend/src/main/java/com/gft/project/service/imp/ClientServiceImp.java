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

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class ClientServiceImp implements ClientService {

	
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	

	@Override
	public Iterable<Client> buscarTodos() {
			return clientRepository.findAll();
	}

	@Override
	public Client buscarPorId(Long id) {
		
		Optional<Client> cliente = clientRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Client client) {
		salvarClienteComCep(client);
	}

	@Override
	public void atualizar(Long id, Client client) {
		// Buscar Cliente por ID, caso exista:
		Optional<Client> clienteBd = clientRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(client);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clientRepository.deleteById(id);
	}

	private void salvarClienteComCep(Client client) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = client.getAddress().getCep();
		Address endereco = addressRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Address novoEndereco = viaCepService.consultarCep(cep);
			addressRepository.save(novoEndereco);
			return novoEndereco;
		});
		client.setAddress(endereco);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clientRepository.save(client);
	}




}