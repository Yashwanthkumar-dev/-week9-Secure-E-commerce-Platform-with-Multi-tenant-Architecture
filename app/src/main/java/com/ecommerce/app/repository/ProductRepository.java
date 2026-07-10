package com.ecommerce.app.repository;

import com.ecommerce.app.model.CategoryEntity;
import com.ecommerce.app.model.ProductEntity;
import com.ecommerce.app.security.tenant.TenantContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTenantId(Long tenantId);

    Optional<ProductEntity> findByIdAndTenantId(Long id, Long tenantId);

}
