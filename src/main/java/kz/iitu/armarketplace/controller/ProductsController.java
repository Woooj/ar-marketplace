package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.entity.CategoryEntity;
import kz.iitu.armarketplace.model.ProductDTO;
import kz.iitu.armarketplace.model.ProductToSave;
import kz.iitu.armarketplace.model.ProductsResponse;
import kz.iitu.armarketplace.service.FileService;
import kz.iitu.armarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

	private final ProductService productService;

	private final FileService fileService;

	@GetMapping("/all")
	public ProductsResponse getAll(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
																 @RequestParam(name = "pageSize", defaultValue = "12", required = false) Integer pageSize,
																 @RequestParam(name = "category", required = false) String category,
																 @RequestParam(name = "sort", required = false) String sort,
																 @RequestParam(name = "sortType", required = false) String sortType) {

		return productService.getAllProductsWithFilter(page, pageSize, category, sort, sortType);
	}

	@GetMapping("/get/{id}")
	public ProductDTO getById(@PathVariable("id") Long id) {

		return productService.getById(id);
	}

	@GetMapping("/search")
	public ProductsResponse searchProduct(@RequestParam("searchWord") String searchWord) {

		return productService.search(searchWord);
	}

	@GetMapping("/get-by-category/{categoryId}")
	public List<ProductDTO> getByCategory(@PathVariable("categoryId") Integer categoryId) {

		return productService.getByCategory(categoryId);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<ProductDTO> addProduct(ProductToSave product) {

		return ResponseEntity.ok(productService.saveProduct(product));
	}


	@GetMapping("/categories/all")
	public List<CategoryEntity> getAllCategories() {

		return productService.getAllCategories();
	}

}
