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
 * Staff class.
 */
public class StaffTest {

    // Notification Alert instance used in the test.
    private Staff staff;

    // Empty constructor.
    public StaffTest() {
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
     * Initializes the Staff instance before each test is executed.
     */
    @BeforeEach
    public void setUp() {
        staff = new Staff(
                "STAFF001",
                Role.DISASTER_EVENT_ASSISTANT,
                "Charles Xavier",
                "charles@drs.com",
                "12345"
        );
    }

    /**
     * Cleans up after each test is executed.
     */
    @AfterEach
    public void tearDown() {
    }
    
    /**
     * Tests the Staff constructor and the get methods to ensure 
     * the object is initialized with the correct values.
     */
    @Test
    public void testStaffConstructor() {
        assertNotNull(staff);
        assertEquals("DISASTER_EVENT_ASSISTANT", staff.getRoleAsString());
        assertEquals("Charles Xavier", staff.getName());
        assertEquals("charles@drs.com", staff.getEmailAddress());
        assertEquals("12345", staff.getPassword());
    }
    
    /**
     * Tests the Staff constructor and the get methods to ensure 
     * the object is initialized with the wrong role.
     */
    @Test
    public void failTestStaffConstructor() {
        assertNotNull(staff);
        assertEquals("Cheff", staff.getRoleAsString());
        assertEquals("Charles Xavier", staff.getName());
        assertEquals("charles@drs.com", staff.getEmailAddress());
        assertEquals("12345", staff.getPassword());
    }
    
    /**
     * Tests the toCsvStringStaff method to ensure it generates the 
     * correct CSV string representation of the Staff.
     */
    @Test
    public void testToCsvStringStaff(){
        String expectedCsv = "STAFF001,DISASTER_EVENT_ASSISTANT,Charles Xavier,"
                + "charles@drs.com,12345";
        assertEquals(expectedCsv, staff.toCsvStringStaff());
    }
    
    /**
     * Tests the getCsvStaff method to ensure it returns the correct
     * CSV header for the Staff.
     */
    @Test
    public void testGetCsvStaffHeader(){
        String expectedHeader = "IdStaff,Role,Name,EmailAddress,Password";
        assertEquals(expectedHeader, staff.getCsvStaffHeader());
    }

}
