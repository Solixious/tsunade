package com.konoha.tsunade.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.konoha.tsunade.constants.ReactionType;
import com.konoha.tsunade.model.entity.AnonymousUserEntity;
import com.konoha.tsunade.model.entity.PostEntity;
import com.konoha.tsunade.model.entity.ReactionEntity;

@Repository
public interface ReactionRepository extends TsunadePagingAndSortingRepository<ReactionEntity, Long> {

	Optional<ReactionEntity> findByPostAndAnonymousUser(PostEntity post, AnonymousUserEntity anonymousUser);

	Long countByPostAndReactionType(PostEntity post, ReactionType reactionType);
}
