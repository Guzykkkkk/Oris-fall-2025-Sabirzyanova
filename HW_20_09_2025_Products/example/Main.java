package org.example;

import org.example.console.Menu;
import org.example.console.ProductConsole;
import org.example.repositories.Product_Repository;
import org.example.service.ProductService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Product_Repository repository = new Product_Repository();
        ProductService service = new ProductService(repository);
        Scanner scanner = new Scanner(System.in);
        ProductConsole console = new ProductConsole(scanner, service);
        Menu menu = new Menu(scanner, console);

        System.out.println("Welcome to Product Management System!");
        menu.run();
        scanner.close();

    }
}