package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductToSave {

	public String name;

	public String description;

	public Integer price;

	public Integer category;

	public MultipartFile file;

}
