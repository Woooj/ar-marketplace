package kz.iitu.armarketplace.service;

import kz.iitu.armarketplace.entity.CategoryEntity;
import kz.iitu.armarketplace.entity.ProductEntity;
import kz.iitu.armarketplace.model.ProductDTO;
import kz.iitu.armarketplace.model.ProductToSave;
import kz.iitu.armarketplace.model.ProductsResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

	ProductsResponse getAllProductsWithFilter(String category, Double rating, Integer price);

	ProductDTO getById(Long id);

	List<ProductDTO> getByCategory(Integer categoryId);

	ProductDTO saveProduct(ProductToSave product);

	List<CategoryEntity> getAllCategories();
}
