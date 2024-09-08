package com.codewithtee.ecommerce.payment;


import com.codewithtee.ecommerce.notification.NotificationProducer;
import com.codewithtee.ecommerce.notification.PaymentNotificationRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class PaymentService {
    private  final PaymentRepository paymentRepository;
    private final  PaymentMapper mapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = this.paymentRepository.save(mapper.toPayment(paymentRequest));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstName(),
                        paymentRequest.customer().lastName(),
                        paymentRequest.customer().email()
                )
        );
        return payment.getId();
    }
}
