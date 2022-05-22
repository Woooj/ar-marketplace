package kz.iitu.armarketplace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private OrderDetails orderDetails;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private Integer quantity;

	@Column(name = "created_at", columnDefinition = "timestamp")
	private Timestamp createdAt;

	@Column(name = "modified_at", columnDefinition = "timestamp")
	private Timestamp modifiedAt;

}
