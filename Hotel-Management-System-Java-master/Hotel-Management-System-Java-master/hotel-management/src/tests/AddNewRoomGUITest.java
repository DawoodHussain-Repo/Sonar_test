package tests;

import hotel.AddRooms;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.*;
import org.junit.jupiter.api.*;
import static org.assertj.swing.finder.WindowFinder.findFrame;

import javax.swing.*;

public class AddNewRoomGUITest {

    private JFrame frame;
    private FrameFixture window;

    @BeforeEach
    void setUp() {
        // Create and show the AddRooms GUI on the Event Dispatch Thread (EDT)
        frame = GuiActionRunner.execute(() -> {
            AddRooms addRooms = new AddRooms();
            addRooms.setVisible(true);
            addRooms.setExtendedState(JFrame.NORMAL);  // Set to normal state initially
            return addRooms;
        });

        // Set the JFrame to be maximized
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Use AssertJ-Swing to find the window and interact with it
        window = new FrameFixture(frame);
        window.show(); // Show the GUI for testing

        // Wait for window to be fully initialized
        window.robot().waitForIdle();
    }

    @AfterEach
    void tearDown() {
        // Close the window after tests
        window.cleanUp();
    }

    @Test
    void testAddRoom() {
        // Simulate entering data into the form and clicking the "ADD ROOMS" button
        window.textBox("r1").enterText("Deluxe");
        window.textBox("r2").enterText("King");
        window.textBox("r3").enterText("250");

        window.button("btnAddRooms").click();

        // Wait for the action to complete
        window.robot().waitForIdle();

        // Check if a confirmation message appears
        window.optionPane().requireMessage("New Room Added");
    }

    @Test
    void testUpdateRoom() {
        // Simulate adding a room first before updating it
        window.textBox("r1").enterText("Standard");
        window.textBox("r2").enterText("Single");
        window.textBox("r3").enterText("100");
        window.button("btnAddRooms").click();  // Add a room first

        // Wait for the action to complete
        window.robot().waitForIdle();

        // Select a room from the table to update (assuming first row is selected)
        window.table().selectRows(0);

        // Update the room details
        window.textBox("r1").enterText("Updated Standard");
        window.textBox("r2").enterText("Double");
        window.textBox("r3").enterText("150");

        window.button("btnUpdateRooms").click(); // Click "UPDATE ROOMS" button

        // Wait for the action to complete
        window.robot().waitForIdle();

        // Verify that a success message is shown
        window.optionPane().requireMessage("Room Updated");
    }

    @Test
    void testDeleteRoom() {
        // Simulate adding a room first before deleting it
        window.textBox("r1").enterText("Suite");
        window.textBox("r2").enterText("King");
        window.textBox("r3").enterText("500");
        window.button("btnAddRooms").click();  // Add room first

        // Wait for the action to complete
        window.robot().waitForIdle();

        // Select the room to delete
        window.table().selectRows(0); // Select first row to delete

        window.button("btnDeleteRooms").click(); // Click "DELETE ROOMS"

        // Wait for the action to complete
        window.robot().waitForIdle();

        // Verify that a success message is shown
        window.optionPane().requireMessage("Room Deleted");
    }

    @Test
    void testTableContent() {
        // Add a room and check that it appears in the table
        window.textBox("r1").enterText("Deluxe");
        window.textBox("r2").enterText("King");
        window.textBox("r3").enterText("300");
        window.button("btnAddRooms").click();

        // Wait for the action to complete
        window.robot().waitForIdle();

        // Verify that the room appears in the table
        JTableFixture table = window.table();
        // Optionally, you can add a check to assert that the room appears
        // For example: assertTrue(table.cell(0, 0).value().equals("Deluxe"));
    }
}
