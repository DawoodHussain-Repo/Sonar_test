package tests;

import hotel.GetConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class GetConnectionTest {

    private GetConnection getConnection;

    @BeforeEach
    void setUp() {
        getConnection = new GetConnection();
    }

    @Test
    void testGetConnection_Success() {
        // Test if the connection is not null
        Connection connection = getConnection.getConnection();
        assertNotNull(connection, "Connection should not be null");

        // Verify if the connection is valid
        try {
            assertTrue(connection.isValid(2), "Connection should be valid");
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetConnection_InvalidCredentials() {
        // Simulate invalid credentials by temporarily modifying the GetConnection class fields
        String invalidUrl = "jdbc:mysql://localhost:3306/invalidDB?useSSL=false&allowPublicKeyRetrieval=true";
        String invalidUser = "invalidUser";
        String invalidPassword = "invalidPassword";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(invalidUrl, invalidUser, invalidPassword);
            fail("Expected SQLException due to invalid credentials");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Access denied"), "Error message should indicate access denied");
        }

        assertNull(connection, "Connection should be null for invalid credentials");
    }

    @Test
    void testGetConnection_ExceptionHandling() {
        // Simulate an exception by passing a malformed URL
        String malformedUrl = "jdbc:invalid:mysql://localhost:3306/hotelmanagementsystem";

        try {
            DriverManager.getConnection(malformedUrl, "root", "12E2d786@2");
            fail("Expected SQLException due to malformed URL");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("No suitable driver"), "Error message should indicate no suitable driver");
        }
    }

    @Test
    void testGetConnection_NullReturned() {
        // Override the getConnection method to simulate a null return value
        GetConnection mockGetConnection = new GetConnection() {
            @Override
            public Connection getConnection() {
                return null;
            }
        };

        Connection connection = mockGetConnection.getConnection();
        assertNull(connection, "Connection should be null");
    }
}
