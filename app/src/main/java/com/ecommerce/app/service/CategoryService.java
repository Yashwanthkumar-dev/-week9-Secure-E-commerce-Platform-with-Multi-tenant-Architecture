package com.ecommerce.app.service;

import com.ecommerce.app.model.CategoryEntity;
import com.ecommerce.app.model.TenantEntity;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.repository.TenantRepository;
import com.ecommerce.app.security.tenant.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class CategoryService {

    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private TenantRepository tenantRepo;

    // ============ Reuse code ============
    public CategoryEntity getIdOrThrow(Long id) {

        return catRepo.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
    }
    //    ========== create category ===========

    public ResponseEntity<?> createCatergory(String name, String description, MultipartFile image) throws IOException {

//        ====== creating image folder and save in that folder ======

        String uploaddir = "categoryUpload";

        File directoryFile = new File(uploaddir);

        if (!directoryFile.exists()) {
            directoryFile.mkdir();
        }

        String imageName = image.getOriginalFilename();

        Path filePath = Paths.get(uploaddir, imageName);

        Files.write(filePath, image.getBytes());

//        ======= creating category ======
        CategoryEntity newCategory = new CategoryEntity();
        TenantEntity tenant = tenantRepo.findById(TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        newCategory.setTenant(tenant);
        newCategory.setCategoryName(name);
        newCategory.setDescription(description);
        newCategory.setImage(imageName);
        newCategory.setCreatedAt(LocalDateTime.now());

        catRepo.save(newCategory);

        return ResponseEntity.ok("category was created");
    }


    //     ============ get all categories ============

    public ResponseEntity<?> getAllcategories() {
        List<CategoryEntity> categories = catRepo.findByTenantId(TenantContext.getTenantId());
        return ResponseEntity.ok(categories);
    }


    // =========== get  category by id ==============

    public ResponseEntity<?> getCategoryById(Long id) {
        CategoryEntity Category = getIdOrThrow(id);
        return ResponseEntity.ok(Category);

    }

    //    ================== update category by id ========

    public ResponseEntity<?> updateCategoryById(Long id, CategoryEntity cat) {
        CategoryEntity Category = getIdOrThrow(id);

        Category.setCategoryName(cat.getCategoryName());
        Category.setUpdatedAt(LocalDateTime.now());
        Category.setDescription(cat.getDescription());
        catRepo.save(Category);
        return ResponseEntity.ok("Category was updated ");

    }

    //    ================== delete category by id ========

    public ResponseEntity<?> deleteCategoryById(Long id) {

        CategoryEntity isCategory = getIdOrThrow(id);

        catRepo.delete(isCategory);

        return ResponseEntity.ok("Category was deleted ");
    }
}
