package com.ecommerce.app.controller;

import com.ecommerce.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin(origins = "*")
@RestController
    @RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    // ======== create product ===========
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    public ResponseEntity<?> createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Float price,
            @RequestParam int stock,
            @RequestParam Long categoryId,
            @RequestParam MultipartFile image
    ) throws IOException {
        System.out.println("CONTROLLER HIT");
        System.out.println(image.getOriginalFilename());
        System.out.println(image.getSize());
        System.out.println(image.isEmpty());
        return service.createProduct(name, description, price, stock, categoryId, image);
    }

    // =========== get all products ================
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping
    public ResponseEntity<?> getALlProducts() {
        return service.getAllProducts();
    }

    //    ================== get  product by  id ===========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductsById(@PathVariable Long id) {
        return service.getAllProductsById(id);
    }

    //     ================== update product by id ================
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestParam String name, @RequestParam String description, @RequestParam Float price, @RequestParam int stock) {
        return service.updateProductById(id, name, description, price, stock);
    }

    //    ============= delete product by id ================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        return service.deleteProductById(id);

    }

    //    =============== get products by category id ============
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductByCategoryId(@PathVariable Long id) {
        return service.getProductByCategoryId(id);

    }
}
