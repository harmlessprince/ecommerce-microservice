package com.codewithtee.ecommerce.product;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> createProduct(@RequestBody @Valid List<ProductPurchaseRequests> request){
        return ResponseEntity.ok(productService.purchaseProducts(request));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> createProduct(@PathVariable("productId") Integer productId){
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> createProduct(){
        return ResponseEntity.ok(productService.findAll());
    }

}
