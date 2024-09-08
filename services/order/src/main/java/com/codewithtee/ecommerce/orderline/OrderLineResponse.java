package com.codewithtee.ecommerce.orderline;

public record OrderLineResponse(
        Integer orderId,
        double quantity
) {
}
