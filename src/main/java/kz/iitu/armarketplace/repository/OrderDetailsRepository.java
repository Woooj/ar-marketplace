package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.OrderDetails;
import kz.iitu.armarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

	List<OrderDetails> findAllByUser(User user);

}
