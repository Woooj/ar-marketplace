package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.entity.Category;
import kz.iitu.armarketplace.entity.Product;
import kz.iitu.armarketplace.model.ProductDTO;
import kz.iitu.armarketplace.repository.CategoryRepository;
import kz.iitu.armarketplace.repository.ProductRepository;
import kz.iitu.armarketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

	private final RoleRepository roleRepository;
	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@PostMapping("/save-product")
	public void saveProduct(@RequestBody ProductDTO product) {

		productRepository.save(Product.builder()
				.name(product.name)
				.description(product.description)
				.rating(product.rating)
				.price(product.price)
				.amount(product.amount)
				.createdAt(new Date())
//				.categoryId(categoryRepository.findByName(product.categoryName).orElseThrow(() -> new RuntimeException("There is no such category")))
				.build());
	}

	@PostMapping("/save-category")
	public void saveCategory(@RequestBody String categoryName) {

		categoryRepository.save(Category.builder()
				.name(categoryName)
				.build());
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
