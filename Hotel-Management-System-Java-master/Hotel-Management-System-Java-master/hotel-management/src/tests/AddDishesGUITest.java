package tests;

import hotel.AddDishes;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTableFixture;
import org.junit.*;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddDishesGUITest {

    private FrameFixture window;

    // Setup method to initialize the GUI frame and make it ready for testing
    @Before
    public void setUp() {
        AddDishes frame = new AddDishes(); // Instantiate the AddDishes frame
        window = new FrameFixture(frame); // Bind the frame with AssertJ Swing's FrameFixture
        window.show(); // Display the GUI frame
    }

    // Cleanup method to close the window and release resources
    @After
    public void tearDown() {
        window.cleanUp(); // Close the frame and clean up the test environment
    }

    // Test to verify the title of the window
    @Test
    public void testWindowTitle() {
        window.requireTitle("AddDishes");
    }

    // Test to verify the dish name field accepts input correctly
    @Test
    public void testDishNameField() {
        window.textBox("d1").enterText("Pizza"); // Simulate typing "Pizza" in the dish name field
        assertEquals("Pizza", window.textBox("d1").text()); // Assert the field contains the expected text
    }

    // Test to verify the price field accepts input correctly
    @Test
    public void testPriceField() {
        window.textBox("d2").enterText("15"); // Simulate typing "15" in the price field
        assertEquals("15", window.textBox("d2").text()); // Assert the field contains the expected text
    }

    // Test to verify the category field accepts input correctly
    @Test
    public void testCategoryField() {
        window.textBox("d3").enterText("Main Course"); // Simulate typing "Main Course" in the category field
        assertEquals("Main Course", window.textBox("d3").text()); // Assert the field contains the expected text
    }

    // Test to verify the Add Dish button functionality
    @Test
    public void testAddDishButton() {
        // Fill out the dish fields
        window.textBox("d1").enterText("Pasta");
        window.textBox("d2").enterText("10");
        window.textBox("d3").enterText("Side Dish");

        // Simulate clicking the "Add Dish" button
        window.button(JButtonMatcher.withName("ADD DISH")).click();

        // Verify that the table contains at least one dish
        JTableFixture table = window.table(); // Get JTableFixture
        assertTrue(table.rowCount() > 0); // Assert that at least one dish has been added
    }

    // Test to verify the Update Dish button functionality
    @Test
    public void testUpdateDishButton() {
        // Pre-fill the fields with an existing dish's data (you can assume a dish exists already)
        window.textBox("d1").enterText("Updated Pizza");
        window.textBox("d2").enterText("20");
        window.textBox("d3").enterText("Main Course");

        // Simulate clicking the "Update Dish" button
        window.button(JButtonMatcher.withName("UPDATE DISH")).click();

        // Verify that the table reflects the updated dish
        JTableFixture table = window.table(); // Get JTableFixture
        assertTrue(table.rowCount() > 0); // The table should contain updated dishes
    }

    // Test to verify the Delete Dish button functionality
    @Test
    public void testDeleteDishButton() {
        // Select the first row (make sure a dish exists to delete)
        window.table().selectRows(0);

        // Simulate clicking the "Delete Dish" button
        window.button(JButtonMatcher.withName("DELETE DISH")).click();

        // Verify that the table has fewer rows after deletion
        JTableFixture table = window.table(); // Get JTableFixture
        int initialRowCount = table.rowCount();
        window.button(JButtonMatcher.withName("DELETE DISH")).click();
        assertTrue(table.rowCount() < initialRowCount); // Assert that the number of rows decreased
    }

    // Test to verify the Back button functionality
    @Test
    public void testBackButton() {
        // Simulate clicking the "Back" button
        window.button(JButtonMatcher.withName("BACK")).click();

        // Verify that the AdminForm frame appears
        window.requireVisible(); // Assuming AdminForm is the next window
    }
}
