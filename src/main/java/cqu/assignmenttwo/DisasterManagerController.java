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
 * This class is to control the DisasterManager.fxml. It has multiple data
 * types to store the user's input and key events and key actions methods to
 * handle the user interaction.
 */
public class DisasterManagerController {

    // Variables related to the selection of the action plan.
    // Stores all the action plan.
    private List<ActionPlans> planList = new LinkedList<>();
    // Stores the action plan selected.
    private ObservableList<ActionPlans> actionPlans = 
            FXCollections.observableArrayList();
    // Stores the selected action plan from the disasterSelectionCombobox.
    private ActionPlans selectedActionPlan;
    // Stores the selected Review Plan Decision from the planReviewCombobox.
    private String selectedReviewPlanDecision;
    // Stores the provided Changes Required from the actionRequiredTextArea.
    private String providedChangesRequired;
    
    // Variables related to the selection of the action done.
    // Stores all the action done.
    private List<ActionsDone> actionsList = new LinkedList<>();
    // Stores the actions done selected.
    private ObservableList<ActionsDone> actionsDone = 
            FXCollections.observableArrayList();
    // Stores the selected disaster event from the disasterSelectionCombobox.
    private ActionsDone selectedActionDone;
    // Stores the selected Review Plan Decision from the planReviewCombobox.
    private String selectedReviewActionDecision;
    // Stores the provided Changes Required from the actionRequiredTextArea.
    private String providedAdditionalActions;

    // FXML labels, comboboxes, tableviews and tablecolumns. 
    @FXML
    private ComboBox<String> planSelectionCombobox;
    @FXML
    private ComboBox<String> planReviewCombobox;
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
    private ComboBox<String> actionDoneSelectionCombobox;
    @FXML
    private TableView<ActionsDone> actionDoneTableView;
    @FXML
    private TableColumn<ActionsDone, String> disasterIdActionDoneTable;
    @FXML
    private TableColumn<ActionsDone, String> authorityActionDoneTable;
    @FXML
    private TableColumn<ActionsDone, String> actionsDoneTable;
    @FXML
    private TableColumn<ActionsDone, String> actionsReviewTable;
    @FXML
    private TableColumn<ActionsDone, String> additionalActionsTable;
    @FXML
    private ComboBox<String> actionDoneReviewCombobox;
    @FXML
    private Label actionDoneErrorLabel;
    @FXML
    private TextArea actionDoneChangesTextArea;
    @FXML
    private TextArea planChangesTextArea;

    /**
     * This section is to initialize the Tableviews, Tablecolumns and to set 
     * arial font so it can be compatible with mac OS system.
     */
    @FXML
    private void initialize() {
        // Sets the font style of planSelectionCombobox
        planSelectionCombobox.setStyle("-fx-font-family: 'Arial'");
        // Load data from CSV file in the observableList
        actionPlans.setAll(FileUtility.loadPlanFromCsv("ActionPlan.csv"));
        // Populate ComboBox with disaster IDs
        planSelectionCombobox.getItems().addAll(getDisasterIdsForActionPlan());
        // Set up event handler for ComboBox
        planSelectionCombobox.setOnAction(this::handlePlanSelection);

        // Initialize actionPlanTableView columns
        disasterIdTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterId"));
        priorityTable.setCellValueFactory(
                new PropertyValueFactory<>("levelOfPriority"));
        // Use a string converter for ResponderAuthority
        authorityTable.setCellValueFactory(cellData
                -> new SimpleStringProperty(
                        cellData.getValue().getAuthorityRequired()));
        actionsRequiredTable.setCellValueFactory(
                new PropertyValueFactory<>("actionsRequired"));
        planReviewTable.setCellValueFactory(
                new PropertyValueFactory<>("planReview"));
        planChangesTable.setCellValueFactory(
                new PropertyValueFactory<>("planChanges"));
        // Sets the font style
        actionPlanTableView.setStyle("-fx-font-family: 'Arial'");

        // This section initializes the planReviewCombobox by populating it 
        // with two String options.
        if (planReviewCombobox != null) {
            // Initialize the planReviewCombobox with values Approve or 
            // Request Changes.
            ObservableList planReviewDecision = 
                    FXCollections.observableArrayList("Approve", "Request Changes");
            planReviewCombobox.setItems(planReviewDecision);
            // Sets the font style
            planReviewCombobox.setStyle("-fx-font-family: 'Arial'");
        }

        // Sets the font style of actionDoneSelectionCombobox
        actionDoneSelectionCombobox.setStyle("-fx-font-family: 'Arial'");
        // Load data from CSV file in the observableList
        actionsDone.setAll(FileUtility.loadActionsDoneFromCsv("ActionsDone.csv"));
        // Populate ComboBox with disaster IDs
        actionDoneSelectionCombobox.getItems().addAll(getDisasterIdsForActionsDone());
        // Set up event handler for ComboBox
        actionDoneSelectionCombobox.setOnAction(this::handlerActionDoneSelection);

        // Initialize actionDoneTableView columns
        disasterIdActionDoneTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterId"));
        authorityActionDoneTable.setCellValueFactory(cellData
                -> new SimpleStringProperty(
                        cellData.getValue().getAuthorityRequired()));
        actionsDoneTable.setCellValueFactory(
                new PropertyValueFactory<>("actionsDone"));
        actionsReviewTable.setCellValueFactory(
                new PropertyValueFactory<>("actionsDoneReview"));
        additionalActionsTable.setCellValueFactory(
                new PropertyValueFactory<>("additionalActions"));
        // Sets the font style
        actionDoneTableView.setStyle("-fx-font-family: 'Arial'");

        // This section initializes the actionDoneReviewCombobox by populating 
        // it with two String options.
        if (actionDoneReviewCombobox != null) {
            // Initialize the planReviewCombobox with values Approve or Request Changes.
            ObservableList actionReviewDecision = 
                    FXCollections.observableArrayList("Approve", "Request Additional Actions");
            actionDoneReviewCombobox.setItems(actionReviewDecision);
            // Sets the font style
            actionDoneReviewCombobox.setStyle("-fx-font-family: 'Arial'");
        }
    }

    /**
     * This section is to filter the action plans by disaster id.
     *
     * @return the action plan id selected.
     */
    private ObservableList<String> getDisasterIdsForActionPlan() {
        ObservableList<String> disasterIds = FXCollections.observableArrayList();
        for (ActionPlans actionPlan : actionPlans) {
            if (!disasterIds.contains(actionPlan.getDisasterId())) {
                disasterIds.add(actionPlan.getDisasterId());
            }
        }
        return disasterIds;
    }

    /**
     * This section is to handle the selection of the action plan in the
     * planSelectionCombobox.
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
            // Set the items in the table view
            actionPlanTableView.setItems(filteredReports);

            // If there is a selected event, store it in the class-level variable
            if (!filteredReports.isEmpty()) {
                selectedActionPlan = filteredReports.get(0);
            }
        }
    }

    /**
     * This sections handles the review decision for the action plan in the
     * planChangesTextArea.
     *
     * @param event the selection done by the user.
     */
    @FXML
    private void handlerPlanReviewSelection(ActionEvent event) {
        selectedReviewPlanDecision = planReviewCombobox.getValue();

    }

    /**
     * This sections handles the review changes required for the action plan in
     * the planChangesTextArea.
     *
     * @param event the user writes down the changes required.
     */
    @FXML
    private void changesRequiredTextArea(KeyEvent event) {
        providedChangesRequired = planChangesTextArea.getText();

        // This section is to enable the text area once the user select a 
        // review decision.
        if (selectedReviewPlanDecision != null) {
            planChangesTextArea.setEditable(true);
            // Sets the font style of planChangesTextArea
            planChangesTextArea.setStyle("-fx-font-family: 'Arial'");
        }
    }

    /**
     * This section is to handle the Complete review action plan button. It
     * checks if an action plan and if a review decision have been selected, to
     * create the action plan report. It also prints an error message in case
     * the mentioned conditions are not met.
     *
     * @param event the user click the completePlanReviewButton.
     */
    @FXML
    private void completePlanReviewButton(ActionEvent event) {

        if (selectedActionPlan != null && selectedReviewPlanDecision != null) {

            // Capture the data from the selected action plan.
            String disasterId = selectedActionPlan.getDisasterId();
            String levelOfPriority = selectedActionPlan.getLevelOfPriority();
            ResponderAuthority authorityRequired = 
                    ResponderAuthority.valueOf(selectedActionPlan.getAuthorityRequired());
            String actionsRequired = selectedActionPlan.getActionsRequired();

            // Create a new Action Plan
            ActionPlans actionPlan = new ActionPlans(
                    disasterId,
                    levelOfPriority,
                    authorityRequired,
                    actionsRequired,
                    selectedReviewPlanDecision,
                    providedChangesRequired
            );
            // Save the plan in the action plan list.
            planList.add(actionPlan);

            // Save the plan list to a CSV file.
            FileUtility.saveActionPlanToCsv(planList, "ActionPlan.csv");

            // Hides the error message when the action plan is created.
            planErrorLabel.setVisible(false);

            // Displays the Disaster manager menu screen.
            try {
                App.setRoot("DisasterManagerMenu");
            } catch (IOException e) {
                // Handle IOException if there is an issue loading the new screen
                e.printStackTrace();
            }

        } else {
            // Displays a message to inform there is an error.
            planErrorLabel.setText("Error. Please select an Action Plan and a "
                    + "Review Decision");
            planErrorLabel.setVisible(true);
        }
    }

    /**
     * This section is to filter the actions done by disaster id.
     *
     * @return the action done report selected.
     */
    private ObservableList<String> getDisasterIdsForActionsDone() {
        ObservableList<String> disasterIds = FXCollections.observableArrayList();
        for (ActionsDone actionDone : actionsDone) {
            if (!disasterIds.contains(actionDone.getDisasterId())) {
                disasterIds.add(actionDone.getDisasterId());
            }
        }
        return disasterIds;
    }

    /**
     * This section is to handle the selection in the actionDoneSelectionCombobox.
     * 
     * @param event the selection done by the user.
     */
    @FXML
    private void handlerActionDoneSelection(ActionEvent event) {
        String selectedId = actionDoneSelectionCombobox.getValue();
        if (selectedId != null) {
            // Filter the Actions done to find the selected one.
            ObservableList<ActionsDone> filteredReports = actionsDone.filtered(
                    actionDone -> actionDone.getDisasterId().equals(selectedId));
            // Set the items in the table view.
            actionDoneTableView.setItems(filteredReports);

            // If there is a selected action done, store it in the variable.
            if (!filteredReports.isEmpty()) {
                selectedActionDone = filteredReports.get(0);
            }
        }
    }

    /**
     * This section is to handle the selection in the actionDoneReviewCombobox.
     * 
     * @param event the selection done by the user.
     */
    @FXML
    private void handlerActionDoneReviewSelection(ActionEvent event) {
        selectedReviewActionDecision = actionDoneReviewCombobox.getValue();
    }

    @FXML
    private void actionRequiredTextArea(KeyEvent event) {
        providedAdditionalActions = actionDoneChangesTextArea.getText();

        // This section is to enable the text area once the user select a 
        // review decision.
        if (selectedReviewActionDecision != null) {
            actionDoneChangesTextArea.setEditable(true);
            // Sets the font style of planChangesTextArea
            actionDoneChangesTextArea.setStyle("-fx-font-family: 'Arial'");
        }
    }

    /**
     * This section is to handle the Complete review action done button. It
     * checks if an action done and if a review decision have been selected, to
     * create the action done report. It also prints an error message in case
     * the mentioned conditions are not met.
     *
     * @param event the user click the completeActionDoneReviewButton.
     */
    @FXML
    private void completeActionDoneReviewButton(ActionEvent event) {

        if (selectedActionDone != null && selectedReviewActionDecision != null) {

            // Capture the data from the selected action done.
            String disasterId = selectedActionDone.getDisasterId();
            ResponderAuthority authorityRequired = 
                    ResponderAuthority.valueOf(selectedActionDone.getAuthorityRequired());
            String actionsDone = selectedActionDone.getActionsDone();

            // Create a new Action Done.
            ActionsDone actionsDoneToReport = new ActionsDone(
                    disasterId,
                    authorityRequired,
                    actionsDone,
                    selectedReviewActionDecision,
                    providedAdditionalActions
            );
            // Save the action in the action done list
            actionsList.add(actionsDoneToReport);

            // Save the action list to a CSV file
            FileUtility.saveActionsDoneToCsv(actionsList, "ActionsDone.csv");

            // Hides the error message when the action done is created.
            actionDoneErrorLabel.setVisible(false);
            // Displays the Disaster manager menu screen.
            try {
                App.setRoot("DisasterManagerMenu");
            } catch (IOException e) {
                // Handle IOException if there is an issue loading the new screen
                e.printStackTrace();
            }
        } else {
            // Displays a message to inform there is an error. 
            actionDoneErrorLabel.setText("Error. Please select an Action Done "
                    + "and a Review Decision");
            actionDoneErrorLabel.setVisible(true);
        }
    }

    /**
     * This section is to handle the button go back to the main menu. If clicked
     * it will display the main menu screen.
     *
     * @param event the user click the main menu button.
     */
    @FXML
    private void managerBackButtonController(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }
}
