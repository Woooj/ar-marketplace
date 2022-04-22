package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

	List<FileEntity> findAllByProductId(Long id);

}