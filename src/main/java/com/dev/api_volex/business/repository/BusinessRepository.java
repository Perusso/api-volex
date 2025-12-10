package com.dev.api_volex.business.repository;

import com.dev.api_volex.business.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {
    // Métodos customizados se necessário
}
