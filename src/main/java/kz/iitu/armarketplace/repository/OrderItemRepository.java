package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findAllByOrderDetailsId(Long orderDetails_id);
}
