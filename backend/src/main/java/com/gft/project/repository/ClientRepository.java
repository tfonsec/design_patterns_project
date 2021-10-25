package com.gft.project.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gft.project.entities.Client;

@Repository
public interface ClientRepository extends CrudRepository <Client, Long> {

	

}
