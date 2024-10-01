/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPinilla 12243141
 *
 * This class is to save the disaster events, notification alerts, action plans,
 * actions done, and staff in the csv files.
 * It also reads the information from the csv files.
 *
 */
public class FileUtility {

    /**
     * Saves the staff in a CSV file. 
     *
     * @param staffMembers The list of staff to be saved.
     * @param filename The name of the CSV file.
     */
    public static void saveStaffToCsv(List<Staff> staffMembers, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Check if the file is empty, if yes, add the header
            if (Files.size(Path.of(filename)) == 0) {
                writer.println(staffMembers.get(0).getCsvStaffHeader());
            }
            // Iterate through the list of staff and save their information in 
            // the CSV file
            for (Staff staff : staffMembers) {
                writer.println(staff.toCsvStringStaff());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts an staff object in a CSV formatted string.
     *
     * @param staff The staff object to be converted.
     * @return A CSV formatted string representing the staff's information.
     */
    private static String staffToCsv(Staff staff) {
        // Get the header from the staff object
        StringBuilder csvDataStaff = new StringBuilder(staff.getCsvStaffHeader() + "\n");
        // Append the actual information of the staff
        csvDataStaff.append(staff.toCsvStringStaff()).append("\n");
        // Return the CSV formatted string
        return csvDataStaff.toString();
    }

    /**
     * Loads the staff from csv file.
     *
     * @param filePath the path where the file is.
     * @return the information from the file.
     */
    public static List<Staff> loadStaffFromCsv(String filePath) {
        List<Staff> staffCSV = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line if present
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) { // Ensure there are enough columns
                    staffCSV.add(new Staff(
                            values[0], // ReporterName
                            Role.valueOf(values[1]), // Role
                            values[2], // Name
                            values[3], // EmailAddress
                            values[4] // Password
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffCSV;
    }
    
    
    /**
     * Saves a list of disaster events to a CSV file. 
     *
     * @param disasters The list of disasters to be saved.
     * @param filename The name of the CSV file.
     */
    public static void saveDisasterEventsToCsv(
            List<DisasterEvent> disasters, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Check if the file is empty, if yes, add the header
            if (Files.size(Path.of(filename)) == 0) {
                writer.println(disasters.get(0).getCsvDisasterHeader());
            }
            // Iterate through the list of disasters and save their information 
            // in the CSV file
            for (DisasterEvent disaster : disasters) {
                writer.println(disaster.toCsvStringDisasterEvent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts an disaster event object to a CSV formatted string.
     *
     * @param disaster The disaster event object to be converted.
     * @return A CSV formatted string representing the disaster's information.
     */
    private static String disasterToCsv(DisasterEvent disaster) {
        // Get the header from the disaster event object
        StringBuilder csvDataDisaster = 
                new StringBuilder(disaster.getCsvDisasterHeader() + "\n");
        // Append the actual information of the disaster event
        csvDataDisaster.append(disaster.toCsvStringDisasterEvent()).append("\n");
        // Return the CSV formatted string
        return csvDataDisaster.toString();
    }

    /**
     * Loads the disaster event from csv file.
     *
     * @param filePath the path where the file is.
     * @return the information from the file.
     */
    public static List<DisasterEvent> loadDataFromCsv(String filePath) {
        List<DisasterEvent> disasterEventCSV = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line if present
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) { // Ensure there are enough columns
//                    disasterEventCSV.add(new DisasterEvent(
//                            values[0], // ReporterName
//                            Integer.parseInt(values[1]), // ReporterMobile
//                            values[2], // ReporterAddress
//                            values[3], // DisasterId
//                            Date.parse(values[4]), // DisasterDate
//                            TypeOfDisaster.valueOf(values[5]), // TypeOfDisaster
//                            values[6], // DisasterLocation
//                            values[7] // DisasterDescription
//                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disasterEventCSV;
    }

    /**
     * Saves a list of notifications alert to a CSV file. If the DisasterId is 
     * already present, it replaces the old information with the new information.
     *
     * @param notifications The list of notifications to be saved.
     * @param filename The name of the CSV file.
     */
    public static void saveNotificationAlertToCsv(
            List<NotificationAlert> notifications, String filename) {
       
        try {
            // Load existing plans from the CSV file
            List<NotificationAlert> existingNotifications = 
                    loadNotificationFromCsv(filename);

            // Create a map for easy lookup of existing notifications 
            // by disasterId.
            Map<String, NotificationAlert> notificationMap = new HashMap<>();
            for (NotificationAlert notification : existingNotifications) {
//                notificationMap.put(notification.getId(), notification);
            }

            // Update existing notifications or add new ones.
            for (NotificationAlert notification : notifications) {
//                notificationMap.put(notification.getDisasterId(), notification);
            }

            // Save all updated notifications back to the CSV file.
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                // Write header
                writer.println(notifications.get(0).getCsvNotificationAlert());

                // Write all notifications to the file.
                for (NotificationAlert notification : notificationMap.values()) {
                    writer.println(notification.toCsvStringNotificationAlert());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a notification alert object to a CSV formatted string.
     *
     * @param notification The notification alert object to be converted.
     * @return A CSV formatted string representing the notification's
     * information.
     */
    private static String notificationToCsv(NotificationAlert notification) {
        // Get the header from the notification alert object
        StringBuilder csvDataNotification = 
                new StringBuilder(notification.getCsvNotificationAlert() + "\n");
        // Append the actual information of the notification alert
        csvDataNotification.append(notification.toCsvStringNotificationAlert())
                           .append("\n");
        // Return the CSV formatted string
        return csvDataNotification.toString();
    }

    /**
     * Loads the notification alert from csv file.
     *
     * @param filePath the path where the file is.
     * @return the information from the file.
     */
    public static List<NotificationAlert> loadNotificationFromCsv(String filePath) {
        List<NotificationAlert> notificationCSV = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line if present
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) { // Ensure there are enough columns
                    notificationCSV.add(new NotificationAlert(
                            1L, // DisasterId
                            values[1], // DisasterDate
                            values[2], // TypeOfDisaster
                            values[3], // DisasterLocation
                            values[4], // DisasterDescription    
                            values[5] // LevelOfPriority
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notificationCSV;
    }

    /**
     * Saves a list of action plans to a CSV file. If the DisasterId is already
     * present, it replaces the old information with the new information.
     *
     * @param plans The list of action plans to be saved.
     * @param filename The name of the CSV file.
     */
    public static void saveActionPlanToCsv(List<ActionPlans> plans, String filename) {
        try {
            // Load existing plans from the CSV file
            List<ActionPlans> existingPlans = loadPlanFromCsv(filename);

            // Create a map for easy lookup of existing plans by disasterId
            Map<String, ActionPlans> planMap = new HashMap<>();
            for (ActionPlans plan : existingPlans) {
                planMap.put(plan.getDisasterId().toString(), plan);
            }

            // Update existing plans or add new ones
            for (ActionPlans plan : plans) {
                planMap.put(plan.getDisasterId().toString(), plan);
            }

            // Save all updated plans back to the CSV file
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                // Write header
                writer.println(plans.get(0).getCsvActionPlan());

                // Write all plans to the file
                for (ActionPlans plan : planMap.values()) {
                    writer.println(plan.toCsvStringActionPlan());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a action plan object to a CSV formatted string.
     *
     * @param plan The action plan object to be converted.
     * @return A CSV formatted string representing the notification's
     * information.
     */
    private static String actionPlanToCsv(ActionPlans plan) {
        // Get the header from the action plan object.
        StringBuilder csvDataPlan = new StringBuilder(plan.getCsvActionPlan() + "\n");
        // Append the actual information of the action plan.
        csvDataPlan.append(plan.toCsvStringActionPlan()).append("\n");
        // Return the CSV formatted string
        return csvDataPlan.toString();
    }

    /**
     * Loads the action plan from csv file.
     *
     * @param filePath the path where the file is.
     * @return the information from the file.
     */
    public static List<ActionPlans> loadPlanFromCsv(String filePath) {
        List<ActionPlans> planCSV = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line if present
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) { // Ensure there are enough columns
                    planCSV.add(new ActionPlans(
                            1L, // DisasterId
                            values[1], // LevelOfPriority
                            ResponderAuthority.valueOf(values[2]), // authorityRequired
                            values[3], // ActionsRequired
                            values[4], // ManagerReview
                            values[5] // ChangesRequired
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return planCSV;
    }

    /**
     * Saves a list of actions done to a CSV file. If the disasterId is already
     * present, it replaces the old information with the new information.
     *
     * @param actions The list of actions done to be saved.
     * @param filename The name of the CSV file.
     */
    public static void saveActionsDoneToCsv(List<ActionsDone> actions, String filename) {

    try {
        // Load existing plans from the CSV file
        List<ActionsDone> existingActions = loadActionsDoneFromCsv(filename);

        // Create a map for easy lookup of existing plans by disasterId
        Map<String, ActionsDone> actionMap = new HashMap<>();
        for (ActionsDone action : existingActions) {
            actionMap.put(action.getDisasterId().toString(), action);
        }

        // Update existing plans or add new ones
        for (ActionsDone action : actions) {
            actionMap.put(action.getDisasterId().toString(), action);
        }

        // Save all updated plans back to the CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println(actions.get(0).getCsvActionDone());

            // Write all plans to the file
            for (ActionsDone plan : actionMap.values()) {
                writer.println(plan.toCsvStringActionDone());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    /**
     * Converts an action done object to a CSV formatted string.
     *
     * @param action The action done object to be converted.
     * @return A CSV formatted string representing the notification's
     * information.
     */
    private static String actionsDoneToCsv(ActionsDone action) {
        // Get the header from the action done object
        StringBuilder csvDataAction = 
                new StringBuilder(action.getCsvActionDone() + "\n");
        // Append the actual information of the action done
        csvDataAction.append(action.toCsvStringActionDone()).append("\n");
        // Return the CSV formatted string
        return csvDataAction.toString();
    }

    /**
     * Loads the action done from csv file.
     *
     * @param filePath the path where the file is.
     * @return the information from the file.
     */
    public static List<ActionsDone> loadActionsDoneFromCsv(String filePath) {
        List<ActionsDone> actionCSV = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line if present
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) { // Ensure there are enough columns
                    actionCSV.add(new ActionsDone(
                            1L, // DisasterId
                            // authorityRequired
                            ResponderAuthority.valueOf(values[1]),
                            values[2], // actionsDone
                            values[3], // actionsDoneReview
                            values[4] // additionalActions
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actionCSV;
    }
}
