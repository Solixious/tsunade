package com.konoha.tsunade.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.konoha.tsunade.constants.ColumnName;
import com.konoha.tsunade.constants.FeedbackStatus;
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
@Table(name = TableName.FEEDBACK_ENTITY)
public class FeedbackEntity extends BaseEntity {

	@Column(name = ColumnName.USER_ID)
	private UserEntity user;

	@Column(name = ColumnName.FEEDBACK)
	private String feedback;

	@Column(name = ColumnName.STATUS)
	@Enumerated(EnumType.ORDINAL)
	private FeedbackStatus status;
}
