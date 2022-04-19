package kz.iitu.armarketplace.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	public String name;
	public String description;
	public BigDecimal rating;
	public Integer price;

}
