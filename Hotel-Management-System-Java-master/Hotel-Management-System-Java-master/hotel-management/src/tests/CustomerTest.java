package tests;

import hotel.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Connection connection;
    private Statement statement;

    @BeforeEach
    void setUp() throws Exception {
        // Create an in-memory H2 database connection
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        statement = connection.createStatement();

        // Drop table if it exists and create a new one
        statement.execute("DROP TABLE IF EXISTS customer");
        statement.execute(
                "CREATE TABLE customer (" +
                        "customerID INT AUTO_INCREMENT PRIMARY KEY," +
                        "customerName VARCHAR(255)," +
                        "address VARCHAR(255)," +
                        "phoneNo VARCHAR(20)," +
                        "type VARCHAR(50)" +
                        ")"
        );
    }

    @Test
    void testAddCustomer() throws Exception {
        // Arrange
        String insertQuery = "INSERT INTO customer (customerName, address, phoneNo, type) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, "John Doe");
        preparedStatement.setString(2, "123 Main St");
        preparedStatement.setString(3, "1234567890");
        preparedStatement.setString(4, "Regular");
        preparedStatement.executeUpdate();

        // Act
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer WHERE customerName = 'John Doe'");

        // Assert
        assertTrue(resultSet.next());
        assertEquals("John Doe", resultSet.getString("customerName"));
        assertEquals("123 Main St", resultSet.getString("address"));
        assertEquals("1234567890", resultSet.getString("phoneNo"));
        assertEquals("Regular", resultSet.getString("type"));
    }


    @Test
    void testDeleteCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer(0, "Jane Doe", "456 Elm St", "0987654321", "Premium");
        customer.addCustomer("i", customer);

        // Act
        customer.deleteCustomer("d", customer);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer WHERE customerName = 'Jane Doe'");

        // Assert
        assertFalse(resultSet.next());
    }

    @Test
    void testPrintCustomer() {
        // Arrange
        Customer customer = new Customer(0, "Alice", "789 Oak St", "1112223333", "VIP");

        // Act
        customer.printCustomer();

        // Assert
        assertEquals("Alice", customer.custName);
        assertEquals("789 Oak St", customer.addr);
        assertEquals("1112223333", customer.phone);
        assertEquals("VIP", customer.Type);
    }
}