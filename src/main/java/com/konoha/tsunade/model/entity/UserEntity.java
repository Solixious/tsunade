package com.konoha.tsunade.model.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.dozer.Mapping;

import com.konoha.tsunade.constants.ColumnName;
import com.konoha.tsunade.constants.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = TableName.USER_ENTITY)
public class UserEntity extends BaseEntity {

	@Column(name = ColumnName.USER_NAME)
	private String userName;

	@Column(name = ColumnName.EMAIL)
	private String email;

	@Column(name = ColumnName.PASSWORD)
	@Mapping("this")
	private String password;

	@Column(name = ColumnName.FORCE_RESET_PASSWORD)
	private Boolean forceResetPassword;

	@Column(name = ColumnName.RESET_PASSWORD_KEY)
	private String resetPasswordKey;

	@Column(name = ColumnName.PROFILE_PICTURE)
	private String profilePicture;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = TableName.USER_ROLE, joinColumns = @JoinColumn(name = ColumnName.USER_ID, referencedColumnName = ColumnName.ID), inverseJoinColumns = @JoinColumn(name = ColumnName.ROLE_ID, referencedColumnName = ColumnName.ID))
	private Collection<RoleEntity> roles;
}
