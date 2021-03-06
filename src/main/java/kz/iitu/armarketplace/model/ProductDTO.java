package kz.iitu.armarketplace.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	public Long id;

	public String name;
	public String description;
	public BigDecimal rating;
	public Integer price;

	public Integer amount;

	public String categoryName;

	public Date createdAt;

	public List<String> images;

	public String thumbnail;

	public List<CommentDTO> comments;

}
