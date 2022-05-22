package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

	List<File> findAllByProductId(Long id);

	Optional<File> findByName(String name);
}
