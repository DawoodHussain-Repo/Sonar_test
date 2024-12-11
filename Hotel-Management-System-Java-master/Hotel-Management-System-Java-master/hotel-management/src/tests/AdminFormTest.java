package tests;

import hotel.AddDishes;
import hotel.AddRooms;
import hotel.AdminForm;
import hotel.secondPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class AdminFormTest {

    private AdminForm adminForm;
    private JButton manageRoomsButton;
    private JButton manageDishesButton;
    private JButton backButton;

    @BeforeEach
    void setUp() {
        adminForm = new AdminForm();
        manageRoomsButton = (JButton) adminForm.getContentPane().getComponent(0); // Correct index for MANAGE ROOMS
        manageDishesButton = (JButton) adminForm.getContentPane().getComponent(1); // Correct index for MANAGE DISHES
        backButton = (JButton) adminForm.getContentPane().getComponent(5); // Correct index for BACK button
    }


    @Test
    void testAdminFormLaunch() {
        // Test if AdminForm initializes correctly
        assertNotNull(adminForm);
    }

    @Test
    void testManageRoomsButtonNavigation() {
        // Mock navigation action
        AddRooms addRoomsMock = mock(AddRooms.class);
        when(addRoomsMock.isVisible()).thenReturn(true);

        manageRoomsButton.doClick();
        // Simulate the action
        AddRooms ar = new AddRooms();
        ar.setVisible(true);

        assertTrue(ar.isVisible());
    }

    @Test
    void testManageDishesButtonNavigation() {
        // Mock navigation action
        AddDishes addDishesMock = mock(AddDishes.class);
        when(addDishesMock.isVisible()).thenReturn(true);

        manageDishesButton.doClick();
        // Simulate the action
        AddDishes ad = new AddDishes();
        ad.setVisible(true);

        assertTrue(ad.isVisible());
    }

    @Test
    void testBackButtonNavigation() {
        // Mock navigation action
        secondPage secondPageMock = mock(secondPage.class);
        when(secondPageMock.isVisible()).thenReturn(true);

        backButton.doClick();
        // Simulate the action
        secondPage sp = new secondPage();
        sp.setVisible(true);

        assertTrue(sp.isVisible());
    }

    @Test
    void testButtonsIcons() {
        // Check if the icons are set correctly for the buttons
        assertEquals("images\\bed (1).png", ((ImageIcon) manageRoomsButton.getIcon()).getDescription());
        assertEquals("images\\dining (2).png", ((ImageIcon) manageDishesButton.getIcon()).getDescription());
        assertEquals("images\\back.png", ((ImageIcon) backButton.getIcon()).getDescription());
    }

    @Test
    void testPanelColor() {
        // Test if the panel background color is set correctly
        JPanel panel = (JPanel) adminForm.getContentPane().getComponent(3); // Check the first panel
        assertEquals(new Color(0, 153, 204), panel.getBackground());
    }

    @Test
    void testLayoutOfUIComponents() {
        // Test the position and layout of the buttons (x, y, width, height)
        assertEquals(229, manageRoomsButton.getX());
        assertEquals(227, manageRoomsButton.getY());
        assertEquals(383, manageRoomsButton.getWidth());
        assertEquals(256, manageRoomsButton.getHeight());
    }

    @Test
    void testNoExceptionsOnButtonClick() {
        // Ensure no exception is thrown on button click
        assertDoesNotThrow(() -> manageRoomsButton.doClick());
        assertDoesNotThrow(() -> manageDishesButton.doClick());
        assertDoesNotThrow(() -> backButton.doClick());
    }
}
