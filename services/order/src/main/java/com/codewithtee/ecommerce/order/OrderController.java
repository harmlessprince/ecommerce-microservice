package com.codewithtee.ecommerce.order;


import com.codewithtee.ecommerce.customer.CustomerClient;
import com.codewithtee.ecommerce.exception.BusinessException;
import com.codewithtee.ecommerce.kafka.OrderConfirmation;
import com.codewithtee.ecommerce.kafka.OrderProducer;
import com.codewithtee.ecommerce.orderline.OrderLineRequest;
import com.codewithtee.ecommerce.orderline.OrderLineService;
import com.codewithtee.ecommerce.product.ProductClient;
import com.codewithtee.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(this.orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(this.orderService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("orderId") Integer orderId) {
        return ResponseEntity.ok(this.orderService.findById(orderId));
    }
}
