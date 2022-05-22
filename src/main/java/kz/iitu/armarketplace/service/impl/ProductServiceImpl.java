package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.Category;
import kz.iitu.armarketplace.entity.File;
import kz.iitu.armarketplace.entity.Product;
import kz.iitu.armarketplace.model.*;
import kz.iitu.armarketplace.repository.CategoryRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.service.CommentService;
import kz.iitu.armarketplace.service.FileService;
import kz.iitu.armarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	private final FileService fileService;

	private final CommentService commentService;

	@Override
	public ProductsResponse getAllProductsWithFilter(Integer page, Integer pageSize, String category, String sort, String sortType) {

		List<ProductDTO> dtoList;
		Pageable pageable;
		int total;

		if(sort == null || sort.equals("")) {
			pageable = PageRequest.of(page, pageSize);
		} else {
			if (sortType.equals("DESC")) {
				pageable = PageRequest.of(page, pageSize, Sort.by(sort).descending());
			} else {
				pageable = PageRequest.of(page, pageSize, Sort.by(sort));
			}
		}

		if (isBlank(category)) {
				dtoList = convertListToDTO(productRepository.findAll(pageable).getContent());
				total = (int) productRepository.count();
				return new ProductsResponse(dtoList, total);
			}

			Category categoryEntity = categoryRepository.findByName(category).orElse(new Category());

			if (Objects.isNull(categoryEntity.getId())) {
				return new ProductsResponse(new ArrayList<>(), 0);
			}
			dtoList = convertListToDTO(productRepository.findAllByCategoryId(categoryEntity, pageable));
			total = (int) productRepository.countByCategory(categoryEntity.getId());
			return new ProductsResponse(dtoList, total);

	}

	@Override
	public ProductsResponse search(String searchWord) {

		if (isBlank(searchWord.trim())) {
			throw new RuntimeException("Empty search data");
		}

		List<Product> productsBySearchWord = productRepository.getProductsBySearchWord(searchWord.trim());

		return ProductsResponse.builder()
			.list(convertListToDTO(productsBySearchWord))
			.total(productsBySearchWord.size())
			.build();
	}

	@Override
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public ProductDTO getById(Long id) {

		ProductDTO productDTO = convertToDTO(productRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("There is no product with id: " + id)));

		List<CommentDTO> commentsForProduct = commentService.getByProductId(productDTO.getId());

		productDTO.comments = commentsForProduct;

		return productDTO;
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

		Product saved = productRepository.save(saveToEntity(product));

		Long productId = saved.getId();


		String fileName = "asd";

		try {
			fileService.store(new FileToSave(fileName, productId, product.files));
		} catch (IOException ignored) {
			System.out.println("error " + ignored.getMessage());
		}



		return convertToDTO(saved);
	}

	private Product saveToEntity(ProductToSave product) {

//		List<String> paths = new ArrayList<>();

//		for (MultipartFile file : product.files) {
//			paths.add(product.id + "/" + file.getOriginalFilename());
//		}

		return Product.builder()
			.id(product.id)
			.name(product.name)
			.description(product.description)
			.category(categoryRepository.findById(product.category).orElseThrow(() -> new RuntimeException("No such category")))
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
	public List<Category> getAllCategories() {

		return categoryRepository.findAll();
	}

	private List<ProductDTO> convertListToDTO(List<Product> all) {

		List<ProductDTO> dtoList = new ArrayList<>();
		for(Product productEntity : all)
			{
				Category category = productEntity.getCategory();
				List<String> images = new ArrayList<>();
				List<File> mediaFiles = fileService.getAllFilesByProduct(productEntity.getId());
				mediaFiles.forEach(i -> images.add(i.getName()));
				String thumbnail = images.size() > 0 ? images.get(0) : "";
				ProductDTO dto = ProductDTO.builder()
					.id(productEntity.getId())
					.name(productEntity.getName())
					.description(productEntity.getDescription())
					.rating(productEntity.getRating())
					.price(productEntity.getPrice())
					.amount(productEntity.getAmount())
					.createdAt(productEntity.getCreatedAt())
					.categoryName(category != null ? category.getName() : "")
					.images(images)
					.thumbnail(thumbnail)
					.build();

				dtoList.add(dto);
			}

		return dtoList;
	}

	private ProductDTO convertToDTO(Product productEntity) {

		List<String> images = new ArrayList<>();
		List<File> mediaFiles = fileService.getAllFilesByProduct(productEntity.getId());
		mediaFiles.forEach(i -> images.add(i.getName()));
		String thumbnail = images.size() > 0 ? images.get(0) : "";

		return ProductDTO.builder()
			.id(productEntity.getId())
			.name(productEntity.getName())
			.description(productEntity.getDescription())
			.rating(productEntity.getRating())
			.price(productEntity.getPrice())
			.amount(productEntity.getAmount())
			.createdAt(productEntity.getCreatedAt())
			.categoryName(productEntity.getCategory().getName())
			.images(images)
			.thumbnail(thumbnail)
			.build();
	}

}
