package com.konoha.tsunade.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.konoha.tsunade.constants.ColumnName;
import com.konoha.tsunade.constants.ReactionType;
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
@Table(name = TableName.REACTION_ENTITY)
public class ReactionEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = ColumnName.POST_ID)
	private PostEntity post;

	@ManyToOne
	@JoinColumn(name = ColumnName.ANONYMOUS_USER_ID)
	private AnonymousUserEntity anonymousUser;

	@Column(name = ColumnName.REACTION_TYPE)
	@Enumerated(EnumType.ORDINAL)
	private ReactionType reactionType;
}
