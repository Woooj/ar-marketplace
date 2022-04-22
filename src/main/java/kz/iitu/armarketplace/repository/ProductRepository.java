package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.CategoryEntity;
import kz.iitu.armarketplace.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findAllByCategoryIdAndRatingAndPrice(@NonNull CategoryEntity category,
																													 @NonNull BigDecimal rating,
																													 @NonNull Integer price);

	List<ProductEntity> findAllByCategoryId(@NonNull CategoryEntity category);

	Optional<ProductEntity> findById(@NonNull Long id);

	Optional<List<ProductEntity>> findByCategoryId(@NonNull Integer category);
}
