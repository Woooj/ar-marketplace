package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.model.OrderDTO;
import kz.iitu.armarketplace.model.OrderToSave;
import kz.iitu.armarketplace.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@PostMapping
	public void createOrder(OrderToSave toSave) {

		service.createOrder(toSave);
	}

	@GetMapping("/get-all")
	public List<OrderDTO> getAllOrders() {

		return service.getAllOrders();
	}

	@GetMapping("/get-all-by-user")
	public List<OrderDTO> getOrdersForUser(@RequestParam("userId") Long userId) {

		return service.getOrdersForUser(userId);
	}

}
