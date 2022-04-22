package kz.iitu.armarketplace.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private BigDecimal rating;

	private Integer price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity categoryId;

	private Date createdAt;

	@OneToMany
	private Set<FileEntity> mediaFiles;

	@Transient
	public String getImagesPath() {
		if (mediaFiles == null || id == null) return null;

		return "/product-photos/" + id + "/";
	}

}
