package org.example.console;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private ProductConsole productConsole;

    public Menu(Scanner scanner, ProductConsole productConsole) {
        this.scanner = scanner;
        this.productConsole = productConsole;
    }

    public void run() {
        try {
            while (true) {
                printMenu();
                if (!scanner.hasNextLine()) {
                    System.out.println("Input stream closed. Exiting...");
                    break;
                }

                String input = scanner.nextLine();

                if (input.trim().isEmpty()) {
                    continue;
                }

                switch (input) {
                    case "1": productConsole.findById(); break;
                    case "2": productConsole.findBySku(); break;
                    case "3": productConsole.deleteBySku(); break;
                    case "4": productConsole.deleteById(); break;
                    case "5": productConsole.findByName(); break;
                    case "6": productConsole.signUp(); break;
                    case "7": productConsole.showPriceAndQuantity(); break;
                    case "8": productConsole.showAllProducts(); break;
                    case "9": productConsole.showNameAndPrice(); break;
                    case "0":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Error in menu: " + e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("=== PRODUCT MANAGEMENT SYSTEM ===");
        System.out.println("1 - Find by ID");
        System.out.println("2 - Find by SKU");
        System.out.println("3 - Delete by SKU");
        System.out.println("4 - Delete by ID");
        System.out.println("5 - Find by Name");
        System.out.println("6 - Create product");
        System.out.println("7 - Show price and quantity");
        System.out.println("8 - Show all products");
        System.out.println("9 - Show name and price");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");
    }
}