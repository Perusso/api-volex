package com.dev.api_volex.payment.repository;

import com.dev.api_volex.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.card.id = :cardId ORDER BY p.createdAt DESC")
    List<PaymentEntity> findAllByCardId(@Param("cardId") Long cardId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentEntity p WHERE p.card.id = :cardId")
    Long sumAmountByCardId(@Param("cardId") Long cardId);
}
