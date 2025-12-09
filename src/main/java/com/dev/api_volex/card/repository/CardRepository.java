package com.dev.api_volex.card.repository;

import com.dev.api_volex.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    boolean existsByEmployeeIdAndType(Long employeeId, String type);
}
