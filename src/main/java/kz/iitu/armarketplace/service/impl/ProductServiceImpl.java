package kz.iitu.armarketplace.service.impl;

import kz.iitu.armarketplace.entity.CategoryEntity;
import kz.iitu.armarketplace.entity.FileEntity;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	private final FileService fileService;

	@Override
	public ProductsResponse getAllProductsWithFilter(Integer page, Integer pageSize, String category, String sort, String sortType) {

		List<ProductDTO> dtoList;
		Pageable pageable;

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
				return new ProductsResponse(dtoList, dtoList.size());
			}

			CategoryEntity categoryEntity = categoryRepository.findByName(category).orElse(new CategoryEntity());

			if (Objects.isNull(categoryEntity.getId())) {
				return new ProductsResponse(new ArrayList<>(), 0);
			}
			dtoList = convertListToDTO(productRepository.findAllByCategoryId(categoryEntity, pageable));
			return new ProductsResponse(dtoList, dtoList.size());

	}

	@Override
	public ProductsResponse search(String searchWord) {

		if (isBlank(searchWord.trim())) {
			throw new RuntimeException("Empty search data");
		}

		List<ProductEntity> productsBySearchWord = productRepository.getProductsBySearchWord(searchWord.trim());

		return ProductsResponse.builder()
			.list(convertListToDTO(productsBySearchWord))
			.total(productsBySearchWord.size())
			.build();
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

		Long productId = saved.getId();

		for (MultipartFile file : product.getFiles()) {

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			String uploadDir = "product-photos/" + productId;

			try {
				fileService.store(new FileToSave(fileName, productId));
				fileService.saveFile(uploadDir, fileName, file);
			} catch (IOException ignored) {
				System.out.println("error " + ignored.getMessage());
			}

		}


		return convertToDTO(saved);
	}

	private ProductEntity saveToEntity(ProductToSave product) {

		List<String> paths = new ArrayList<>();

		for (MultipartFile file : product.files) {
			paths.add(product.id + "/" + file.getOriginalFilename());
		}

		return ProductEntity.builder()
			.id(product.id)
			.name(product.name)
			.description(product.description)
			.categoryId(categoryRepository.findById(product.category).orElseThrow(() -> new RuntimeException("No such category")))
			.rating(BigDecimal.ZERO)
			.price(product.price)
//			.imgProducts(paths)
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
				List<String> paths = new ArrayList<>();
				List<FileEntity> mediaFiles = fileService.getAllFilesByProduct(productEntity.getId());
				mediaFiles.forEach(i -> paths.add(i.getPath()));
				ProductDTO dto = ProductDTO.builder()
					.id(productEntity.getId())
					.name(productEntity.getName())
					.description(productEntity.getDescription())
					.rating(productEntity.getRating())
					.price(productEntity.getPrice())
					.categoryName(category != null ? category.getName() : "")
					.filePath(paths)
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
