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
* ActionsDone class.
 */
public class ActionsDoneTest {
    
    // Actions done instance used in the test.
    private ActionsDone actions;
    
    // Empty constructor.
    public ActionsDoneTest() {
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
     * Initializes the ActionsDone instance before each test is executed.
     */
    @BeforeEach
    public void setUp() {
        actions = new ActionsDone(
                "DISASTER001",
                ResponderAuthority.FIREFIGHTERS,
                "The fire was controlled and the people was assisted.",
                "Approve",
                "No additional actions required"
        );
    }
    
    /**
     * Cleans up after each test is executed.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Tests the ActionsDone constructor and the get methods to ensure 
     * the object is initialized with the correct values.
     */
    @Test
    public void testActionsDoneConstructor(){
        assertNotNull(actions);
        assertEquals("DISASTER001", actions.getDisasterId());
        assertEquals("FIREFIGHTERS", actions.getAuthorityRequired());
        assertEquals("The fire was controlled and the people was assisted.", 
                actions.getActionsDone());
        assertEquals("Approve", actions.getActionsDoneReview());
        assertEquals("No additional actions required", 
                actions.getAdditionalActions());
    }
    
    /**
     * Tests the ActionsDone constructor and the get methods to ensure 
     * the object is initialized with the wrong disaster id.
     */
    @Test
    public void failTestActionsDoneConstructor(){
        assertNotNull(actions);
        assertEquals("", actions.getDisasterId());
        assertEquals("FIREFIGHTERS", actions.getAuthorityRequired());
        assertEquals("The fire was controlled and the people was assisted.", 
                actions.getActionsDone());
        assertEquals("Approve", actions.getActionsDoneReview());
        assertEquals("No additional actions required", 
                actions.getAdditionalActions());
    }
    
    /**
     * Tests the toCsvStringActionDone method to ensure it generates the correct
     * CSV string representation of the ActionsDone.
     */
    @Test
    public void testToCsvStringActionDone(){
        String expectedCsv = "DISASTER001,FIREFIGHTERS,"
                + "The fire was controlled and the people was assisted.,Approve,"
                + "No additional actions required";
        assertEquals(expectedCsv,actions.toCsvStringActionDone());
    }
    
    /**
     * Tests the getCsvActionDone method to ensure it returns the correct
     * CSV header for the ActionDone.
     */
    @Test
    public void testGetCsvActionDone(){
        String expectedHeader = "DisasterId,AuthorityRequired,ActionsDone,"
                + "ManagerReview,AdditionalActionsRequired";
        assertEquals(expectedHeader,actions.getCsvActionDone());
    }
}
