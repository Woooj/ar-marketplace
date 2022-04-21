package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.entity.ProductEntity;
import kz.iitu.armarketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

	private final ProductRepository productRepository;

	@GetMapping("/all")
	public List<ProductEntity> getAll() {

		return productRepository.findAll();
	}

	@GetMapping("/get/{id}")
	public ProductEntity getById(@PathVariable("id") Long id) {

		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("There is no product with id: " + id));
	}

	@GetMapping("/get/{categoryId}")
	public List<ProductEntity> getByCategory(@PathVariable("categoryId") Long categoryId) {

		return productRepository.findByCategoryId(categoryId).orElseThrow(() -> new RuntimeException("There is no product with category id: " + categoryId));
	}

}
