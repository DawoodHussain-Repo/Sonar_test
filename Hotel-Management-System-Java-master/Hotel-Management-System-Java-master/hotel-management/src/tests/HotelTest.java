package tests;

import hotel.Hotel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    @Test
    void testGetInstance_SingletonBehavior() {
        // Retrieve two instances of the singleton
        Hotel instance1 = Hotel.getInstance();
        Hotel instance2 = Hotel.getInstance();

        // Verify both instances are the same
        assertSame(instance1, instance2, "Both instances should refer to the same object");
    }

    @Test
    void testHotelDetails_Accessibility() {
        // Retrieve the singleton instance
        Hotel hotel = Hotel.getInstance();

        // Use reflection to access private fields for validation
        try {
            var nameField = Hotel.class.getDeclaredField("hotelName");
            nameField.setAccessible(true);
            String hotelName = (String) nameField.get(hotel);

            var addressField = Hotel.class.getDeclaredField("hotelAddress");
            addressField.setAccessible(true);
            String hotelAddress = (String) addressField.get(hotel);

            // Validate the singleton instance's fields
            assertEquals("CRESCENT", hotelName, "Hotel name should be 'CRESCENT'");
            assertEquals("HUBLI", hotelAddress, "Hotel address should be 'HUBLI'");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed to access fields: " + e.getMessage());
        }
    }

    @Test
    void testPrivateConstructor_Uninstantiable() {
        // Use reflection to ensure the constructor is private
        try {
            var constructor = Hotel.class.getDeclaredConstructor(String.class, String.class);
            assertTrue(constructor.canAccess(null) == false, "Constructor should be private");
        } catch (NoSuchMethodException e) {
            fail("Expected constructor not found: " + e.getMessage());
        }
    }
}
