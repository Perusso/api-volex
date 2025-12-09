package com.dev.api_volex.recharge.repository;

import com.dev.api_volex.recharge.entity.RechargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargeRepository extends JpaRepository<RechargeEntity, Long> {

    @Query("SELECT r FROM RechargeEntity r WHERE r.card.id = :cardId ORDER BY r.createdAt DESC")
    List<RechargeEntity> findAllByCardId(@Param("cardId") Long cardId);

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM RechargeEntity r WHERE r.card.id = :cardId")
    Long sumAmountByCardId(@Param("cardId") Long cardId);
}
