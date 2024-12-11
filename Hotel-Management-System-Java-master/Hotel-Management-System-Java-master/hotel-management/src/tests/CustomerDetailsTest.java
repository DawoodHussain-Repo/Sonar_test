package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hotel.Customer;
import hotel.CustomerDetails;
import hotel.GetConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDetailsTest {

    private CustomerDetails customerDetails;
    private GetConnection mockConnection;
    private Connection mockDBConnection;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        // Mock database components
        mockConnection = mock(GetConnection.class);
        mockDBConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        // Set up mocked behavior
        when(mockConnection.getConnection()).thenReturn(mockDBConnection);
        when(mockDBConnection.createStatement()).thenReturn(mockStatement);

        // Initialize CustomerDetails instance
        customerDetails = new CustomerDetails();
    }

    @Test
    void testInitialization() {
        // Test if all UI components are initialized
        assertNotNull(customerDetails.getContentPane());
        assertNotNull(customerDetails.getTitle());
        assertNotNull(customerDetails.getBounds());

        // Check if table and text fields exist
        assertNotNull(customerDetails.table);
        assertNotNull(customerDetails.textField);
        assertNotNull(customerDetails.textField_1);
        assertNotNull(customerDetails.textField_2);
    }

    @Test
    void testAddButtonValidation() {
        // Simulate pressing the "ADD" button with empty fields
        customerDetails.textField.setText("");
        customerDetails.textField_1.setText("");

        ActionEvent addEvent = mock(ActionEvent.class);
        customerDetails.btnAdd.doClick();

        // Verify the asterisks for validation are displayed
        assertTrue(customerDetails.a1.isVisible());
        assertTrue(customerDetails.a2.isVisible());
    }

    @Test
    void testDisplayCustomers() throws Exception {
        // Mock database response
        when(mockStatement.executeQuery("SELECT * FROM customer")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // First call returns data, second ends loop
        when(mockResultSet.getString("customerID")).thenReturn("1");
        when(mockResultSet.getString("customerName")).thenReturn("John Doe");
        when(mockResultSet.getString("address")).thenReturn("123 Main St");
        when(mockResultSet.getString("phoneNo")).thenReturn("1234567890");
        when(mockResultSet.getString("type")).thenReturn("Regular");

        // Call displayCustomers
        customerDetails.displayCustomers();

        // Verify table data
        JTable table = customerDetails.table;
        assertEquals(1, table.getRowCount());
        assertEquals("John Doe", table.getValueAt(0, 1));
        assertEquals("123 Main St", table.getValueAt(0, 2));
        assertEquals("1234567890", table.getValueAt(0, 3));
        assertEquals("Regular", table.getValueAt(0, 4));
    }

    @Test
    void testDeleteButtonAction() throws Exception {
        // Simulate pressing the "DELETE" button
        customerDetails.textField.setText("John Doe");

        // Mock delete behavior
        Customer mockCustomer = Mockito.mock(Customer.class);
        doNothing().when(mockCustomer).deleteCustomer(anyString(), any(Customer.class));

        ActionEvent deleteEvent = mock(ActionEvent.class);
        customerDetails.btnDelete.doClick();

        // Verify that the delete method was called
        verify(mockCustomer, times(1)).deleteCustomer("d", mockCustomer);
    }

    @Test
    void testPhoneFieldValidation() {
        // Simulate entering non-numeric characters
        customerDetails.textField_2.setText("Invalid123");
        KeyEvent keyEvent = new KeyEvent(customerDetails.textField_2, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 'a');

        customerDetails.textField_2.getKeyListeners()[0].keyTyped(keyEvent);

        // Verify only numeric characters are allowed
        assertEquals("", customerDetails.textField_2.getText());
    }
}
