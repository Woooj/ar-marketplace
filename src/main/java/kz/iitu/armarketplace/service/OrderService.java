package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.model.OrderDTO;
import kz.iitu.armarketplace.model.OrderToSave;

import java.util.List;

public interface OrderService {

	void createOrder(OrderToSave toSave);

	List<OrderDTO> getAllOrders();

	List<OrderDTO> getOrdersForUser(Long userId);

}
