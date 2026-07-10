package com.ecommerce.app.service;

import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepo;

    //    =========== Reuse code ================
    public TenantEntity getIdOrThrow(Long id) {
        return tenantRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Tenant was not found")
        );
    }

    //    ============ create Tenant ===========
    public ResponseEntity<?> createTenant(TenantEntity tenant) {
        TenantEntity newTenant = new TenantEntity();
        newTenant.setTenantName(tenant.getTenantName());
        newTenant.setCreatedAt(LocalDateTime.now());
        tenantRepo.save(newTenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "tenant Created successfully"
        );
    }

    //    ============== get all tenants ==========
    public ResponseEntity<?> getAllTenants() {
        List<TenantEntity> allTenants = tenantRepo.findAll();
        if (allTenants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No tenant was here");
        }
        return ResponseEntity.ok().body(allTenants);
    }

    //    =============== get tenants by id ============
    public ResponseEntity<?> getTenantById(Long id) {
        TenantEntity tenant = getIdOrThrow(id);
        return ResponseEntity.ok(tenant);
    }

    //    ========= update tenant by id ==============
    public ResponseEntity<?> updateTenantById(Long id, TenantEntity tenant) {
        TenantEntity updateTenant = getIdOrThrow(id);
        updateTenant.setTenantName(tenant.getTenantName());
        updateTenant.setUpdatedAt(LocalDateTime.now());
        tenantRepo.save(updateTenant);
        return ResponseEntity.ok("tenant was updated");
    }

    // ============== delete tenant by id ==============
    public ResponseEntity<?> deleteTenantById(Long id) {
        TenantEntity tenant = getIdOrThrow(id);
        tenantRepo.delete(tenant);
        return ResponseEntity.ok("deleted successfully");
    }
}
