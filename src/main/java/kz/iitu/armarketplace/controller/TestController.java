package kz.iitu.armarketplace.controller;

import kz.iitu.armarketplace.entity.RoleEntity;
import kz.iitu.armarketplace.enums.RoleEnum;
import kz.iitu.armarketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

	private final RoleRepository roleRepository;
	@GetMapping("/all")
	public String allAccess() {

		roleRepository.saveAll(Arrays.asList(
			new RoleEntity(RoleEnum.ROLE_ADMIN),
			new RoleEntity(RoleEnum.ROLE_USER),
			new RoleEntity(RoleEnum.ROLE_MODERATOR)));
		return "Public Content.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
