package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.CategoryEntity;
import kz.iitu.armarketplace.entity.ProductEntity;
import kz.iitu.armarketplace.model.FileToSave;
import kz.iitu.armarketplace.model.ProductDTO;
import kz.iitu.armarketplace.model.ProductToSave;
import kz.iitu.armarketplace.model.ProductsResponse;
import kz.iitu.armarketplace.repository.CategoryRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.service.FileService;
import kz.iitu.armarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	private final FileService fileService;

	@Override
	public ProductsResponse getAllProductsWithFilter(String category, Double rating, Integer price) {

		List<ProductDTO> dtoList;

		if (isBlank(category) && rating == null && price == null) {
				dtoList = convertListToDTO(productRepository.findAll());
				return new ProductsResponse(dtoList, dtoList.size());
			} else if (isNotBlank(category) && rating == null && price == null) {

				CategoryEntity categoryEntity = categoryRepository.findByName(category).orElse(new CategoryEntity());

				if (Objects.isNull(categoryEntity.getId())) {
					return new ProductsResponse(new ArrayList<>(), 0);
				}
				dtoList = convertListToDTO(productRepository.findAllByCategoryId(categoryEntity));
				return new ProductsResponse(dtoList, dtoList.size());

			} else {

				CategoryEntity categoryEntity = categoryRepository.findByName(category).orElse(new CategoryEntity());

			if (Objects.isNull(categoryEntity.getId())) {
				return new ProductsResponse(new ArrayList<>(), 0);
			}

				dtoList = convertListToDTO(productRepository.findAllByCategoryIdAndRatingAndPrice(categoryEntity,
					BigDecimal.valueOf(rating), price));
				return new ProductsResponse(dtoList, dtoList.size());

			}
	}

	@Override
	public ProductDTO getById(Long id) {

		return convertToDTO(productRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("There is no product with id: " + id)));
	}

	@Override
	public List<ProductDTO> getByCategory(Integer id) {

		return convertListToDTO(productRepository.findByCategoryId(id)
			.orElseThrow(() -> new RuntimeException("There is no product with category id: " + id)));
	}

	@Override
	@Transactional
	public ProductDTO saveProduct(ProductToSave product) {

		validateProduct(product);

		ProductEntity saved = productRepository.save(saveToEntity(product));

		String fileName = StringUtils.cleanPath(product.getFile().getOriginalFilename());

		String uploadDir = "product-photos/" + saved.getId();

		try {
			fileService.saveFile(uploadDir, fileName, product.getFile());
		} catch (IOException ignored) {
			System.out.println("error " + ignored.getMessage());
		}

		return convertToDTO(saved);
	}

	private ProductEntity saveToEntity(ProductToSave product) {

		return ProductEntity.builder()
			.name(product.name)
			.description(product.description)
			.categoryId(categoryRepository.findById(product.category).orElseThrow(() -> new RuntimeException("No such category")))
			.rating(BigDecimal.ZERO)
			.price(product.price)
			.createdAt(new Date())
			.build();
	}

	private void validateProduct(ProductToSave product) {

		if (product == null) {
			throw new RuntimeException("No name");
		}

		if (product.name == null || product.name.equals("")) {
			throw new RuntimeException("No name");
		}

		if (product.description == null || product.description.equals("")) {
			throw new RuntimeException("No description");
		}

		if (product.category == null) {
			throw new RuntimeException("No category");
		}

		if (product.price == null) {
			throw new RuntimeException("No price");
		}

	}

	@Override
	public List<CategoryEntity> getAllCategories() {

		return categoryRepository.findAll();
	}

	private List<ProductDTO> convertListToDTO(List<ProductEntity> all) {

		List<ProductDTO> dtoList = new ArrayList<>();
		for(ProductEntity productEntity : all)
			{
				CategoryEntity category = productEntity.getCategoryId();
				ProductDTO dto = ProductDTO.builder()
					.id(productEntity.getId())
					.name(productEntity.getName())
					.description(productEntity.getDescription())
					.rating(productEntity.getRating())
					.price(productEntity.getPrice())
					.categoryName(category != null ? category.getName() : "")
					.build();

				dtoList.add(dto);
			}

		return dtoList;
	}

	private ProductDTO convertToDTO(ProductEntity productEntity) {

		return ProductDTO.builder()
			.id(productEntity.getId())
			.name(productEntity.getName())
			.description(productEntity.getDescription())
			.rating(productEntity.getRating())
			.price(productEntity.getPrice())
			.categoryName(productEntity.getCategoryId().getName())
			.build();
	}

}
