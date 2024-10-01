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
 
 This test class is to review the constructor and methods of the 
 ActionPlans class.
 */
public class ActionPlanTest {
    
    // Action plan instance used in the test.
    private ActionPlans plan;
    // Action plan instance used in the fail test.
    private ActionPlans secondPlan;
    
    // Empty constructor.
    public ActionPlanTest() {
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
     * Initializes the ActionPlans instance for the before each test is executed.
     */
    @BeforeEach
    public void setUp() {
        plan = new ActionPlans(
        "DISASTER001",
        "High",
        ResponderAuthority.FIREFIGHTERS,
        "Control the fire and assist the people in danger.",
        "Approve",
        "No changes required"
        );
    }
    
    /**
     * Initializes the ActionPlans instance for the fail test before is executed.
     */
    @BeforeEach
    public void SetUpFailTest(){
        secondPlan = new ActionPlans(
        "DISASTER002",
        "High",
        ResponderAuthority.POLICE,
        "Capture the terrorist.",
        "Approve",
        "No changes required"              
        );
    }
    
    /**
     * Cleans up after each test is executed.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Tests the ActionPlans constructor and the get methods to ensure 
 the object is initialized with the correct values.
     */
    @Test
    public void testActionPlanConstructor() {
        assertNotNull(plan);
        assertEquals("DISASTER001", plan.getDisasterId());
        assertEquals("High", plan.getLevelOfPriority());
        assertEquals("FIREFIGHTERS", plan.getAuthorityRequired());
        assertEquals("Control the fire and assist the people in danger.", 
                plan.getActionsRequired());
        assertEquals("Approve", plan.getPlanReview());
        assertEquals("No changes required", plan.getPlanChanges());
    }
    
    /**
     * Tests the ActionPlans constructor and the get methods to ensure 
 the object is initialized with the wrong emergency responder.
     */
    @Test
    public void failTestActionPlanConstructor() {
        assertNotNull(secondPlan);
        assertEquals("DISASTER002", secondPlan.getDisasterId());
        assertEquals("Very High", secondPlan.getLevelOfPriority());
        assertEquals("CQUniversity", secondPlan.getAuthorityRequired());
        assertEquals("Capture the terrorist.", 
                secondPlan.getActionsRequired());
        assertEquals("Approve", secondPlan.getPlanReview());
        assertEquals("No changes required", secondPlan.getPlanChanges());
    }
    
    /**
     * Tests the toCsvStringActionPlan method to ensure it generates the correct
 CSV string representation of the ActionPlans.
     */
    @Test
    public void testToCsvStringActionPlan(){
        String expectedCsv = "DISASTER001,High,FIREFIGHTERS,"
                + "Control the fire and assist the people in danger.,"
                + "Approve,No changes required";
        assertEquals(expectedCsv, plan.toCsvStringActionPlan());
    }
    
    /**
     * Tests the getCsvActionPlan method to ensure it returns the correct
 CSV header for the ActionPlans.
     */
    @Test
    public void testGetCsvActionPlan(){
        String expectedHeader = "DisasterId,LevelOfPriority,AuthorityRequired,"
                + "ActionsRequired,ManagerReview,ChangesRequired";
        assertEquals(expectedHeader, plan.getCsvActionPlan());
    }
}
