package tests;

import hotel.GetConnection;
import hotel.room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

class RoomTest {

    private room room1;
    private room room2;
    private GetConnection mockGetConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    void setUp() {
        // Initialize room objects
        room1 = new room(101, "Single", "King", 100);
        room2 = new room(102, "Double", "Queen", 150);

        // Mock database connection
        mockGetConnection = mock(GetConnection.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockGetConnection.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testRoomConstructor() {
        assertEquals(101, room1.getRoomNo());
        assertEquals("Single", room1.getRoomType());
        assertEquals("King", room1.getBedtype());
        assertEquals(100, room1.getPrice());
        assertEquals(0, room1.status);  // Default status should be 0 (vacant)
    }

    @Test
    void testIsVacantWhenVacant() {
        room1.status = 0;  // Room is vacant
        assertTrue(room1.isVacant(room1), "Room should be vacant.");
    }

    @Test
    void testIsVacantWhenNotVacant() {
        room1.status = 1;  // Room is not vacant
        assertFalse(room1.isVacant(room1), "Room should not be vacant.");
    }

    @Test
    void testVacateRoom() {
        room1.status = 1;  // Room is reserved
        room1.vacateRoom(101);  // Vacate room 101
        assertEquals(0, room1.status, "Room status should be vacant after vacating.");
    }

    @Test
    void testDisplayDetailsWhenVacant() {
        room1.status = 0;  // Room is vacant
        // Assuming you want to test if the print statements are executed correctly,
        // You can use a different approach here, like capturing the printed output.
        room1.displayDetails();
    }

    @Test
    void testDisplayDetailsWhenReserved() {
        room1.status = 1;  // Room is reserved
        room1.displayDetails();
    }

    @Test
    void testAddRooms() throws Exception {
        // Prepare mock behavior for database interaction
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful insert

        room1.addRooms("i", room1);  // Simulate adding a room

        // Verify that the prepared statement was executed
        verify(mockPreparedStatement).executeUpdate();

        // Optional: Verify JOptionPane dialog was shown (you may need to mock it)
        // assertTrue(someMockedJOptionPane.wasMessageDialogShown());
    }

    @Test
    void testAddRoomsFailure() throws Exception {
        // Simulate a failure during the execution
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);  // Simulate failed insert

        room1.addRooms("i", room1);  // Try adding a room

        // Verify that no new customer is added as the insert failed
        verify(mockPreparedStatement).executeUpdate();
    }
}
