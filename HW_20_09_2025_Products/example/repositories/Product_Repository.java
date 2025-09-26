package org.example.repositories;

import org.example.models.Product;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Product_Repository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Almazcuzel2006";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver registered successfully");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("SET client_encoding = 'UTF8'");
        }

        return conn;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "sku VARCHAR(10) UNIQUE NOT NULL, " +
                "price DECIMAL(10,2) NOT NULL, " +
                "quantity INTEGER NOT NULL" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            if (!e.getMessage().contains("already exists")) {
                e.printStackTrace();
            }
        }
    }
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("PostgreSQL connection successful!");
            return true;
        } catch (SQLException e) {
            System.err.println("PostgreSQL connection failed: " + e.getMessage());
            System.err.println("Please check:");
            System.err.println("1. Is PostgreSQL running?");
            System.err.println("2. Does database 'OrisProduct' exist?");
            System.err.println("3. Are username/password correct?");
            return false;
        }
    }
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding product by id: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Product> findBySku(String sku) {
        String sql = "SELECT * FROM products WHERE sku = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sku);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding product by sku: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Product> findByName(String name) {
        String sql = "SELECT * FROM products WHERE name ILIKE ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding products by name: " + e.getMessage());
        }
        return products;
    }

    public void save(Product product) {
        String sql = "INSERT INTO products (name, sku, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSku());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                    System.out.println("Product saved with ID: " + product.getId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving product: " + e.getMessage());
            throw new RuntimeException("Error saving product", e);
        }
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBySku(String sku) {
        String sql = "DELETE FROM products WHERE sku = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sku);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product by sku: " + e.getMessage());
            return false;
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all products: " + e.getMessage());
        }
        return products;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("sku"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity")
        );
    }
}