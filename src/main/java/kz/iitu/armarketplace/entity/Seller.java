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
@Table(name = "sellers")
@Entity
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String contact;

	private BigDecimal rating;

	private String address;

	@Column(name = "created_at", columnDefinition = "timestamp")
	private Timestamp createdAt;

	@Column(name = "modified_at", columnDefinition = "timestamp")
	private Timestamp modifiedAt;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "seller_user",
		joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private User user;

}
