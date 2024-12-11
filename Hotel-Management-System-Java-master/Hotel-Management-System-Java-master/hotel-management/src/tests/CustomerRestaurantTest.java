package tests;

import hotel.CustomerRestaurant;
import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerRestaurantTest {

    private FrameFixture window;

    @Before
    public void setUp() {
        // Initialize the frame
        CustomerRestaurant frame = new CustomerRestaurant();
        frame.setName("customerRestaurantFrame"); // Ensure the frame has a name for debugging
        window = new FrameFixture(frame);
        window.show(); // Show the GUI
    }

    @After
    public void tearDown() {
        if (window != null) {
            window.cleanUp(); // Close the GUI and clean up resources
        }
    }

    @Test
    @GUITest
    public void testFillFormAndGenerateReceipt() {
        // Fill out the form fields
        window.textBox("nameField").setText("John Doe");
        window.textBox("addressField").setText("123 Elm Street");
        window.textBox("phoneField").setText("1234567890");
        window.textBox("roomTypeField").setText("Deluxe");
        window.textBox("bedTypeField").setText("King");
        window.textBox("priceField").setText("500");
        window.textBox("daysField").setText("3");

        // Click the button to calculate the total
        window.button(JButtonMatcher.withName("totalButton")).click();
        assertThat(window.textBox("totalField").text()).isEqualTo("1500"); // Assuming total = price * days

        // Generate the receipt
        window.button(JButtonMatcher.withName("generateReceiptButton")).click();

        // Validate the receipt area contains expected details
        String receiptText = window.textBox("textArea").text();
        assertThat(receiptText).contains(
                "John Doe",
                "123 Elm Street",
                "1234567890",
                "Deluxe",
                "King",
                "1500"
        );
    }
    @Test
    @GUITest
    public void testFillFormAndGenerateReceiptFixed() {
        // Fill out the form fields
        window.textBox("nameField").setText("John Doe");
        window.textBox("addressField").setText("123 Elm Street");
        window.textBox("phoneField").setText("1234567890");
        window.textBox("roomTypeField").setText("Deluxe");
        window.textBox("bedTypeField").setText("King");
        window.textBox("priceField").setText("500");
        window.textBox("daysField").setText("3");

        // Directly generate the receipt
        window.button(JButtonMatcher.withName("generateReceiptButton")).click();

        // Validate the receipt area contains expected details
        String receiptText = window.textBox("textArea").text();
        assertThat(receiptText).contains(
                "John Doe",
                "123 Elm Street",
                "1234567890",
                "Deluxe",
                "King",
                "1500" // Assuming total = price * days
        );
    }
}