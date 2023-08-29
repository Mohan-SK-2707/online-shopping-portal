package com.online.shop.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.shop.dto.CustomerDto;
import com.online.shop.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


/**
 * Authentication controller - The gateway where we're handling all the
 * authentication and authorization related activities of the user's and
 * management person based on their roles and authorities
 * 
 * @version v_0.1
 * @category Security module
 * @author Mohanlal
 * 
 */
@RestController
@RequestMapping("/authentication")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;

	@GetMapping("/welcome-page")
	public ResponseEntity<String> getWelcomePageOfapplication() {
		return ResponseEntity.status(HttpStatus.OK)
				.body("Welcome to Online shopping platform......Have a great shopping!!");
	}

	/**
	 * Api for user registration
	 * 	
	 * @param customerDetails
	 * @return customerDto
	 * @category security module
	 * @author Sivaranjani K
	 */
	@PostMapping("/v1/sign-up")
	public ResponseEntity<CustomerDto> signUp(@RequestBody @Valid CustomerDto customerDetails) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUp(customerDetails));
	}

	@Secured("hasRole('ROLE_ADMIN')")
	@PutMapping("/v1/authority")
	public ResponseEntity<Boolean> updateUserAuthority(@RequestHeader String id, @RequestBody Set<String> authorities) {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(authenticationService.updateUserAuthority(id, authorities));
	}

}



