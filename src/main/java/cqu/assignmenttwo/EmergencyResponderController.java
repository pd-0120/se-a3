/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author AndresPinilla 12243141
 * 
 * This class is to control the EmergencyController.fxml. It has multiple data
 * types to store the user's input and key events and key actions methods to
 * handle the user interaction.
 */
public class EmergencyResponderController {
    
    // Stores all the actions done.
    private List<ActionsDone> actionsList = new LinkedList<>();
    // Stores all the action plans.
    private List<ActionPlans> planList = new LinkedList<>();
    // Stores the disaster event selected.
    private ObservableList<ActionPlans> actionPlans = 
            FXCollections.observableArrayList();
    // Stores the selected disaster event from the disasterSelectionCombobox.
    private ActionPlans selectedActionPlan;
    // Stores the provided actiones done from the actionDoneTextArea.
    private String providedActionsDone;
    
    // FXML buttons, labels, combobox text areas and text fields.
    @FXML
    private ComboBox<String> planSelectionCombobox;
    @FXML
    private TableView<ActionPlans> actionPlanTableView;
    @FXML
    private TableColumn<ActionPlans, String> disasterIdTable;
    @FXML
    private TableColumn<ActionPlans, String> priorityTable;
    @FXML
    private TableColumn<ActionPlans, String> authorityTable;
    @FXML
    private TableColumn<ActionPlans, String> actionsRequiredTable;
    @FXML
    private TableColumn<ActionPlans, String> planReviewTable;
    @FXML
    private TableColumn<ActionPlans, String> planChangesTable;
    @FXML
    private Label planErrorLabel;
    @FXML
    private TextArea actionsDoneTextArea;
    
    /**
     * This section is to initialize the Tableviews, Tablecolumns and to set 
     * arial font so it can be compatible with mac OS system.
     */
    @FXML
    private void initialize() {
        // Sets the font style of planSelectionCombobox.
        planSelectionCombobox.setStyle("-fx-font-family: 'Arial'");
        // Load data from CSV file in the observableList.
        actionPlans.setAll(FileUtility.loadPlanFromCsv("ActionPlan.csv"));
        // Populate ComboBox with disaster IDs.
        planSelectionCombobox.getItems().addAll(getDisasterIdsForActionPlan());
        // Set up event handler for ComboBox.
        planSelectionCombobox.setOnAction(this::handlePlanSelection);

        // Initialize actionPlanTableView columns.
        disasterIdTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterId"));
        priorityTable.setCellValueFactory(
                new PropertyValueFactory<>("levelOfPriority"));
        // Use a string converter for ResponderAuthority.
        authorityTable.setCellValueFactory(cellData
                -> new SimpleStringProperty(
                        cellData.getValue().getAuthorityRequired()));
        actionsRequiredTable.setCellValueFactory(
                new PropertyValueFactory<>("actionsRequired"));
        planReviewTable.setCellValueFactory(
                new PropertyValueFactory<>("planReview"));
        planChangesTable.setCellValueFactory(
                new PropertyValueFactory<>("planChanges"));
        // Sets the font style.
        actionPlanTableView.setStyle("-fx-font-family: 'Arial'");
          
        // Sets the font style of planSelectionCombobox.
        actionsDoneTextArea.setStyle("-fx-font-family: 'Arial'");
    }

    /**
     * This section is to filter the action plan by disaster id.
     *
     * @return the action plan selected.
     */
    private ObservableList<String> getDisasterIdsForActionPlan() {
        ObservableList<String> disasterIds = FXCollections.observableArrayList();
        for (ActionPlans actionPlan : actionPlans) {
            if (!disasterIds.contains(actionPlan.getDisasterId())) {
                disasterIds.add(actionPlan.getDisasterId().toString());
            }
        }
        return disasterIds;
    }
    
    /**
     * This section is to handle the selection of the action plan.
     * 
     * @param event the selection done by the user. 
     */
    @FXML
    private void handlePlanSelection(ActionEvent event) {
        String selectedId = planSelectionCombobox.getValue();
        if (selectedId != null) {
            // Filter the action plan to find the selected one.
            ObservableList<ActionPlans> filteredReports = actionPlans.filtered(
                    actionPlan -> actionPlan.getDisasterId().equals(selectedId));
            // Set the items in the table view.
            actionPlanTableView.setItems(filteredReports);

            // If there is a selected plan, store it in the class-level variable.
            if (!filteredReports.isEmpty()) {
                selectedActionPlan = filteredReports.get(0);  
            }
        }
    }

    /**
     * This section is to handle the actions done provided in the text area by
     * the user.
     * 
     * @param event The user writes down the actions done. 
     */
    @FXML
    private void actionsDoneTextArea(KeyEvent event) {
        providedActionsDone = actionsDoneTextArea.getText();
    }

    /**
     * This section is to handle the complete action done button.
     * It validates if an action plan was selected and the actions done were
     * provided.
     * 
     * @param event The user clicks the completeActionDone button. 
     */
    @FXML
    private void completeActionDoneButton(ActionEvent event) {
        
        if (selectedActionPlan != null && providedActionsDone != null) {
            
            // Capture the data from the selected action plan.
            Long disasterId = selectedActionPlan.getDisasterId();
            ResponderAuthority authorityRequired = 
                    ResponderAuthority.valueOf(selectedActionPlan.getAuthorityRequired());
            String actionsDone = providedActionsDone;

            // Create a new Action Plan
            ActionsDone actionsReport = new ActionsDone(
                    disasterId,
                    authorityRequired,
                    actionsDone,
                    // Manager review, it is filled with 0 because they are not 
                    //provided in this screen.
                    "0",
                    // Additional actions, it is filled with 0 because they are not 
                    //provided in this screen.
                    "0"
            );
            // Save the actions done in the acitons list.
            actionsList.add(actionsReport);

            // Save the actions list to a CSV file.
            FileUtility.saveActionsDoneToCsv(actionsList, "ActionsDone.csv");

            // Hides the error message when the actions done are created.
            planErrorLabel.setVisible(false);
            
            // Displays the Disaster emergency responders menu screen.
            try {
            App.setRoot("EmergencyResponderMenu");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }

        } else {
            // Displays a message to inform there is an error 
            planErrorLabel.setText("Error. Please select an Action Plan and "
                    + "Describe the actions done");
            planErrorLabel.setVisible(true);
        }  
    }
    
    
    /**
     * This section is to handle the button go back to the main menu.
     * If clicked it will display the main menu screen.
     * @param event when the user click the button.
     */
    @FXML
    private void responderBackButtonController(ActionEvent event) {
    try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }
}
