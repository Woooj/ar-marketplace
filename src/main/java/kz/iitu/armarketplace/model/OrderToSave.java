package kz.iitu.armarketplace.model;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderToSave {

	private List<OrderItemToSave> items;

	private Double total;

	private Long userId;

}
