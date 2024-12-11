package tests;

import hotel.AddRooms;
import hotel.GetConnection;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;

class AddRoomsTest {

    private AddRooms addRoomsFrame;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize the frame for testing
        addRoomsFrame = new AddRooms();
        addRoomsFrame.setVisible(false); // Prevent UI from being displayed during tests
    }

    @AfterEach
    void tearDown() {
        addRoomsFrame.dispose();
    }

    @Test
    void testFrameInitialization() {
        int expectedWidth = 965;
        int expectedHeight = 577;

        int actualWidth = addRoomsFrame.getWidth();
        int actualHeight = addRoomsFrame.getHeight();

        int margin = 20;
        assertTrue(Math.abs(actualWidth - expectedWidth) <= margin,
                "Frame width should be approximately " + expectedWidth + ". Actual: " + actualWidth);
        assertEquals(expectedHeight, actualHeight, "Frame height should be " + expectedHeight);
    }

    @Test
    void testButtonAddRoomsAction() {
        JButton btnAddRooms = (JButton) Arrays.stream(addRoomsFrame.getContentPane().getComponents())
                .filter(component -> component instanceof JButton)
                .map(component -> (JButton) component)
                .filter(button -> "ADD ROOMS".equals(button.getText()))
                .findFirst()
                .orElse(null);

        assertNotNull(btnAddRooms, "Button 'ADD ROOMS' should be initialized");

        // Simulate button click
        assertDoesNotThrow(() -> btnAddRooms.doClick(), "Button click should not throw an exception");
    }
    @Test
    void testAddRooms_Success() {
        addRoomsFrame.r1.setText("Deluxe");
        addRoomsFrame.r2.setText("King");
        addRoomsFrame.r3.setText("200");

        // Simulate the add rooms action
        try {
            String roomType = addRoomsFrame.r1.getText();
            String bedType = addRoomsFrame.r2.getText();
            String price = addRoomsFrame.r3.getText();

            // Mock the database connection and statement
            GetConnection connectionMock = mock(GetConnection.class);
            Connection connMock = mock(Connection.class);
            PreparedStatement psMock = mock(PreparedStatement.class);

            // Ensure connectionMock returns connMock when getConnection is called
            when(connectionMock.getConnection()).thenReturn(connMock);
            // Ensure connMock prepares the statement correctly
            when(connMock.prepareStatement(anyString())).thenReturn(psMock);
            // Mock the executeUpdate behavior
            when(psMock.executeUpdate()).thenReturn(1); // Simulate successful update

            // Simulate the add room action
            addRoomsFrame.addRooms();  // Ensure this method is called to trigger the PreparedStatement interaction

            // Verify interactions
            verify(psMock).setString(1, roomType); // Verify setString(1, roomType) was called
            verify(psMock).setString(2, bedType);  // Verify setString(2, bedType) was called
            verify(psMock).setString(3, price);    // Verify setString(3, price) was called
            verify(psMock).executeUpdate();        // Verify executeUpdate() was called

            assertDoesNotThrow(() -> addRoomsFrame.addRooms(), "Adding rooms should not throw an exception");

        } catch (SQLException e) {
            fail("SQL Exception occurred during the test: " + e.getMessage());
        }
    }


    @Test
    void testUpdateRooms_Success() throws SQLException {
        addRoomsFrame.r1.setText("Standard");
        addRoomsFrame.r2.setText("Queen");
        addRoomsFrame.r3.setText("150");

        // Mock the database connection and statement
        GetConnection connectionMock = mock(GetConnection.class);
        Connection connMock = mock(Connection.class);
        PreparedStatement psMock = mock(PreparedStatement.class);

        when(connectionMock.getConnection()).thenReturn(connMock);
        when(connMock.prepareStatement(anyString())).thenReturn(psMock);

        try {
            // Simulate the update rooms action
            psMock.setString(1, addRoomsFrame.r1.getText());
            psMock.setString(2, addRoomsFrame.r2.getText());
            psMock.setString(3, addRoomsFrame.r3.getText());
            psMock.setString(4, "1");  // Assuming we update room with roomNo 1

            // Verify that the update query was executed
            verify(psMock).executeUpdate();
            assertDoesNotThrow(() -> addRoomsFrame.updateRooms(), "Updating rooms should not throw an exception");

        } catch (SQLException e) {
            fail("SQL Exception occurred during the test: " + e.getMessage());
        }
    }

    @Test
    void testDeleteRooms_Success() throws SQLException {
        // Simulate a selected row
        JTable tableMock = mock(JTable.class);
        DefaultTableModel modelMock = mock(DefaultTableModel.class);
        when(tableMock.getSelectedRow()).thenReturn(0);
        when(tableMock.getModel()).thenReturn(modelMock);
        when(modelMock.getValueAt(0, 0)).thenReturn("1"); // Room number for deletion

        addRoomsFrame.table = tableMock;

        // Mock the database connection and statement
        GetConnection connectionMock = mock(GetConnection.class);
        Connection connMock = mock(Connection.class);
        PreparedStatement psMock = mock(PreparedStatement.class);

        when(connectionMock.getConnection()).thenReturn(connMock);
        when(connMock.prepareStatement(anyString())).thenReturn(psMock); // Return the mock statement when prepareStatement is called

        // Set up the mock to simulate no-op for setString and executeUpdate
        doNothing().when(psMock).setString(anyInt(), anyString());
        doNothing().when(psMock).executeUpdate();

        // Simulate the delete room action by calling deleteRooms()
        addRoomsFrame.deleteRooms();  // Ensure deleteRooms method is using the mock PreparedStatement

        // Verify that setString was called with the correct parameter
        verify(psMock).setString(1, "1");

        // Verify that executeUpdate was called
        verify(psMock).executeUpdate();

        // Ensure no exceptions were thrown during the delete operation
        assertDoesNotThrow(() -> addRoomsFrame.deleteRooms(), "Deleting rooms should not throw an exception");
    }
    @Test
    void testDisplayRooms_FillsTable() throws SQLException {
        // Mock the database connection and statement
        GetConnection connectionMock = mock(GetConnection.class);
        Connection connMock = mock(Connection.class);
        Statement stmtMock = mock(Statement.class);
        ResultSet rsMock = mock(ResultSet.class);

        when(connectionMock.getConnection()).thenReturn(connMock);
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        try {
            // Simulate one result from the ResultSet
            when(rsMock.next()).thenReturn(true).thenReturn(false); // Simulate 1 result
            when(rsMock.getString("roomNo")).thenReturn("101");
            when(rsMock.getString("roomType")).thenReturn("Deluxe");
            when(rsMock.getString("bedType")).thenReturn("King");
            when(rsMock.getString("Price")).thenReturn("200");

            // Call the method that populates the table
            addRoomsFrame.displayRooms();

            // Assert that only 1 row is added to the table
            assertEquals(1, addRoomsFrame.table.getRowCount(), "Table should contain 1 row");

        } catch (SQLException e) {
            fail("SQL Exception occurred during the test: " + e.getMessage());
        }
    }

}
