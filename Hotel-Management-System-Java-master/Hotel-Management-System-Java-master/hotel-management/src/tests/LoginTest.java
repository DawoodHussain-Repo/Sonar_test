package tests;

import hotel.GetConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import hotel.loginPage;
class LoginTest {

    private GetConnection mockGetConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private loginPage loginPage;

    @BeforeEach
    void setUp() throws SQLException {
        // Mock dependencies
        mockGetConnection = mock(GetConnection.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Setup behavior
        when(mockGetConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Initialize loginPage
        loginPage = new loginPage(mockGetConnection);
    }

    @Test
    void testLoginSuccess() throws SQLException {
        // Setup mock ResultSet to simulate valid user
        when(mockResultSet.next()).thenReturn(true);

        // Simulate input
        loginPage.tfusername.setText("validUser");
        loginPage.tfpwd.setText("validPassword");

        // Trigger login
        loginPage.btnLogin.doClick();

        // Verify success behavior
        assertFalse(loginPage.frame.isVisible(), "Main frame should be hidden after successful login");
        verify(mockPreparedStatement).setString(1, "validUser");
        verify(mockPreparedStatement).setString(2, "validPassword");
    }

    @Test
    void testLoginFailure() throws SQLException {
        // Setup mock ResultSet to simulate invalid user
        when(mockResultSet.next()).thenReturn(false);

        // Simulate input
        loginPage.tfusername.setText("invalidUser");
        loginPage.tfpwd.setText("invalidPassword");

        // Trigger login
        loginPage.btnLogin.doClick();

        // Verify failure behavior
        assertTrue(loginPage.frame.isVisible(), "Main frame should remain visible after failed login");
        verify(mockPreparedStatement).setString(1, "invalidUser");
        verify(mockPreparedStatement).setString(2, "invalidPassword");
    }

    @Test
    void testEmptyFieldsValidation() {
        // Simulate empty fields
        loginPage.tfusername.setText("");
        loginPage.tfpwd.setText("");

        // Trigger login
        loginPage.btnLogin.doClick();

        // Verify validation messages
        assertTrue(loginPage.Ustar.isVisible(), "Username validation star should be visible");
        assertTrue(loginPage.Pstar.isVisible(), "Password validation star should be visible");
    }

    @Test
    void testDatabaseExceptionHandling() throws SQLException {
        // Simulate SQLException
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Simulate input
        loginPage.tfusername.setText("anyUser");
        loginPage.tfpwd.setText("anyPassword");

        // Trigger login
        loginPage.btnLogin.doClick();

        // Verify frame is still visible and exception handled
        assertTrue(loginPage.frame.isVisible(), "Main frame should remain visible after exception");
    }
}
