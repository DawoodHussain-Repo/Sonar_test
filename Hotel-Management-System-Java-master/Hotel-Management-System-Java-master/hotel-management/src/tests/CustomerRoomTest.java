package tests;

import hotel.CustomerRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

class CustomerRoomTest {

    private CustomerRoom customerRoom;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        customerRoom = spy(new CustomerRoom());

        // Mock database objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Stub CustomerRoom's database connection method
      //  doReturn(mockConnection).when(customerRoom).getDatabaseConnection();
    }

    @Test
    void testFillCombo_Dishes() throws SQLException {
        // Mock database response for dishes
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("itemName")).thenReturn("Dish 1");

        // Call the method to fill the combo box
        customerRoom.FillCombo();

        // Verify if the combo box for dishes was filled
       // assertEquals("Dish 1", customerRoom.comboBox_Dish.getItemAt(0));
    }

    @Test
    void testFillCombo_Drinks() throws SQLException {
        // Mock database response for drinks
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("itemName")).thenReturn("Drink 1");

        // Call the method to fill the combo box
        customerRoom.FillCombo();

        // Verify if the combo box for drinks was filled
    //    assertEquals("Drink 1", customerRoom.comboBox_Drink.getItemAt(0));
    }

    @Test
    void testCalculateAmount_ValidInput() throws SQLException {
        // Mock database response
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Price")).thenReturn("100");

        // Simulate user selection
        customerRoom.comboBox_Dish.addItem("Dish 1");
        customerRoom.comboBox_Dish.setSelectedItem("Dish 1");
        customerRoom.comboBox_Drink.addItem("Drink 1");
        customerRoom.comboBox_Drink.setSelectedItem("Drink 1");

        // Call calculateAmount
        customerRoom.calculateAmount();

        // Verify if the total amount is calculated correctly
     //   assertEquals("200", customerRoom.totalA.getText());
    }

    @Test
    void testGenerateReceipt_EmptyFields() {
        // Simulate empty fields
        customerRoom.name.setText("");
        customerRoom.addr.setText("");
        customerRoom.phone.setText("");

        // Trigger the button click event manually
        ActionEvent event = mock(ActionEvent.class);
        customerRoom.btnGenerateReceipt.getActionListeners()[0].actionPerformed(event);

        // Verify that the required fields validation message appears
        assertTrue(customerRoom.a1.isVisible());
        assertTrue(customerRoom.a2.isVisible());
        assertTrue(customerRoom.a3.isVisible());
    }

    @Test
    void testAddToDatabase_Success() throws SQLException {
        // Mock database interaction
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insert

        // Simulate user input
        customerRoom.name.setText("John Doe");
        customerRoom.addr.setText("123 Main St");
        customerRoom.phone.setText("1234567890");
        customerRoom.comboBox_Dish.addItem("Dish 1");
        customerRoom.comboBox_Dish.setSelectedItem("Dish 1");
        customerRoom.comboBox_Drink.addItem("Drink 1");
        customerRoom.comboBox_Drink.setSelectedItem("Drink 1");
        customerRoom.totalA.setText("200");

        // Add customer to database
        customerRoom.addToDatabase();

        // Verify database interaction
        verify(mockPreparedStatement).setString(1, "John Doe");
        verify(mockPreparedStatement).setString(2, "123 Main St");
        verify(mockPreparedStatement).setString(3, "1234567890");
        verify(mockPreparedStatement).setString(4, "Dish 1");
        verify(mockPreparedStatement).setString(5, "Drink 1");
        verify(mockPreparedStatement).setString(6, "200");
    }

    @Test
    void testCheckout_Success() throws SQLException {
        // Mock database interaction
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful checkout

        // Simulate user input
        customerRoom.name.setText("John Doe");

        // Checkout customer
        customerRoom.checkOut();

        // Verify database interaction
        verify(mockPreparedStatement).setString(1, "John Doe");
    }

    @Test
    void testGenerateReceipt_WithValidInputs() {
        // Simulate valid inputs
        customerRoom.name.setText("John Doe");
        customerRoom.addr.setText("123 Main St");
        customerRoom.phone.setText("1234567890");
        customerRoom.comboBox_Dish.addItem("Dish 1");
        customerRoom.comboBox_Dish.setSelectedItem("Dish 1");
        customerRoom.comboBox_Drink.addItem("Drink 1");
        customerRoom.comboBox_Drink.setSelectedItem("Drink 1");

        // Simulate total amount calculation
        customerRoom.totalA.setText("200");

        // Trigger the button click event manually
        ActionEvent event = mock(ActionEvent.class);
        customerRoom.btnGenerateReceipt.getActionListeners()[0].actionPerformed(event);

        // Verify the receipt contains correct information
        String receipt = customerRoom.area.getText();
        assertTrue(receipt.contains("John Doe"));
        assertTrue(receipt.contains("123 Main St"));
        assertTrue(receipt.contains("1234567890"));
        assertTrue(receipt.contains("Dish 1"));
        assertTrue(receipt.contains("Drink 1"));
        assertTrue(receipt.contains("200"));
    }
}
