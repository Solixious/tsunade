package com.konoha.tsunade.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.konoha.tsunade.constants.FeedbackStatus;
import com.konoha.tsunade.model.entity.FeedbackEntity;

public interface FeedbackRepository extends TsunadePagingAndSortingRepository<FeedbackEntity, Long> {

	Page<FeedbackEntity> findByStatusAndActiveTrue(FeedbackStatus status, Pageable pageable);
}
