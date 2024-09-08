package com.codewithtee.ecommerce.payment;

import com.codewithtee.ecommerce.customer.CustomerResponse;
import com.codewithtee.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        CustomerResponse customer
) {
}
