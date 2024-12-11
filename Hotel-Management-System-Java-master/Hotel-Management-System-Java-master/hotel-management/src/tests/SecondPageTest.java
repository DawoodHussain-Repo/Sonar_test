package tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import hotel.AdminForm;
import hotel.CustomerRestaurant;
import hotel.CustomerRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hotel.secondPage;
class SecondPageTest {

    private secondPage secondPage;
    private JButton btnNewButton;
    private JButton btnAdmin;
    private JComboBox comboBox;

    @BeforeEach
    void setUp() {
        // Initialize secondPage GUI and components
        secondPage = new secondPage();
        btnNewButton = (JButton) secondPage.contentPane.getComponent(1); // Get the CUSTOMER button
        btnAdmin = (JButton) secondPage.contentPane.getComponent(2); // Get the ADMIN button
        comboBox = (JComboBox) secondPage.contentPane.getComponent(0); // Get the ComboBox
    }

    @Test
    void testButtonLabels() {
        // Check button labels
        assertEquals("  CUSTOMER", btnNewButton.getText());
        assertEquals("ADMIN", btnAdmin.getText());
    }

    @Test
    void testComboBoxSelection() {
        // Check initial comboBox selection
        assertEquals("ROOM", comboBox.getSelectedItem().toString());

        // Set comboBox selection to RESTAURANT
        comboBox.setSelectedItem("RESTAURANT");
        assertEquals("RESTAURANT", comboBox.getSelectedItem().toString());
    }
    @Test
    void testCustomerButtonActionRoomSelected() {
        // Create the mock object for CustomerRestaurant
        CustomerRestaurant mockCustomerRestaurant = mock(CustomerRestaurant.class);

        // Assuming SecondPage is the class containing the comboBox and button
        secondPage secondPage = new secondPage();

        // Set the mock object on the class
        secondPage.setCustomerRestaurant(mockCustomerRestaurant);  // Assuming this setter method exists or modify as needed

        // Simulate "ROOM" selection in the comboBox
        comboBox.setSelectedItem("ROOM");

        // Set up the mock to do nothing on these methods
        doNothing().when(mockCustomerRestaurant).setVisible(true);
        doNothing().when(mockCustomerRestaurant).pack();
        doNothing().when(mockCustomerRestaurant).setLocationRelativeTo(null);
        doNothing().when(mockCustomerRestaurant).setBounds(anyInt(), anyInt(), anyInt(), anyInt());

        // Trigger the action by clicking the button
        btnNewButton.doClick();

        // Verify that the setVisible(true) method was called for the mockCustomerRestaurant
        verify(mockCustomerRestaurant).setVisible(true);
    }


    @Test
    void testCustomerButtonActionRestaurantSelected() {
        // Simulate "RESTAURANT" selection
        comboBox.setSelectedItem("RESTAURANT");

        // Mock CustomerRoom class to test if it is being launched correctly
        CustomerRoom mockCustomerRoom = mock(CustomerRoom.class);
        doNothing().when(mockCustomerRoom).setVisible(true);
        doNothing().when(mockCustomerRoom).pack();
        doNothing().when(mockCustomerRoom).setLocationRelativeTo(null);
        doNothing().when(mockCustomerRoom).setBounds(anyInt(), anyInt(), anyInt(), anyInt());

        // Trigger action by clicking the button
        btnNewButton.doClick();

        // Verify that the CustomerRoom window is being opened
        verify(mockCustomerRoom).setVisible(true);
    }
    @Test
    void testAdminButtonAction() {
        // Mock AdminForm to test if it is being launched correctly
        AdminForm mockAdminForm = mock(AdminForm.class);
        doNothing().when(mockAdminForm).setVisible(true);
        doNothing().when(mockAdminForm).pack();
        doNothing().when(mockAdminForm).setLocationRelativeTo(null);
        doNothing().when(mockAdminForm).setBounds(anyInt(), anyInt(), anyInt(), anyInt());

        // Trigger action
        btnAdmin.doClick();

        // Verify that the AdminForm window is being opened
        verify(mockAdminForm).setVisible(true);
    }

    @Test
    void testGUIElementsPresence() {
        // Test that GUI elements are visible and properly laid out
        assertNotNull(secondPage);
        assertTrue(btnNewButton.isVisible(), "Customer Button should be visible");
        assertTrue(btnAdmin.isVisible(), "Admin Button should be visible");
        assertTrue(comboBox.isVisible(), "ComboBox should be visible");
        assertTrue(secondPage.contentPane.getComponent(3).isVisible(), "Background image should be visible");
    }



}
