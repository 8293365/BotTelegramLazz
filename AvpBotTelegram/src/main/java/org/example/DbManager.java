package org.example;

import java.sql.*;

public class DbManager {

    private static final String URL = "jdbc:mysql://localhost:3306/AvpbotDB";
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = null; // Replace with your MySQL password
    private  Connection connection;

    public DbManager() {
        try {
            // Forcefully load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully.");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }




    // Method to insert a Maker
    public void insertMaker(String nameM) {
        String sql = "INSERT INTO Maker (NameM) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nameM);
            stmt.executeUpdate();
            System.out.println("Maker inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting maker: " + e.getMessage());
        }
    }

    // Method to insert a Product
    public void insertProduct(String nameP, String image, String productCode, String lastUpdate, String pdfLink, String category) {
        String sql = "INSERT INTO Products (NameP, image, productCode, lastUpdate, pdfLink, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nameP);
            stmt.setString(2, image);
            stmt.setString(3, productCode);
            stmt.setDate(4, Date.valueOf(lastUpdate)); // Ensure the date is in "yyyy-MM-dd" format
            stmt.setString(5, pdfLink);
            stmt.setString(6, category);
            stmt.executeUpdate();
            System.out.println("Product inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting product: " + e.getMessage());
        }
    }

    // Method to fetch all Products
    public void fetchProducts() {
        String sql = "SELECT * FROM Products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Product Code: " + rs.getString("productCode"));
                System.out.println("NameP: " + rs.getString("NameP"));
                System.out.println("Image: " + rs.getString("image"));
                System.out.println("Last Update: " + rs.getDate("lastUpdate"));
                System.out.println("PDF Link: " + rs.getString("pdfLink"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }
    }

    public void fetchProduct(String ProductCodeOrName){//returns arraylist of row if found else returns null
        String sqlCode = "SELECT * FROM Products WHERE productCode = ";
        String sqlNameP = "SELECT * FROM Products WHERE nameP = ";//so I need to make two calls how do i do that??
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlCode+ ProductCodeOrName +";")) {
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlNameP+ ProductCodeOrName +";")) {
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }

    }

    // Close the connection
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    
    public static void main(String[] args) {
        DbManager DbManager = new DbManager();

        // Example usage
        DbManager.insertMaker("MakerName1");
        DbManager.insertProduct("ProductName1", "image.png", "P001", "2023-12-23", "link.pdf", "category1");
        DbManager.fetchProducts();

        DbManager.close();
    }
}
