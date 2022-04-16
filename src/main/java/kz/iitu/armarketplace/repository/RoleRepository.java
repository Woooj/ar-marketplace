package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.RoleEntity;
import kz.iitu.armarketplace.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	Optional<RoleEntity> findByName(RoleEnum name);

}
