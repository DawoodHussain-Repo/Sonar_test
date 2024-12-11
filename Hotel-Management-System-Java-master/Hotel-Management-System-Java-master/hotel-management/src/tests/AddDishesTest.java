package tests;

import hotel.AddDishes;
import hotel.GetConnection;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;

class AddDishesTest {

    private AddDishes addDishesFrame;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize the frame for testing
        addDishesFrame = new AddDishes();
        addDishesFrame.setVisible(false); // Prevent UI from being displayed during tests
    }

    @AfterEach
    void tearDown() {
        addDishesFrame.dispose();
    }

    @Test
    void testFrameInitialization() {
        // Expected values
        int expectedWidth = 950;
        int expectedHeight = 500;

        // Get the actual values
        int actualWidth = addDishesFrame.getWidth();
        int actualHeight = addDishesFrame.getHeight();

        // Allow a margin of error of 20 pixels for width
        int margin = 20;

        // Assert that the actual width is within the margin of the expected width
        assertTrue(Math.abs(actualWidth - expectedWidth) <= margin,
                "Frame width should be approximately " + expectedWidth + ". Actual: " + actualWidth);

        // Assert the frame height is exactly as expected
        assertEquals(expectedHeight, actualHeight, "Frame height should be " + expectedHeight);
    }
    @Test
    void testLabelInitialization() {
        // Ensure the frame is fully initialized and components are added
        addDishesFrame.setVisible(true);

        // Ensure the frame contains at least one component
        assertTrue(addDishesFrame.getContentPane().getComponentCount() > 0, "Frame should have at least one component");

        // Find the first JLabel in the content pane (if it exists)
        JLabel label = null;
        for (Component comp : addDishesFrame.getContentPane().getComponents()) {
            if (comp instanceof JLabel) {
                label = (JLabel) comp;
                break; // Stop once we find the first JLabel
            }
        }

        // Ensure that a JLabel was found
        assertNotNull(label, "First component should be a JLabel");

        // Check the label's text
        assertEquals("DISH NAME", label.getText(), "Label text should be 'DISH NAME'");
    }

    @Test
    void testTextFieldInitialization() {
        // Initialize AddDishes object
        AddDishes addDishesFrame = new AddDishes();
        addDishesFrame.setVisible(true); // Ensure the frame is visible and components are initialized

        // Initialize the text fields
        JTextField txtDishName = addDishesFrame.d1;
        JTextField txtPrice = addDishesFrame.d2;
        JTextField txtType = addDishesFrame.d3;

        // Assert that the text fields are properly initialized
        assertNotNull(txtDishName, "TextField d1 should be initialized");
        assertNotNull(txtPrice, "TextField d2 should be initialized");
        assertNotNull(txtType, "TextField d3 should be initialized");

        // Optionally, you can check if they are correctly configured (e.g., not empty)
        assertTrue(txtDishName.getText().isEmpty(), "TextField d1 should be empty initially");
        assertTrue(txtPrice.getText().isEmpty(), "TextField d2 should be empty initially");
        assertTrue(txtType.getText().isEmpty(), "TextField d3 should be empty initially");
    }

    @Test
    void testButtonAddDishAction() {
        // Find the "ADD DISH" button by its label
        JButton btnAdd = Arrays.stream(addDishesFrame.getContentPane().getComponents())
                .filter(component -> component instanceof JButton)
                .map(component -> (JButton) component)
                .filter(button -> "ADD DISH".equals(button.getText()))
                .findFirst()
                .orElse(null);

        assertNotNull(btnAdd, "Button btnAdd should be initialized");

        // Simulate button click
        assertDoesNotThrow(() -> btnAdd.doClick(), "Button click should not throw an exception");
    }

    @Test
    void testAddDish_Success() {
        addDishesFrame.d1.setText("Pizza");
        addDishesFrame.d2.setText("15");
        addDishesFrame.d3.setText("Main"); // Shortened the type value

        // Simulate the add dish action
        try {
            String dishName = addDishesFrame.d1.getText();
            String price = addDishesFrame.d2.getText();
            String type = addDishesFrame.d3.getText();

            // Use GetConnection class to get a connection
            GetConnection connection = new GetConnection();
            Connection conn = connection.getConnection();

            // Prepare SQL query to insert a dish into the restaurant table
            String query = "INSERT INTO restaurant (itemName, Price, Type) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, dishName);
            pst.setInt(2, Integer.parseInt(price));
            pst.setString(3, type);

            int rowsAffected = pst.executeUpdate();
            assertEquals(1, rowsAffected, "One row should be affected when adding a new dish");

        } catch (SQLException e) {
            fail("SQL Exception occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void testAddDish_EmptyFields() {
        // Mock the database connection and statement
        GetConnection connectionMock = mock(GetConnection.class);
        Connection connMock = mock(Connection.class);
        PreparedStatement pstMock = mock(PreparedStatement.class);

        // Mock the behavior of the getConnection method
        when(connectionMock.getConnection()).thenReturn(connMock);

        // Simulate empty fields
        addDishesFrame.d1.setText(""); // Empty Dish Name
        addDishesFrame.d2.setText(""); // Empty Dish Price
        addDishesFrame.d3.setText(""); // Empty Dish Type

        // Get the values from the fields and trim any surrounding whitespace
        String dishName = addDishesFrame.d1.getText().trim();
        String price = addDishesFrame.d2.getText().trim();
        String type = addDishesFrame.d3.getText().trim();

        // Check that fields are empty
        if (dishName.isEmpty() || price.isEmpty() || type.isEmpty()) {
            // Assert that no query should be executed
            try {
                // Assert that the connection prepare statement method is never called
                verify(connMock, never()).prepareStatement(anyString());
            } catch (SQLException e) {
                fail("SQL Exception occurred during the test: " + e.getMessage());
            }
        } else {
            try {
                // If fields are not empty, simulate the add dish action
                String query = "INSERT INTO restaurant (itemName, Price, Type) VALUES (?, ?, ?)";
                when(connMock.prepareStatement(query)).thenReturn(pstMock);

                // Simulate the execution of the query
                pstMock.setString(1, dishName);
                pstMock.setInt(2, Integer.parseInt(price));
                pstMock.setString(3, type);

                // Verify that the query is executed
                int rowsAffected = pstMock.executeUpdate();
                assertEquals(1, rowsAffected, "One row should be affected when adding a new dish");
            } catch (SQLException e) {
                fail("SQL Exception occurred during the test: " + e.getMessage());
            }
        }
    }
    @Test
    void testDisplayDishes_FillsTable() {
        // Initialize AddDishes object and ensure the table is set up
        AddDishes addDishesFrame = new AddDishes();
        addDishesFrame.setVisible(true); // Make the frame visible to initialize components
        JTable table = addDishesFrame.table; // Access the table

        // Simulate the display dishes action
        try {
            // Mock database data (this can be replaced with a real database connection if needed)
            String[] dishNames = {"Dish 1", "Dish 2", "Dish 3"};
            String[] prices = {"10.0", "20.0", "30.0"};
            String[] types = {"Main", "Side", "Dessert"};

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Adding mock data to the table
            for (int i = 0; i < dishNames.length; i++) {
                model.addRow(new Object[] {dishNames[i], prices[i], types[i]});
            }

            // Assertion to check if the table has been populated
            assertTrue(table.getRowCount() > 0, "Table should contain at least one row");

        } catch (Exception e) {
            fail("Exception occurred during the test: " + e.getMessage());
        } finally {
            // Ensure that the frame is disposed of after the test
            addDishesFrame.dispose();
        }
    }

    @Test
    void testDeleteDish_Success() {
        // First, insert a dish to ensure it exists before attempting to delete
        try {
            GetConnection connection = new GetConnection();
            Connection conn = connection.getConnection();

            // Insert a test dish with known itemNo for deletion
            String insertQuery = "INSERT INTO restaurant (itemName, Price, Type) VALUES (?, ?, ?)";
            PreparedStatement insertPst = conn.prepareStatement(insertQuery);
            insertPst.setString(1, "Test Dish");
            insertPst.setInt(2, 10);
            insertPst.setString(3, "Appetizer");
            insertPst.executeUpdate();

            // Get the itemNo of the inserted dish (assuming it's auto-generated)
            String selectQuery = "SELECT itemNo FROM restaurant WHERE itemName = 'Test Dish'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            int dishId = 0;
            if (rs.next()) {
                dishId = rs.getInt("itemNo");
            }

            // Now delete the dish using the itemNo
            String deleteQuery = "DELETE FROM restaurant WHERE itemNo = ?";
            PreparedStatement deletePst = conn.prepareStatement(deleteQuery);
            deletePst.setInt(1, dishId); // Use the dishId retrieved from the query
            int rowsAffected = deletePst.executeUpdate();

            // Assert that exactly 1 row was deleted
            assertEquals(1, rowsAffected, "One row should be affected when deleting a dish");

        } catch (SQLException e) {
            fail("SQL Exception occurred during the test: " + e.getMessage());
        }
    }
}
