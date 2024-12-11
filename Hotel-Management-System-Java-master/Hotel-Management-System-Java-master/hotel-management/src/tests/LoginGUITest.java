package tests;

import hotel.GetConnection;
import hotel.loginPage;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginGUITest {
    private FrameFixture window;
    private loginPage frame;  // Change to loginPage

    // Setup method to initialize and display the Login GUI for testing
    @Before
    public void setUp() {
        GetConnection connect = new GetConnection(); // Initialize your connection object
        frame = new loginPage(connect); // Instantiate the loginPage
        frame.getFrame().setName("loginFrame"); // Assign a name for easier identification
        frame.getFrame().setVisible(true); // Make the frame visible for interaction
        window = new FrameFixture(frame.getFrame()); // Bind the frame to AssertJ Swing's FrameFixture
    }

    // Cleanup method to close the window and release resources after each test
    @After
    public void tearDown() {
        window.cleanUp(); // Close the GUI and cleanup resources
    }

    // Test to verify the Username field behavior
    @Test
    public void testUsernameField() {
        JTextComponentFixture usernameField = window.textBox("tfusername"); // Reference the Username field
        usernameField.requireEnabled(); // Ensure the field is enabled
        usernameField.requireEmpty(); // Ensure the field is initially empty

        // Simulate entering a username
        usernameField.enterText("testUser");
        usernameField.requireText("testUser"); // Verify the entered username is correct
    }

    // Test to verify the Password field behavior
    @Test
    public void testPasswordField() {
        JTextComponentFixture passwordField = window.textBox("tfpwd"); // Reference the Password field (as a text component)
        passwordField.requireEnabled(); // Ensure the field is enabled
        passwordField.requireEmpty(); // Ensure the field is initially empty

        // Simulate entering a password
        passwordField.enterText("testPass");
        passwordField.requireText("testPass"); // Verify the entered password is correct
    }

    // Test to verify the Login button functionality
    @Test
    public void testLoginButton() {
        window.button("btnLogin").requireEnabled().requireVisible(); // Ensure the Login button is enabled and visible

        // Simulate a login action
        window.textBox("tfusername").enterText("admin");
        window.textBox("tfpwd").enterText("admin123");  // Using textBox for password as well
        window.button("btnLogin").click();

        // Additional assertions can verify the frame transition or mock database behavior
    }


}
