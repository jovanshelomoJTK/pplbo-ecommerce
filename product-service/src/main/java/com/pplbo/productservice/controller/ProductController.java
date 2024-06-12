package com.pplbo.productservice.controller;

import java.util.Map;

import com.pplbo.productservice.event.ValidateStockEvent;
import com.pplbo.productservice.kafka.KafkaProducerService;
import com.pplbo.productservice.model.Product;
import com.pplbo.productservice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long productId, @RequestBody Map<String, Object> updates) {
        Product updatedProduct = productService.patchProduct(productId, updates);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.searchProductsByKeyword(keyword);
    }

    @GetMapping("/product-category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable Integer categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/check-stock/{productId}")
    public ResponseEntity<Void> checkStockAndNotify(@PathVariable Long productId) {
        productService.checkStockAndNotify(productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/testkafka/{id}")
    public String testKafka(@PathVariable Long id, @RequestParam int quantity) {
        ValidateStockEvent event = new ValidateStockEvent(id, quantity);
        kafkaProducerService.sendValidateStockEvent(event);
        productService.consumeValidateStockEvent(event);
        return "Kafka event sent and processed successfully";
    }
}
