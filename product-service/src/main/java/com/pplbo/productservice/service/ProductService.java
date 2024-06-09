package com.pplbo.productservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pplbo.productservice.model.Product;
import com.pplbo.productservice.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public Product saveProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByProductName(product.getProductName());
        if (existingProduct.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists");
        }
        return productRepository.save(product);
    }

    public Product patchProduct(Long productId, Map<String, Object> updates) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        updates.forEach((key, value) -> {
            if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, key + " cannot be empty or null");
            }
            switch (key) {
                case "productName":
                    String newProductName = (String) value;
                    // Check if the new product name already exists in another product
                    Optional<Product> productByName = productRepository.findByProductName(newProductName);
                    if (productByName.isPresent() && !productByName.get().getProductId().equals(productId)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with the same name already exists");
                    }
                    existingProduct.setProductName(newProductName);
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
