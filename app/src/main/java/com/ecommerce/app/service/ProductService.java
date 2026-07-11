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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

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
            // Long categoryId,
            MultipartFile image) throws IOException {

        System.out.println("image name :" + image.getOriginalFilename() + " image was passed");
        if (image == null || image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Image was not found or image was empty");
        }

        System.out.println("product image folder name passed");
        String uploadDir = "productImages";

        System.out.println("Folder was created for image");
        File directoryName = new File(uploadDir);
        if (!directoryName.exists()) {
            directoryName.mkdirs();
        }

        System.out.println("getting image name");
        String imageName = image.getOriginalFilename();

        System.out.println("creating image file path name");
        Path filePath = Paths.get(uploadDir, imageName);

        System.out.println("inserting the image into the folder");
        Files.write(filePath, image.getBytes());

        System.out.println("image stored ");

        // CategoryEntity category = catRepo.findById(categoryId)
        // .orElseThrow(
        // () -> new ResourceNotFoundException("category was not found"));

        ProductEntity newProduct = new ProductEntity();

        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        newProduct.setStock(stock);
        newProduct.setCreatedAt(LocalDateTime.now());
        // newProduct.setCategory(category);
        newProduct.setImage(uploadDir + "/" + imageName);

        try {
            System.out.println("before save");
            productRepo.save(newProduct);
            System.out.println("save after");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("product was created");
    }

    // ================= GET ALL =================

    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(
                productRepo.findByTenantId(TenantContext.getTenantId()));
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
            int stock) {

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
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<ProductResponse> products = category.getProducts()
                .stream()
                .map(product -> new ProductResponse(
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getPrice(),
                        product.getStock()))
                .toList();

        return ResponseEntity.ok(products);
    }
}