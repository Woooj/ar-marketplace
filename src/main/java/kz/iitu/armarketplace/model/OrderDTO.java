package kz.iitu.armarketplace.model;

import kz.iitu.armarketplace.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

	private Long id;

	private List<OrderItem> items;

	private Double total;

	private Date createdAt;

	private Date modifiedAt;

}
