package com.codewithtee.ecommerce.kafka;


import com.codewithtee.ecommerce.email.EmailService;
import com.codewithtee.ecommerce.kafka.order.OrderConfirmation;
import com.codewithtee.ecommerce.kafka.payment.PaymentConfirmation;
import com.codewithtee.ecommerce.notification.NotificationEntity;
import com.codewithtee.ecommerce.notification.NotificationRepository;
import com.codewithtee.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment-topic:: %s", paymentConfirmation));
        repository.save(NotificationEntity.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .paymentConfirmation(paymentConfirmation)
                .notificationDate(LocalDateTime.now())
                .build()
        );
        //  send email
        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        this.emailService.sendPaymentSuccessfulEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment-topic:: %s", orderConfirmation));
        repository.save(NotificationEntity.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .orderConfirmation(orderConfirmation)
                .notificationDate(LocalDateTime.now())
                .build()
        );
        //  send email
        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        this.emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
