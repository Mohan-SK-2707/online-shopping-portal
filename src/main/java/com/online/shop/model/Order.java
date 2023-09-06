package com.online.shop.model;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.online.shop.dto.PaymentDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Document(collection = "app_order")
@Accessors(chain = true)
public class Order {

	@Id
	private String id = UUID.randomUUID().toString();

	private Customer customer;

	private Address address;

	private List<Products> products;

	private List<Payment> payment;

}