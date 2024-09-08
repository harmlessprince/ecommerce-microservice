package com.codewithtee.ecommerce.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .id(paymentRequest.id())
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .orderId(paymentRequest.orderId())
                .build();
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(

        );
    }
}
