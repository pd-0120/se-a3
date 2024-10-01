/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package cqu.assignmenttwo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author AndresPinilla 12243141
 * 
 * This test class is to review the constructor and methods of the 
 * NotificationAlert class.
 */
public class NotificationAlertTest {

    // Notification Alert instance used in the test.
    private NotificationAlert notification;

    // Empty constructor.
    public NotificationAlertTest() {
    }

    /**
     * Sets up the test environment before any tests are run.
     */
    @BeforeAll
    public static void setUpClass() {
    }

    /**
     * Cleans up the test environment after all tests are completed.
     */
    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Initializes the NotificationAlert instance before each test is executed.
     */
    @BeforeEach
    public void setUp() {
        notification = new NotificationAlert(
                "DISASTER004",
                "22-08-2024",
                "FIRE",
                "22 Market Street",
                "The building is on fire and the people is trapped.",
                "High"
        );
    }
    
    /**
     * Cleans up after each test is executed.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Tests the NotificationAlert constructor and the get methods to ensure 
     * the object is initialized with the correct values.
     */
    @Test
    public void testNotificationAlertConstructor() {
        assertNotNull(notification);
        assertEquals("DISASTER004", notification.getDisasterId());
        assertEquals("22-08-2024", notification.getDisasterDate());
        assertEquals("FIRE", notification.getTypeOfDisaster());
        assertEquals("The building is on fire and the people is trapped.", 
                notification.getDisasterDescription());
        assertEquals("High", notification.getLevelOfPriority());
    }

    /**
     * Tests the toCsvStringNotificationAlert method to ensure it generates the 
     * correct CSV string representation of the NotificationAlert.
     */
    @Test
    public void tesToCsvStringNotificationAlert() {
        String expectedCsv = "DISASTER004,22-08-2024,FIRE,22 Market Street,"
                + "The building is on fire and the people is trapped.,High";
        assertEquals(expectedCsv, notification.toCsvStringNotificationAlert());
    }

    /**
     * Tests the getCsvNotificationAlert method to ensure it returns the correct
     * CSV header for the NotificationAlert.
     */
    @Test
    public void testGetCsvNotificationAlert() {
        String expectedHeader = "DisasterId,DisasterDate,TypeOfDisaster,"
                + "DisasterLocation,DisasterDescription,LevelOfPriority";
        assertEquals(expectedHeader, notification.getCsvNotificationAlert());
    }

}
