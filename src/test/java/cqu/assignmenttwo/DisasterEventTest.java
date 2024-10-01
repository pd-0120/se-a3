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
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author AndresPinilla 12243141
 * 
 * This test class is to review the constructor and methods of the 
 * DisasterEvent class.
 */
public class DisasterEventTest {

    // Disaster Event instance used in the test.
    private DisasterEvent disasterEvent;
    LocalDate disasterDate = LocalDate.of(2024, 8, 21);

    // Empty constructor.
    public DisasterEventTest() {
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
     * Cleans up after each test is executed.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Initializes the DisasterEvent instance before each test is executed.
     */
    @BeforeEach
    public void setUp() {
        disasterEvent = new DisasterEvent(
                "Joe Arroyo",
                655210754,
                "123 Second Street",
                1l,
                disasterDate,
                TypeOfDisaster.FIRE,
                "400 Kent Street",
                "A large fire has broken out."
        );
    }

    /**
     * Tests the DisasterEvent constructor and the get methods to ensure 
     * the object is initialized with the correct values.
     */
    @Test
    public void testDisasterEventConstructor() {
        assertNotNull(disasterEvent);
        assertEquals("Joe Arroyo", disasterEvent.getReporterName());
        assertEquals(655210754, disasterEvent.getReporterMobile());
        assertEquals("123 Second Street", disasterEvent.getReporterAddress());
        assertEquals("DISASTER123", disasterEvent.getDisasterId());
        assertEquals("2024-08-21", disasterEvent.getDisasterDate());
        assertEquals("FIRE", disasterEvent.getTypeOfDisasterAsString());
        assertEquals("400 Kent Street", disasterEvent.getDisasterLocation());
        assertEquals("A large fire has broken out.", 
                disasterEvent.getDisasterDescription());
    }
    
    /**
     * Tests the DisasterEvent constructor and the get methods to ensure 
     * the object is initialized with the wrong mobile number.
     */
    @Test
    public void failTestDisasterEventConstructor() {
        assertNotNull(disasterEvent);
        assertEquals("Joe Arroyo", disasterEvent.getReporterName());
        assertEquals("this is not a number", disasterEvent.getReporterMobile());
        assertEquals("123 Second Street", disasterEvent.getReporterAddress());
        assertEquals("DISASTER123", disasterEvent.getDisasterId());
        assertEquals("2024-08-21", disasterEvent.getDisasterDate());
        assertEquals("FIRE", disasterEvent.getTypeOfDisasterAsString());
        assertEquals("400 Kent Street", disasterEvent.getDisasterLocation());
        assertEquals("A large fire has broken out.", 
                disasterEvent.getDisasterDescription());
    }

    /**
     * Tests the toCsvStringDisasterEvent method to ensure it generates the 
     * correct CSV string representation of the DisasterEvent.
     */
    @Test
    public void testToCsvStringDisasterEvent() {
        String expectedCsv = "Joe Arroyo,655210754,123 Second Street,"
                + "DISASTER123,2024-08-21,FIRE,400 Kent Street,"
                + "A large fire has broken out.";
        assertEquals(expectedCsv, disasterEvent.toCsvStringDisasterEvent());
    }

    /**
     * Tests the getCsvDisasterHeader method to ensure it returns the correct
     * CSV header for the DisasterEvent.
     */
    @Test
    public void testGetCsvDisasterHeader() {
        String expectedHeader = "ReporterName,ReporterMobile,ReporterAddress,"
                + "DisasterId,DisasterDate,TypeOfDisaster,DisasterLocation,"
                + "DisasterDescription";
        assertEquals(expectedHeader, disasterEvent.getCsvDisasterHeader());
    }
}
