package com.ecommerce.app.controller;

import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    @Autowired
    private TenantService service;

    //    ============ create Tenant ===========
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    private ResponseEntity<?> createTenant(@RequestBody TenantEntity tenant) {
        return service.createTenant(tenant);
    }

    //    ============== get all tenants ==========
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    private ResponseEntity<?> getAllTenants() {
        return service.getAllTenants();
    }

    //    =============== get tenants by id ============
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    private ResponseEntity<?> getTenantById(@PathVariable Long id) {
        return service.getTenantById(id);
    }

    //    ========= update tenant by id ==============
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    private ResponseEntity<?> updateTenantById(@PathVariable Long id, @RequestBody TenantEntity tenant) {
        return service.updateTenantById(id, tenant);
    }

    // ============== delete tenant by id ==============
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    private ResponseEntity<?> deleteTenantById(@PathVariable Long id) {
        return service.deleteTenantById(id);
    }
}
