package com.codewithtee.ecommerce.order;


import com.codewithtee.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,

        String reference,


        @Positive(message = "Order amount should be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "customer id is required")
        @NotBlank(message = "customer id is required")
        @NotEmpty(message = "customer id is required")
        String customerId,


        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {

}
