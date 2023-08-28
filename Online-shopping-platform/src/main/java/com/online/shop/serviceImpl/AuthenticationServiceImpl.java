package com.online.shop.serviceImpl;

import org.springframework.stereotype.Service;

import com.online.shop.model.Customer;
import com.online.shop.repository.UserRepo;
import com.online.shop.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

/**
 * Authentication service implementation - Business layer where we're written
 * all the business logic which has to implement the authentication and
 * authorization by very efficient way
 * 
 * @category - security module
 * @author - Mohanlal
 * 
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private UserRepo userRepository;

	@Override
	public String signUp(Customer customer) {
		return userRepository.save(customer).getId();
	}

}
