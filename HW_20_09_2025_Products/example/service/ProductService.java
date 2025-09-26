package org.example.service;

import org.example.models.Product;
import org.example.repositories.Product_Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private static final int SKU_LENGTH = 10;
    private final Product_Repository productRepository;

    public ProductService(Product_Repository productRepository) {
        this.productRepository = productRepository;

        if (productRepository.testConnection()) {
            System.out.println("Database connection successful, creating table...");
            this.productRepository.createTable();
        } else {
            System.err.println("Cannot connect to database. Some features may not work.");
        }
    }
    public void createProduct(String name, String sku, BigDecimal price, Integer quantity) {
        validateSku(sku);

        Product product = new Product(name, sku, price, quantity);
        productRepository.save(product);
        System.out.println("Product created successfully: " + product.getName());
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() ->
                new IllegalArgumentException("Product not found with id: " + id));
    }

    public Product findProductBySku(String sku) {
        Optional<Product> product = productRepository.findBySku(sku);
        return product.orElseThrow(() ->
                new IllegalArgumentException("Product not found with SKU: " + sku));
    }

    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public void deleteProductById(Long id) {
        Product product = findById(id);
        if (productRepository.deleteById(id)) {
            System.out.println("Product deleted: " + product.getName());
        } else {
            throw new IllegalArgumentException("Failed to delete product with id: " + id);
        }
    }

    public void deleteBySku(String sku) {
        Product product = findProductBySku(sku);
        if (productRepository.deleteBySku(sku)) {
            System.out.println("Product deleted: " + product.getName());
        } else {
            throw new IllegalArgumentException("Failed to delete product with SKU: " + sku);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private void validateSku(String sku) {
        if (sku == null || sku.length() != SKU_LENGTH) {
            throw new IllegalArgumentException("Product SKU must be exactly " + SKU_LENGTH + " characters");
        }
    }
}