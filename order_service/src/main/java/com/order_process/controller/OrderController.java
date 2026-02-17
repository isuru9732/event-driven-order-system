package com.order_process.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order_process.dto.request.CreateOrderRequest;
import com.order_process.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(value="new-order")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {

		return orderService.PlaceOrder(request);
	}
}
