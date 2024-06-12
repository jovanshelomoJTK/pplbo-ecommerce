package com.pplbo.productservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pplbo.productservice.event.ProductOutOfStockEvent;
import com.pplbo.productservice.dto.CreateProductRequest;
import com.pplbo.productservice.event.OrderNotValidatedEvent;
import com.pplbo.productservice.event.OrderValidatedEvent;
import com.pplbo.productservice.event.ValidateStockEvent;
import com.pplbo.productservice.kafka.KafkaProducerService;
import com.pplbo.productservice.model.Product;
import com.pplbo.productservice.model.Category;
import com.pplbo.productservice.repository.CategoryRepository;
import com.pplbo.productservice.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            Long categoryId = product.getCategory() != null ? product.getCategory().getCategoryId() : null;
            product.setCategory(categoryRepository.findById(categoryId).orElse(null));
        });
        return products;
    }

    public Product getProductById(Long productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Long categoryId = product.getCategory() != null ? product.getCategory().getCategoryId() : null;
        product.setCategory(categoryRepository.findById(categoryId).orElse(null));
        return product;
    }

    public Product saveProduct(Product product) {
        // Save the product
        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return category.getProducts();
    }

    public Product patchProduct(Long productId, CreateProductRequest updates) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (updates.productName() != null && !updates.productName().isEmpty()) {
            // Check if the new product name already exists in another product
            Optional<Product> productByName = productRepository.findByProductName(updates.productName());
            if (productByName.isPresent() && !productByName.get().getProductId().equals(productId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with the same name already exists");
            }
            existingProduct.setProductName(updates.productName());
        }
        if (updates.price() != null) {
            existingProduct.setPrice(updates.price());
        }
        if (updates.stock() != null) {
            existingProduct.setStock(updates.stock());
        }
        if (updates.categoryId() != null) {
            Category category = categoryRepository.findById(updates.categoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            existingProduct.setCategory(category);
        }
        if (updates.brandId() != null) {
            existingProduct.setBrandId(updates.brandId().intValue());
        }
        if (updates.size() != null) {
            existingProduct.setSize(updates.size());
        }
        if (updates.color() != null) {
            existingProduct.setColor(updates.color());
        }
        if (updates.material() != null) {
            existingProduct.setMaterial(updates.material());
        }
        if (updates.productDesc() != null) {
            existingProduct.setProductDesc(updates.productDesc());
        }
        if (updates.productImage() != null) {
            existingProduct.setProductImage(updates.productImage());
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(product);
    }

    public List<Product> searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameContaining(keyword);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found with the given keyword");
        }
        return products;
    }

    // Metode baru untuk mengecek stok dan mengirim event jika stok habis saat cart service memanggil
    public void checkStockAndNotify(Long productId) {
        Product product = getProductById(productId);
        if (product.getStock() <= 0) {
            ProductOutOfStockEvent event = new ProductOutOfStockEvent(
                product.getProductId(),
                product.getProductName(),
                product.getStock()
            );
            kafkaProducerService.sendMessage(event);
            throw new ResponseStatusException(HttpStatus.OK, "Product is out of stock");
        }
    }

    @KafkaListener(topics = "ValidateStockEvent", groupId = "product_group")
    public void consumeValidateStockEvent(ValidateStockEvent event) {
        Product product = productRepository.findById(event.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStock() >= event.getQuantity()) {
            product.setStock(product.getStock() - event.getQuantity());
            productRepository.save(product);
            kafkaProducerService.sendOrderValidatedEvent(new OrderValidatedEvent(event.getProductId(), event.getQuantity()));
        } else {
            kafkaProducerService.sendOrderNotValidatedEvent(new OrderNotValidatedEvent(event.getProductId(), event.getQuantity()));
        }
    }
}
