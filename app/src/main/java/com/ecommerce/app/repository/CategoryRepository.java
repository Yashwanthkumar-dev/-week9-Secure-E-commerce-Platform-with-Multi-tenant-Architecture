package com.ecommerce.app.repository;

import com.ecommerce.app.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByTenantId(Long tenantId);

    Optional<CategoryEntity> findByIdAndTenantId(Long id, Long tenantId);
}

