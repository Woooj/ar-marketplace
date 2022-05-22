package kz.iitu.armarketplace.repository;

import kz.iitu.armarketplace.entity.Role;
import kz.iitu.armarketplace.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleEnum name);

}
