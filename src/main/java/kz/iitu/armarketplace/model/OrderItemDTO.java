package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

	private ProductDTO product;

	private Integer quantity;

}
