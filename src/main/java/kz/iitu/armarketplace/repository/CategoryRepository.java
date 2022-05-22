package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Optional<Category> findByName(@NonNull String name);

}
