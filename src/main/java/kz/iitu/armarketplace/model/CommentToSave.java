package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentToSave {

	public Long id;

	public String text;

	public Double rating;

	public String username;

	public Long productId;

}
