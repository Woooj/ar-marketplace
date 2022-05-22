package kz.iitu.armarketplace.entity;

import kz.iitu.armarketplace.enums.RoleEnum;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "roles")
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleEnum name;

	@Column(name = "created_at", columnDefinition = "timestamp")
	private Timestamp createdAt;

	@Column(name = "modified_at", columnDefinition = "timestamp")
	private Timestamp modifiedAt;


	public Role() {

	}

	public Role(RoleEnum name) {
		this.name = name;
	}
}
