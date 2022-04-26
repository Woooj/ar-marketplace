package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

	private Long id;

	private String text;

	private Double rating;

	private String username;

	private Date createdAt;

	private Long productId;

}
