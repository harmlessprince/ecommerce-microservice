package com.codewithtee.ecommerce.order;


import com.codewithtee.ecommerce.customer.CustomerClient;
import com.codewithtee.ecommerce.exception.BusinessException;
import com.codewithtee.ecommerce.kafka.OrderConfirmation;
import com.codewithtee.ecommerce.kafka.OrderProducer;
import com.codewithtee.ecommerce.orderline.OrderLineRequest;
import com.codewithtee.ecommerce.orderline.OrderLineService;
import com.codewithtee.ecommerce.payment.PaymentClient;
import com.codewithtee.ecommerce.payment.PaymentRequest;
import com.codewithtee.ecommerce.product.ProductClient;
import com.codewithtee.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private  final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;

    private final PaymentClient paymentClient;
    public Integer createOrder(OrderRequest request) {
        /// check customer -> OpenFeign
        var customer = this.customerClient.findByCustomerId(request.customerId()).orElseThrow(
                () -> new BusinessException("Can not create order:: No customer exist with he provided Customer ID: " + request.customerId())
        );

        // purchase the product --> product-ms (Rest template)
        var purchaseProducts =  this.productClient.purchaseProducts(request.products());
        // persist order
        var order = this.orderRepository.save(mapper.toOrder(request));
        // persist order lines
        for (PurchaseRequest purchaseRequest: request.products()){

            this.orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity(),
                            request.amount()
                    )
            );
        }

        // todo start payment processing
        this.paymentClient.requestOrderPayment(new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        ));
        // send order confirmation to notification -ms {Kafka}
        this.orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchaseProducts
        ));
        log.info(customer.email());
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return  this.orderRepository.findAll().stream().map(mapper::fromOrder).collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return this.orderRepository.findById(orderId).map(mapper::fromOrder).orElseThrow(
                ()  -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId))
        );
    }
}
