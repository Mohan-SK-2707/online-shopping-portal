package com.online.shop.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.shop.dto.OrderDto;
import com.online.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<String> saveOrderDetails(@RequestBody OrderDto saveOrderDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(saveOrderDto));
	}

	@GetMapping("/id")
	public ResponseEntity<OrderDto> getOrderDetails(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.getOrderDetailsById(id));
	}
}
