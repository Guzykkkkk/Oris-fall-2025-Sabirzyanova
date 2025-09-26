package org.example.console;

import org.example.models.Product;
import org.example.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProductConsole {
    private final Scanner scanner;
    private final ProductService service;

    public ProductConsole(Scanner scanner, ProductService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void findBySku() {
        System.out.print("Enter product SKU: ");
        String sku = scanner.nextLine();

        try {
            Product product = service.findProductBySku(sku);
            System.out.println("found product: " + product);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteBySku() {
        System.out.print("Enter product SKU to delete: ");
        String sku = scanner.nextLine();
        try {
            service.deleteBySku(sku);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showNameAndPrice() {
        List<Product> products = service.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available");
        } else {
            System.out.println("Products (Name - Price):");
            products.forEach(p -> System.out.println(p.getName() + " - rubles:  " + p.getPrice()));
        }
    }

    public void signUp() {
        System.out.println("Enter product details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("SKU (10 characters): ");
        String sku = scanner.nextLine();

        System.out.print("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        System.out.print("Quantity: ");
        Integer quantity = Integer.parseInt(scanner.nextLine());
        service.createProduct(name, sku, price, quantity);
    }

    public void findById() {
        System.out.print("Enter product ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            Product product = service.findById(id);
            System.out.println("found product: " + product);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findByName() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        List<Product> products = service.findProductsByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found with name: " + name);
        } else {
            System.out.println("found products:");
            products.forEach(System.out::println);
        }
    }
    public void deleteById() {
        System.out.print("Enter product ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        service.deleteProductById(id);
    }
    
    public void showAllProducts() {
        List<Product> products = service.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available");
        } else {
            System.out.println("All products:");
            products.forEach(System.out::println);
        }
    }

    public void showPriceAndQuantity() {
        List<Product> products = service.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available");
        } else {
            System.out.println("Products (Price - Quantity):");
            products.forEach(p -> System.out.println("rubles:  " + p.getPrice() + " - " + p.getQuantity() + " units"));
        }
    }
}