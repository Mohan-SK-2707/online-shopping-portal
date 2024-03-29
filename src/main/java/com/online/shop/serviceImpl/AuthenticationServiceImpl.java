package com.online.shop.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.online.shop.dto.AddressDto;
import com.online.shop.dto.CustomerDto;
import com.online.shop.dto.LoginRequestDto;
import com.online.shop.dto.ManagerDto;
import com.online.shop.enums.ROLE;
import com.online.shop.error_response.EShopException;
import com.online.shop.model.Address;
import com.online.shop.model.Authorities;
import com.online.shop.model.Customer;
import com.online.shop.model.Manager;
import com.online.shop.repository.ManagerRepo;
import com.online.shop.repository.UserRepo;
import com.online.shop.security.UserServiceImpl;
import com.online.shop.service.AuthenticationService;
import com.online.shop.utility.EShopUtility;

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

	private final UserRepo userRepository;
	private final ManagerRepo managerRepo;
	private final PasswordEncoder passwordEncoder;
	private final UserServiceImpl userService;
	private final EShopUtility utility;
	private final AuthenticationManager authenticationManager;

//user registration serviceImpl
	@Override
	public CustomerDto signUp(CustomerDto customer) {
		Optional<Customer> customers = userRepository.findByEmailId(customer.getEmailId());
		if (customers.isPresent()) {
			throw new EShopException(406, "The emailId is already exist please try with new emailId");
		}
		Customer saveDetail = utility.toConvert(customer, CustomerDto.class);
		saveDetail.setAddressess(List.of());
		saveDetail.setPassword(passwordEncoder.encode(customer.getPassword()));
		saveDetail.setUserAuthorities(List.of(new Authorities().setRole(ROLE.USER.getRoleName())));
		Customer saveDetailValues = userRepository.save(saveDetail);
		CustomerDto customerDto = utility.toConvert(saveDetailValues, CustomerDto.class);
		List<AddressDto> addressDto = utility.toConvertList(customerDto.getAddressess(), AddressDto.class);
		customerDto.setAddressess(addressDto);
		return customerDto;
	}

//management registration serviceImpl
	@Override
	public ManagerDto managementSignUp(ManagerDto manager) {
		Manager saveDetail = utility.toConvert(manager, Manager.class);
		saveDetail.setPassword(passwordEncoder.encode(manager.getPassword()));
		saveDetail.setManagerAuthorities(List.of(new Authorities().setRole(ROLE.ADMIN.getRoleName())));
		Manager saveDetailValues = managerRepo.save(saveDetail);
		ManagerDto managerDto = utility.toConvert(saveDetailValues, ManagerDto.class);
		return managerDto;
	}

	@Override
	public Boolean updateUserAuthority(String id, Set<String> authorities) {
		Customer existingCustomer = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User doesn't exist!"));
		java.util.List<Authorities> userAuthorities = existingCustomer.getUserAuthorities();
		userAuthorities.addAll(userAuthorities);
		return true;
	}

	@Override
	public String signIn(LoginRequestDto requestDto) {
		Optional<Customer> user = userRepository.findByEmailId(requestDto.getEmailId());
		if (user.isEmpty()) {
			Manager admin = managerRepo.findByEmailId(requestDto.getEmailId())
					.orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
			String admintoken = this.getToken(admin, requestDto);
			return admintoken;
		} else {
			String usertoken = this.getToken(user.get(), requestDto);
			return usertoken;
		}
	}

	private String getToken(UserDetails user, LoginRequestDto requestDto) {
		if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			String emailId = requestDto.getEmailId(), password = requestDto.getPassword();
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailId,
					password);
			authenticationManager.authenticate(authenticationToken);
			String generateToken = userService
					.generateToken(new User(user.getUsername(), user.getPassword(), user.getAuthorities()));
			return generateToken;
		} else {
			throw new UsernameNotFoundException("User doesn't exists!..");
		}
	}

}
