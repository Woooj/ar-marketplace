package kz.iitu.armarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileToSave {

	private String name;

	private Long productId;

	private List<MultipartFile> images;
}
