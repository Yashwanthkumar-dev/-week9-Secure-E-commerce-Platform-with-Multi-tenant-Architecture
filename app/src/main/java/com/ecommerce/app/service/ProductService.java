package com.ecommerce.app.service;

import com.ecommerce.app.dto.Response.ProductResponse;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.CategoryEntity;
import com.ecommerce.app.model.ProductEntity;
import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.TenantRepository;
import com.ecommerce.app.security.tenant.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private TenantRepository tenantRepo;

    public ProductEntity getIdOrThrow(Long id) {
        return productRepo.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ================= CREATE PRODUCT =================

    public ResponseEntity<?> createProduct(
            String name,
            String description,
            Float price,
            int stock,
            Long categoryId,
            MultipartFile image
    ) throws IOException {

        System.out.println("===== SERVICE START =====");
        System.out.println("Image Name : " + image.getOriginalFilename());
        System.out.println("Image Size : " + image.getSize());
        System.out.println("Is Empty   : " + image.isEmpty());

        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest().body("Image is empty");
        }

        CategoryEntity category = catRepo.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        TenantEntity tenant = tenantRepo.findById(TenantContext.getTenantId())
                .orElseThrow(() ->
                        new RuntimeException("Tenant not found"));

        String uploadDir = System.getProperty("user.dir") + "/productUpload";

        File folder = new File(uploadDir);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        File destination = new File(folder, fileName);

        image.transferTo(destination);

        ProductEntity product = new ProductEntity();

        product.setTenant(tenant);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setImage(fileName);   // <-- String field
        product.setCreatedAt(LocalDateTime.now());

        productRepo.save(product);

        return ResponseEntity.ok(product);
    }

    // ================= GET ALL =================

    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(
                productRepo.findByTenantId(TenantContext.getTenantId())
        );
    }

    // ================= GET BY ID =================

    public ResponseEntity<?> getAllProductsById(Long id) {
        return ResponseEntity.ok(getIdOrThrow(id));
    }

    // ================= UPDATE =================

    public ResponseEntity<?> updateProductById(
            Long id,
            String name,
            String description,
            Float price,
            int stock
    ) {

        ProductEntity product = getIdOrThrow(id);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setUpdatedAt(LocalDateTime.now());

        productRepo.save(product);

        return ResponseEntity.ok("Product Updated");
    }

    // ================= DELETE =================

    public ResponseEntity<?> deleteProductById(Long id) {

        ProductEntity product = getIdOrThrow(id);

        productRepo.delete(product);

        return ResponseEntity.ok("Product Deleted");
    }

    // ================= CATEGORY PRODUCTS =================

    public ResponseEntity<?> getProductByCategoryId(Long id) {

        CategoryEntity category = catRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        List<ProductResponse> products = category.getProducts()
                .stream()
                .map(product -> new ProductResponse(
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getPrice(),
                        product.getStock()
                ))
                .toList();

        return ResponseEntity.ok(products);
    }
}