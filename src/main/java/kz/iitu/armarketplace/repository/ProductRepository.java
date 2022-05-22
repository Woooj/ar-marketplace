package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.Category;
import kz.iitu.armarketplace.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


	@Query(value = "SELECT nextval('products_id_seq')", nativeQuery =
		true)
	Long getNextSeriesId();

	@Query(value = "SELECT * FROM products p WHERE p.name LIKE CONCAT('%', :keyWord, '%') OR p.description LIKE CONCAT('%', :keyWord, '%') order by created_at", nativeQuery =
		true)
	List<Product> getProductsBySearchWord(@Param("keyWord") String keyWord);

	List<Product> findAllByCategoryIdAndRatingAndPrice(@NonNull Category category, @NonNull BigDecimal rating, @NonNull Integer price);

	List<Product> findAllByCategoryId(@NonNull Category category, Pageable pageable);

	Optional<Product> findById(@NonNull Long id);

	Optional<List<Product>> findByCategoryId(@NonNull Integer category);


	@Query(value = "SELECT count(1) FROM products p where p.category_id = ?1", nativeQuery = true)
	long countByCategory(Integer categoryId);
}
