package com.konoha.tsunade.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = TableName.POST_ENTITY)
public class PostEntity extends BaseEntity {

	@Lob
	@Column(name = ColumnName.CONTENT)
	private String content;

	@ManyToOne
	@JoinColumn(name = ColumnName.ALIAS_ID)
	private AliasEntity author;

	@OneToOne
	@JoinColumn(name = ColumnName.ROOM_ID)
	private RoomEntity room;
}
