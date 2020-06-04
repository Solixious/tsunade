package com.konoha.tsunade.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = TableName.ROOM_ENTITY)
public class RoomEntity extends BaseEntity {

	@Lob
	@Column(name = ColumnName.TITLE)
	private String title;

	@Lob
	@Column(name = ColumnName.DESCRIPTION)
	private String description;

	@Lob
	@Column(name = ColumnName.PATH)
	private String path;

	@Column(name = ColumnName.CLOSED)
	private Boolean closed;

	@ManyToOne
	@JoinColumn(name = ColumnName.ALIAS_ID)
	private AliasEntity author;
}
