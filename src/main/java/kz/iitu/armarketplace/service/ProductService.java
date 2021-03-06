package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.entity.Category;
import kz.iitu.armarketplace.model.ProductDTO;
import kz.iitu.armarketplace.model.ProductToSave;
import kz.iitu.armarketplace.model.ProductsResponse;

import java.util.List;

public interface ProductService {

	ProductsResponse getAllProductsWithFilter(Integer page, Integer pageSize, String category, String rating, String price);

	ProductDTO getById(Long id);

	List<ProductDTO> getByCategory(Integer categoryId);

	ProductDTO saveProduct(ProductToSave product);

	List<Category> getAllCategories();

	ProductsResponse search(String searchWord);

	void saveCategory(Category build);
}
