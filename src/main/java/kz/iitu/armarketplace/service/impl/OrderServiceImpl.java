package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.OrderDetails;
import kz.iitu.armarketplace.entity.OrderItem;
import kz.iitu.armarketplace.entity.User;
import kz.iitu.armarketplace.model.OrderDTO;
import kz.iitu.armarketplace.model.OrderItemToSave;
import kz.iitu.armarketplace.model.OrderToSave;
import kz.iitu.armarketplace.repository.OrderDetailsRepository;
import kz.iitu.armarketplace.repository.OrderItemRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.repository.UserRepository;
import kz.iitu.armarketplace.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderDetailsRepository detailsRepo;

	private final OrderItemRepository itemRepo;

	private final ProductRepository productRepo;

	private final UserRepository userRepo;

	@Override
	public void createOrder(OrderToSave toSave) {


		OrderDetails saved = detailsRepo.save(orderToEntity(toSave));

		itemRepo.saveAll(itemToEntity(toSave.getItems(), saved));

	}

	private List<OrderItem> itemToEntity(List<OrderItemToSave> itemsToSave, OrderDetails saved) {

		List<OrderItem> items = new ArrayList<>();

		for (OrderItemToSave orderItemToSave : itemsToSave) {
			items.add(OrderItem.builder()
				.orderDetails(saved)
				.quantity(orderItemToSave.getQuantity())
				.product(productRepo.findById(orderItemToSave.getProductId()).get())
				.createdAt(Timestamp.valueOf(LocalDateTime.now()))
				.modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
				.build());
		}

		return items;
	}

	private OrderDetails orderToEntity(OrderToSave toSave) {

		User user = userRepo.findById(toSave.getUserId()).orElseThrow(() -> new RuntimeException("No user"));

		return OrderDetails.builder()
			.user(user)
			.total(BigDecimal.valueOf(toSave.getTotal()))
			.createdAt(Timestamp.valueOf(LocalDateTime.now()))
			.modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
			.build();
	}

	@Override
	public List<OrderDTO> getAllOrders() {

		List<OrderDetails> details = detailsRepo.findAll();
		List<OrderDTO> dtoList = new ArrayList<>();

		for(OrderDetails detail : details) {
			List<OrderItem> allByOrderDetailsId = itemRepo.findAllByOrderDetailsId(detail.getId());
			dtoList.add(OrderDTO.builder()
					.id(detail.getId())
					.items(allByOrderDetailsId)
					.total(detail.getTotal().doubleValue())
					.createdAt(detail.getCreatedAt())
					.modifiedAt(detail.getModifiedAt())
				.build());
		}

		return dtoList;
	}

	@Override
	public List<OrderDTO> getOrdersForUser(Long userId) {

		List<OrderDetails> details = detailsRepo.findAllByUser(userRepo.findById(userId).get());
		List<OrderDTO> dtoList = new ArrayList<>();

		for(OrderDetails detail : details) {
			List<OrderItem> allByOrderDetailsId = itemRepo.findAllByOrderDetailsId(detail.getId());
			dtoList.add(OrderDTO.builder()
				.id(detail.getId())
				.items(allByOrderDetailsId)
				.total(detail.getTotal().doubleValue())
				.createdAt(detail.getCreatedAt())
				.modifiedAt(detail.getModifiedAt())
				.build());
		}

		return dtoList;
	}

}
