package com.codewithtee.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ProductPurchaseRequests(
        @NotNull(message = "Product is required")
        Integer productId,


        @NotNull(message = "Product quantity is required")
        @Positive(message = "Quantity must be positive")
        double quantity
) {
}
