package com.codewithtee.ecommerce.notification;

import com.codewithtee.ecommerce.kafka.order.OrderConfirmation;
import com.codewithtee.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(value = "notifications")
public class NotificationEntity {
    @Id
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;

    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
