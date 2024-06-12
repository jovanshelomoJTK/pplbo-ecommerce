package com.pplbo.productservice.controller;

import com.pplbo.productservice.event.ValidateStockEvent;
import com.pplbo.productservice.kafka.KafkaProducerService;
import com.pplbo.productservice.model.Product;
import com.pplbo.productservice.model.Category;
import com.pplbo.productservice.service.CategoryService;
import com.pplbo.productservice.service.ProductService;
import com.pplbo.productservice.dto.CreateProductRequest;

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

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductRequest request) {
        Product product = new Product();
        product.setProductName(request.productName());
        product.setPrice(request.price());
        product.setStock(request.stock());
        Category category = categoryService.getCategoryById(request.categoryId());
        product.setCategory(category);
        product.setBrandId(request.brandId().intValue());
        product.setSize(request.size());
        product.setColor(request.color());
        product.setMaterial(request.material());
        product.setProductDesc(request.productDesc());
        product.setProductImage(request.productImage());

        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long productId, @RequestBody CreateProductRequest request) {
        Product updates = new Product();
        updates.setProductName(request.productName());
        updates.setPrice(request.price());
        updates.setStock(request.stock());
        Category category = categoryService.getCategoryById(request.categoryId());
        updates.setCategory(category);
        updates.setBrandId(request.brandId().intValue());
        updates.setSize(request.size());
        updates.setColor(request.color());
        updates.setMaterial(request.material());
        updates.setProductDesc(request.productDesc());
        updates.setProductImage(request.productImage());
        Product updatedProduct = productService.patchProduct(productId, request);

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

    // @GetMapping("/product-category/{categoryId}")
    // public List<Product> getProductsByCategoryId(@PathVariable Integer categoryId) {
    //     return productService.getProductsByCategoryId(categoryId);
    // }

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
