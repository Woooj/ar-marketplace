package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsResponse {

	public List<ProductDTO> list;

	public int total;

}
