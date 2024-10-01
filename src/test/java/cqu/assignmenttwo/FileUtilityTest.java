/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package cqu.assignmenttwo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
 * This test class is to review the methods of the FileUtility class.
 * This class tests the save and load operations for different types of data
 * such as Staff, DisasterEvents, NotificationAlerts, ActionPlans, and ActionsDone.
 */
public class FileUtilityTest {

    // Variables to store the csv file names.
    private static final String STAFF_CSV = "staff_test.csv";
    private static final String DISASTER_CSV = "disaster_test.csv";
    private static final String NOTIFICATION_CSV= "notification_test.csv";
    private static final String ACTIONPLAN_CSV= "actionPlan_test.csv";
    private static final String ACTIONSDONE_CSV= "actionsDone_test.csv";

    public FileUtilityTest() {
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
     * Creates the csv files to perform save and load operations.
     */
    @BeforeEach
    public void setUp() {
        
        try {
            new File(STAFF_CSV).createNewFile();
            new File(DISASTER_CSV).createNewFile();
            new File (NOTIFICATION_CSV).createNewFile();
            new File (ACTIONPLAN_CSV).createNewFile();
            new File (ACTIONSDONE_CSV).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the test files after each test to clean the environment for the 
     * next test.
     */
    @AfterEach
    public void tearDown() {
        new File(STAFF_CSV).delete();
        new File(DISASTER_CSV).delete();
        new File (NOTIFICATION_CSV).delete();
        new File (ACTIONPLAN_CSV).delete();
        new File (ACTIONSDONE_CSV).delete();
    }

    /**
     * Tests the save and load methods for Staff objects.
     * Verifies that staff members are correctly saved to and loaded 
     * from a CSV file.
     */
    @Test
    public void testSaveAndLoadStaffToCsv() {
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff("STAFF001", Role.DISASTER_EVENT_ASSISTANT, 
                "Michael Jackson", "michaelj@drs.com", "1111"));

        // Save staff to CSV.
        FileUtility.saveStaffToCsv(staffList, STAFF_CSV);

        // Load staff from CSV.
        List<Staff> loadedStaffList = FileUtility.loadStaffFromCsv(STAFF_CSV);

        // Verify
        assertEquals(staffList.size(), loadedStaffList.size());
        assertEquals(staffList.get(0).getIdStaff(), 
                loadedStaffList.get(0).getIdStaff());
        assertEquals(staffList.get(0).getRoleAsString(), 
                loadedStaffList.get(0).getRoleAsString());
        assertEquals(staffList.get(0).getName(), 
                loadedStaffList.get(0).getName());
        assertEquals(staffList.get(0).getEmailAddress(), 
                loadedStaffList.get(0).getEmailAddress());
        assertEquals(staffList.get(0).getPassword(), 
                loadedStaffList.get(0).getPassword());
    }

     /**
     * Tests the save and load methods for DisasterEvent objects.
     * Verifies that disaster events are correctly saved to and loaded 
     * from a CSV file.
     */
    @Test
    public void testSaveAndLoadDisasterEventsToCsv() {
        List<DisasterEvent> disasterList = new ArrayList<>();
        disasterList.add(new DisasterEvent(
                "Mary Jane", 1111111111, "415 Victoria Street", 1l,
                LocalDate.of(2024, 8, 21), TypeOfDisaster.FLOOD, "Central",
                "Heavy flooding"
        ));

        // Save disaster events to CSV.
        FileUtility.saveDisasterEventsToCsv(disasterList, DISASTER_CSV);

        // Load disaster events from CSV.
        List<DisasterEvent> loadedDisasterList = 
                FileUtility.loadDataFromCsv(DISASTER_CSV);

        // Verify
        assertEquals(disasterList.size(), loadedDisasterList.size());
        assertEquals(disasterList.get(0).getReporterName(), 
                loadedDisasterList.get(0).getReporterName());
        assertEquals(disasterList.get(0).getReporterMobile(), 
                loadedDisasterList.get(0).getReporterMobile());
        assertEquals(disasterList.get(0).getReporterAddress(), 
                loadedDisasterList.get(0).getReporterAddress());
        assertEquals(disasterList.get(0).getDisasterId(), 
                loadedDisasterList.get(0).getDisasterId());
        assertEquals(disasterList.get(0).getDisasterDate(), 
                loadedDisasterList.get(0).getDisasterDate());
        assertEquals(disasterList.get(0).getTypeOfDisasterAsString(), 
                loadedDisasterList.get(0).getTypeOfDisasterAsString());
        assertEquals(disasterList.get(0).getDisasterLocation(), 
                loadedDisasterList.get(0).getDisasterLocation());
        assertEquals(disasterList.get(0).getDisasterDescription(), 
                loadedDisasterList.get(0).getDisasterDescription());
    }
    
    /**
     * Tests the save and load methods for NotificationAlert objects.
     * Verifies that notification alerts are correctly saved to and loaded 
     * from a CSV file.
     */
    @Test
    public void testSaveAndLoadNotificationAlertsToCsv() {
        List<NotificationAlert> notificationList = new ArrayList<>();
        notificationList.add(new NotificationAlert(
                1L, "22-08-2024", "EARTHQUAKE", "Penrith",
                "A couple of houses are damaged and people is hurt", "High"
        ));

        // Save notification alert to CSV.
        FileUtility.saveNotificationAlertToCsv(notificationList, NOTIFICATION_CSV);

        // Load notification alert from CSV.
        List<NotificationAlert> loadedNotificationList = 
                FileUtility.loadNotificationFromCsv(NOTIFICATION_CSV);

        // Verify
        assertEquals(notificationList.size(), loadedNotificationList.size());
        assertEquals(notificationList.get(0).getDisasterId(), 
                loadedNotificationList.get(0).getDisasterId());
        assertEquals(notificationList.get(0).getDisasterDate(), 
                loadedNotificationList.get(0).getDisasterDate());
        assertEquals(notificationList.get(0).getTypeOfDisaster(), 
                loadedNotificationList.get(0).getTypeOfDisaster());
        assertEquals(notificationList.get(0).getDisasterDescription(), 
                loadedNotificationList.get(0).getDisasterDescription());
        assertEquals(notificationList.get(0).getLevelOfPriority(), 
                loadedNotificationList.get(0).getLevelOfPriority());      
    }
    
    /**
     * Tests the save and load method for ActionPlan objects.
     * Verifies that action plans are correctly saved to and loaded 
     * from a CSV file.
     */
    @Test
    public void testSaveAndLoadActionPlanToCsv() {
        List<ActionPlans> actionPlanList = new ArrayList<>();
        actionPlanList.add(new ActionPlans(
                1L, "High", ResponderAuthority.MEDICAL_TEAM, 
                "The people involved in the car accident is bleeding and need immediate assistance",
                "Approve", "No changes required"
        ));

        // Save the action plan to CSV.
        FileUtility.saveActionPlanToCsv(actionPlanList, ACTIONPLAN_CSV);

        // Load the action plan from CSV.
        List<ActionPlans> loadedActionPlanList = 
                FileUtility.loadPlanFromCsv(ACTIONPLAN_CSV);

        // Verify
        assertEquals(actionPlanList.size(), loadedActionPlanList.size());
        assertEquals(actionPlanList.get(0).getDisasterId(), 
                loadedActionPlanList.get(0).getDisasterId());
        assertEquals(actionPlanList.get(0).getLevelOfPriority(), 
                loadedActionPlanList.get(0).getLevelOfPriority());
        assertEquals(actionPlanList.get(0).getAuthorityRequired(), 
                loadedActionPlanList.get(0).getAuthorityRequired());
        assertEquals(actionPlanList.get(0).getActionsRequired(),
                loadedActionPlanList.get(0).getActionsRequired());
        assertEquals(actionPlanList.get(0).getPlanReview(), 
                loadedActionPlanList.get(0).getPlanReview());
        assertEquals(actionPlanList.get(0).getPlanChanges(), 
                loadedActionPlanList.get(0).getPlanChanges());
    }
    
    /**
     * Tests the save and load method for ActionsDone objects.
     * Verifies that actions done  are correctly saved to and loaded 
     * from a CSV file.
     */
    @Test
    public void testSaveAndLoadActionsDoneToCsv() {
        List<ActionsDone> actionsDoneList = new ArrayList<>();
        actionsDoneList.add(new ActionsDone(
               1L, ResponderAuthority.MEDICAL_TEAM, 
                "The people was assisted and taken to the hospital","Approve", 
                "No additional actions required"
        ));
        
        // Save disaster events to CSV
        FileUtility.saveActionsDoneToCsv(actionsDoneList, ACTIONSDONE_CSV);

        // Load disaster events from CSV
        List<ActionsDone> loadedActionsDoneList = 
                FileUtility.loadActionsDoneFromCsv(ACTIONSDONE_CSV);

        // Verify
        assertEquals(actionsDoneList.size(), loadedActionsDoneList.size());
        assertEquals(actionsDoneList.get(0).getDisasterId(), 
                loadedActionsDoneList.get(0).getDisasterId());
        assertEquals(actionsDoneList.get(0).getAuthorityRequired(),
                loadedActionsDoneList.get(0).getAuthorityRequired());
        assertEquals(actionsDoneList.get(0).getActionsDone(), 
                loadedActionsDoneList.get(0).getActionsDone());
        assertEquals(actionsDoneList.get(0).getActionsDoneReview(),
                loadedActionsDoneList.get(0).getActionsDoneReview());
        assertEquals(actionsDoneList.get(0).getAdditionalActions(), 
                loadedActionsDoneList.get(0).getAdditionalActions());
    }
}
