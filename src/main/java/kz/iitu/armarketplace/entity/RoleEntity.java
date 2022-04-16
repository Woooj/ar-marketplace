package kz.iitu.armarketplace.entity;

import kz.iitu.armarketplace.enums.RoleEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleEnum name;

	public RoleEntity() {

	}

	public RoleEntity(RoleEnum name) {
		this.name = name;
	}
}
