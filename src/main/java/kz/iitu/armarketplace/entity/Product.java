package kz.iitu.armarketplace.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private BigDecimal rating;

	private Integer price;

	@Column(columnDefinition = "integer default 1")
	private Integer amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discount_id")
	private Discount discount;

	@CreationTimestamp
	private Date createdAt;

	@OneToMany(mappedBy = "product")
	private List<File> imgProducts;

	@OneToMany(mappedBy = "product")
	private List<Comment> comments;

}
