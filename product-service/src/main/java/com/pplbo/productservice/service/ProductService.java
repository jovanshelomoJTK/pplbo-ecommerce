package com.pplbo.productservice.service;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pplbo.productservice.model.Product;
import com.pplbo.productservice.repository.ProductRepository;
import org.springframework.http.HttpStatus;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findByProductId(productId);
        return product.get();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product patchProduct(Long productId, Map<String, Object> updates) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "productName":
                    existingProduct.setProductName((String) value);
                    break;
                case "price":
                    existingProduct.setPrice((Integer) value);
                    break;
                case "stock":
                    existingProduct.setStock((Integer) value);
                    break;
                case "categoryId":
                    existingProduct.setCategoryId((Integer) value);
                    break;
                case "brandId":
                    existingProduct.setBrandId((Integer) value);
                    break;
                case "productDesc":
                    existingProduct.setProductDesc((String) value);
                    break;
                case "productImage":
                    existingProduct.setProductImage((String) value);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid field: " + key);
            }
        });

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(product);
    }
}
