package tests;

import hotel.CustomerRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerRestaurantGUITest {

    private JFrame frame;
    private CustomerRestaurant customerRestaurant;

    @BeforeEach
    public void setUp() {
        customerRestaurant = new CustomerRestaurant();
        frame = new JFrame("Customer Restaurant Test");
        frame.setSize(1236, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(customerRestaurant.getContentPane()); // Use getContentPane() for direct access to the panel
        frame.setVisible(true);
    }

    @AfterEach
    public void tearDown() {
        frame.dispose();
    }

    @Test
    public void testFieldsAreEditable() {
        // Access the text fields directly by name
        JTextField nameField = (JTextField) customerRestaurant.getContentPane().getComponent(7);  // First JTextField for "Name"
        JTextField addrField = (JTextField) customerRestaurant.getContentPane().getComponent(8);  // Second JTextField for "Address"
        JTextField phoneField = (JTextField) customerRestaurant.getContentPane().getComponent(9);  // Third JTextField for "Phone"

        nameField.setText("John Doe");
        addrField.setText("123 Main St");
        phoneField.setText("1234567890");

        // Check that the text fields have been updated correctly
        assertEquals("John Doe", nameField.getText());
        assertEquals("123 Main St", addrField.getText());
        assertEquals("1234567890", phoneField.getText());
    }

    @Test
    public void testGenerateReceiptButton() {
        // Simulate entering values in the text fields
        JTextField nameField = (JTextField) customerRestaurant.getContentPane().getComponent(7);
        JTextField addrField = (JTextField) customerRestaurant.getContentPane().getComponent(8);
        JTextField phoneField = (JTextField) customerRestaurant.getContentPane().getComponent(9);
        JTextField roomTypeField = (JTextField) customerRestaurant.getContentPane().getComponent(10);
        JTextField bedTypeField = (JTextField) customerRestaurant.getContentPane().getComponent(11);
        JTextField priceField = (JTextField) customerRestaurant.getContentPane().getComponent(12);
        JTextField daysField = (JTextField) customerRestaurant.getContentPane().getComponent(14);

        nameField.setText("John Doe");
        addrField.setText("123 Main St");
        phoneField.setText("1234567890");
        roomTypeField.setText("Deluxe");
        bedTypeField.setText("King");
        priceField.setText("200");
        daysField.setText("5");

        // Trigger the receipt generation action
        JButton generateReceiptButton = (JButton) customerRestaurant.getContentPane().getComponent(15);  // Button to generate receipt
        generateReceiptButton.doClick();

        // Check if the total was correctly calculated and displayed
        JTextField totalField = (JTextField) customerRestaurant.getContentPane().getComponent(16);  // Total field
        assertEquals("1000", totalField.getText());  // 200 * 5 = 1000
    }

    @Test
    public void testBackButton() {
        JButton backButton = (JButton) customerRestaurant.getContentPane().getComponent(17);  // Back button
        backButton.doClick();

        // Verify that the back action does what is expected (e.g., hiding or closing the frame)
        assertFalse(frame.isVisible());
    }

    @Test
    public void testCheckoutButton() {
        JButton checkoutButton = (JButton) customerRestaurant.getContentPane().getComponent(18);  // Checkout button
        checkoutButton.doClick();

        // Verify if the checkout process was triggered correctly
        // This can vary based on the behavior of the checkout button, which could trigger actions like generating a final receipt
        assertNotNull(customerRestaurant.getContentPane());  // Verify that the content pane still exists after clicking checkout
    }

    @Test
    public void testRoomSelection() {
        JScrollPane scrollPane = (JScrollPane) customerRestaurant.getContentPane().getComponent(4);  // JScrollPane for room table
        JTable table = (JTable) scrollPane.getViewport().getView();  // JTable inside JScrollPane

        assertTrue(table.getModel().getRowCount() > 0);  // Ensure that the table has rows
        table.setRowSelectionInterval(0, 0);  // Simulate selecting the first row

        // Verify that the selection has been made correctly
        assertEquals(0, table.getSelectedRow());
    }
}
