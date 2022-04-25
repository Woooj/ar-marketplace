package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.CategoryEntity;
import kz.iitu.armarketplace.entity.ProductEntity;
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
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


	@Query(value = "SELECT nextval('products_id_seq')", nativeQuery =
		true)
	Long getNextSeriesId();

	@Query(value = "SELECT * FROM products p WHERE p.name LIKE CONCAT('%', :keyWord, '%') OR p.description LIKE CONCAT('%', :keyWord, '%') order by created_at", nativeQuery =
		true)
	List<ProductEntity> getProductsBySearchWord(@Param("keyWord") String keyWord);

	List<ProductEntity> findAllByCategoryIdAndRatingAndPrice(@NonNull CategoryEntity category,
																													 @NonNull BigDecimal rating,
																													 @NonNull Integer price);

	List<ProductEntity> findAllByCategoryId(@NonNull CategoryEntity category, Pageable pageable);

	Optional<ProductEntity> findById(@NonNull Long id);

	Optional<List<ProductEntity>> findByCategoryId(@NonNull Integer category);
}
