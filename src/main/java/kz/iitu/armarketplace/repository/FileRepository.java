package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

	List<FileEntity> findAllByProductId(Long id);

	Optional<FileEntity> findByName(String name);
}
