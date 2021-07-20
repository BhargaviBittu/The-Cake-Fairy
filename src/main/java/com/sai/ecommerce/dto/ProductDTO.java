package com.sai.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

    private String productName;

    private int categoryId;

    private double productPrice;

    private double productWeight;

    private String productDescription;

    private String ProductImageName;
}
