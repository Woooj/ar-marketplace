package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductToSave {

	public Long id;

	public String name;

	public String description;

	public Integer price;

	public Integer amount;

	public Integer category;

	public List<MultipartFile> files;

}
