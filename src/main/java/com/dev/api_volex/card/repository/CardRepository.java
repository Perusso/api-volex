package com.dev.api_volex.card.repository;

import com.dev.api_volex.card.entity.CardEntity;
import com.dev.api_volex.card.enumeration.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Query("SELECT COUNT(c) > 0 FROM CardEntity c WHERE c.employee.id = :employeeId AND c.type = :type")
    boolean existsByEmployeeIdAndType(@Param("employeeId") Long employeeId, @Param("type") CardType type);
}
