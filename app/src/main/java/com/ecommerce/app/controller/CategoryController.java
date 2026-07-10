package com.ecommerce.app.controller;

import com.ecommerce.app.model.CategoryEntity;
import com.ecommerce.app.service.CategoryService;
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

//    ========== create category ===========

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    public ResponseEntity<?> createCategory(@RequestParam String name, @RequestParam String description, @RequestParam MultipartFile image) throws IOException {

        return service.createCatergory(
                name,
                description,
                image
        );
    }


    //     ============ get all categories ============
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return service.getAllcategories();
    }

    //    ================ get category by id ===========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    //    ================== update category by id ========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@PathVariable Long id, @RequestBody CategoryEntity cat) {
        return service.updateCategoryById(id, cat);
    }

    //    ================== delete category by id ========
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        return service.deleteCategoryById(id);
    }
}
