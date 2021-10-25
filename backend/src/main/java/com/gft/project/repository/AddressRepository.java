package com.gft.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gft.project.entities.Address;



@Repository
public interface AddressRepository extends CrudRepository<Address, String>{

}
