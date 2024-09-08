package com.codewithtee.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(


        Integer id,

        @NotNull(message = "Product name is required")
        String name,

        @NotNull(message = "Product description is required")
        String description,




        @Positive(message = "Product available quantity should be positive")
        @NotNull(message = "Product available quantity is required")
        double availableQuantity,



        @Positive(message = "Product price should be positive")
        @NotNull(message = "Product price is required")
        BigDecimal price,

        @NotNull(message = "Product category is required")
        Integer categoryId
        ) {
}
