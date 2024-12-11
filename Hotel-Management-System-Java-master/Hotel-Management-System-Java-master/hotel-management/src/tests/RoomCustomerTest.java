package tests;

import hotel.RoomCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomCustomerTest {

    private RoomCustomer roomCustomer1;
    private RoomCustomer roomCustomer2;

    @BeforeEach
    void setUp() {
        // Initialize RoomCustomer objects
        roomCustomer1 = new RoomCustomer("Single", 100.0f, 0);
        roomCustomer2 = new RoomCustomer("Double", 150.0f, 1);
    }

    @Test
    void testRoomCustomerConstructorWithRoomType() {
        assertEquals("Single", roomCustomer1.getRoomType());
        assertEquals(100.0f, roomCustomer1.getPrice());
        assertEquals(0, roomCustomer1.getStatus());  // Default status should be 0 (vacant)
    }

    @Test
    void testRoomCustomerConstructorWithCustomerInfo() {
        // Initialize a RoomCustomer with Customer data
        RoomCustomer customerWithDetails = new RoomCustomer(1, "John Doe", "123 Main St", "555-1234", "Regular");

        assertEquals(1, customerWithDetails.getCustomerID());
        assertEquals("John Doe", customerWithDetails.getCustName());
        assertEquals("123 Main St", customerWithDetails.getAddr());
        assertEquals("555-1234", customerWithDetails.getPhone());
        assertEquals("Regular", customerWithDetails.getType());
    }

    @Test
    void testGetAndSetRoomType() {
        roomCustomer1.setRoomType("Suite");
        assertEquals("Suite", roomCustomer1.getRoomType());
    }

    @Test
    void testGetAndSetPrice() {
        roomCustomer1.setPrice(200.0f);
        assertEquals(200.0f, roomCustomer1.getPrice());
    }

    @Test
    void testGetAndSetStatus() {
        roomCustomer1.setStatus(1);
        assertEquals(1, roomCustomer1.getStatus());
    }

    @Test
    void testConstructorWithFullDetails() {
        // Check if the constructor with all details works correctly
        RoomCustomer roomCustomerFull = new RoomCustomer(2, "Alice", "456 Oak Ave", "555-5678", "VIP");
        assertEquals(2, roomCustomerFull.getCustomerID());
        assertEquals("Alice", roomCustomerFull.getCustName());
        assertEquals("456 Oak Ave", roomCustomerFull.getAddr());
        assertEquals("555-5678", roomCustomerFull.getPhone());
        assertEquals("VIP", roomCustomerFull.getType());
    }

    @Test
    void testRoomCustomerStatusWhenVacant() {
        roomCustomer1.setStatus(0);  // Set room status to vacant
        assertEquals(0, roomCustomer1.getStatus(), "Room should be vacant.");
    }

    @Test
    void testRoomCustomerStatusWhenReserved() {
        roomCustomer1.setStatus(1);  // Set room status to reserved
        assertEquals(1, roomCustomer1.getStatus(), "Room should be reserved.");
    }
}
