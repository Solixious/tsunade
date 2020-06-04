package com.konoha.tsunade.model.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = TableName.ANONYMOUS_USER_ENTITY)
public class AnonymousUserEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = ColumnName.ROOM_ID)
	private RoomEntity room;

	@ManyToOne
	@JoinColumn(name = ColumnName.ALIAS_ID)
	private AliasEntity alias;

	@ManyToOne
	@JoinColumn(name = ColumnName.USER_ID)
	private UserEntity user;
}
