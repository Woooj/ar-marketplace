package kz.iitu.armarketplace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discounts")
@Entity
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@Column(name = "discount_percent")
	private BigDecimal discountPercent;

	private Boolean active;

	@OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
	private List<Product> products;

	@Column(name = "created_at", columnDefinition = "timestamp")
	private Timestamp createdAt;

	@Column(name = "modified_at", columnDefinition = "timestamp")
	private Timestamp modifiedAt;

	@Column(name = "deleted_at", columnDefinition = "timestamp")
	private Timestamp deletedAt;

}
