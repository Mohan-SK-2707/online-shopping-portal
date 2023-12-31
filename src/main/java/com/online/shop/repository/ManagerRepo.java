package com.online.shop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.online.shop.model.Manager;

/**
 * Repository layer where we can able to handle all the CRUD operation and
 * interacting with DB by very easier way
 * 
 * @category Persistence module
 * @author Sneka S
 */
@EnableMongoRepositories
public interface ManagerRepo extends MongoRepository<Manager, String> {

	public Optional<Manager> findByEmailId(String emailId);
	
	public Optional <Manager> findById(String id);

}
