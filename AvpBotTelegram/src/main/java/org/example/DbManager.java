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
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("Product Code: " + resultSet.getString("productCode"));
                System.out.println("NameP: " + resultSet.getString("NameP"));
                System.out.println("Image: " + resultSet.getString("image"));
                System.out.println("Last Update: " + resultSet.getDate("lastUpdate"));
                System.out.println("PDF Link: " + resultSet.getString("pdfLink"));
                System.out.println("Category: " + resultSet.getString("category"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }
    }
//toh ora faccio una funzione wrapper per le stramaledette query
    public String fetchProduct(String ProductCodeOrName) throws SQLException {//returns arraylist of row if found else returns null
        String[] columns = {"nameP, productCode"};
        boolean running = true;
        for (String s : columns) {
            ResultSet rs = fetch(ProductCodeOrName, s, "*");
            if (rs != null){
                boolean upToDate = checkDate(ProductCodeOrName, "lastUpdate");
                if(!upToDate){ return "2: Product Data outdated";}

                try {
                    return rs.getString("pdflink");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "empty";
    }

    protected Boolean checkDate(String ProdName, String column){//returns
        Statement stmt;
        try {
            //manual oracle says " The following code fragment, in which con is a valid Connection object, illustrates how to make a result
            // set that is scrollable and insensitive to updates by others, and that is updatable. See ResultSet fields for other options."
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.executeQuery("SELECT EXISTS (\n" +
                    "    SELECT "+ ProdName +" \n" +
                    "    FROM Products \n" +
                    "    WHERE DATEDIFF(NOW(),"+column+") > 30\n" +
                    ") AS is_older_than_30_days;\n");
            if(stmt.toString().equals("TRUE")){
                return true;
            }else if(stmt.toString().equals("FALSE")){
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }

    }
    protected ResultSet fetch(String str, String column, String products){
        String queryStr = "SELECT "+products+" FROM Products WHERE "+ column + " = ";//so I need to make two calls how do i do that??
        Statement stmt;
            try {
                //manual oracle says " The following code fragment, in which con is a valid Connection object, illustrates how to make a result
                // set that is scrollable and insensitive to updates by others, and that is updatable. See ResultSet fields for other options."
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
                return stmt.executeQuery(queryStr + str +";");
        } catch (SQLException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            return null;
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
