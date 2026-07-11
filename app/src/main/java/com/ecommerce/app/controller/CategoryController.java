package com.ecommerce.app.controller;

import com.ecommerce.app.model.CategoryEntity;
import com.ecommerce.app.service.CategoryService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    // ========== create category ===========

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    public ResponseEntity<?> createCategory(
            HttpServletRequest request,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) throws IOException {
        System.out.println("===== CONTROLLER =====");
        System.out.println(image.getOriginalFilename());
        System.out.println(image.getSize());
        System.out.println(image.isEmpty());

        System.out.println("====================");
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURI());
        System.out.println("Thread : " + Thread.currentThread().getName());
        System.out.println("Image : " + image.getOriginalFilename());
        System.out.println("====================");

            System.out.println("Controller hash : " + System.identityHashCode(image));

        return service.createCatergory(
                name,
                description,
                image);
    }

    // ============ get all categories ============
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return service.getAllcategories();
    }

    // ================ get category by id ===========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    // ================== update category by id ========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@PathVariable Long id, @RequestBody CategoryEntity cat) {
        return service.updateCategoryById(id, cat);
    }

    // ================== delete category by id ========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        return service.deleteCategoryById(id);
    }
}
