package com.konoha.tsunade.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = TableName.ALIAS_ENTITY)
public class AliasEntity extends BaseEntity {

	@Column(name = ColumnName.USER_NAME)
	private String username;

	@Column(name = ColumnName.PROFILE_PICTURE)
	private String profilePicture;
}
