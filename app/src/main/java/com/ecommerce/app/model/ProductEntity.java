package com.ecommerce.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = " product name is required")
    private String name;
    @NotBlank(message = "product description is required")
    private String description;
    @NotBlank(message = " product image was important ")
    private String image;
    @NotBlank(message = "product price was important ")
    private float price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;
}
