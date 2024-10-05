package cqu.assignmenttwo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private ObservableList<ActionPlans> actionPlans
            = FXCollections.observableArrayList();
    // Stores the selected disaster event from the disasterSelectionCombobox.
    private ActionPlans selectedActionPlan;
    // Stores the provided actiones done from the actionDoneTextArea.
    private String providedActionsDone;
    
    // Declare entity manager in class level to avoid redundancy 
    EntityManagerUtils emu = new EntityManagerUtils();
    EntityManager em = emu.getEm();
    
    // Get the logged-in user
    Staff loggedInUser = SessionManager.getInstance().getLoggedInUser();

    // FXML buttons, labels, combobox text areas and text fields.
    @FXML
    private ComboBox<Long> planSelectionCombobox;
    @FXML
    private TableView<ActionPlans> actionPlanTableView;
    @FXML
    private TableColumn<ActionPlans, Long> disasterIdTable;
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
    private TableColumn<ActionsDone, LocalDateTime> timeStampingActionsTable;
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
        
        // Load data from database.
        Query query = em.createNamedQuery("getAllActionPlans");
        List<ActionPlans> actionPlanList = query.getResultList();

        // Convert the list to an ObservableList
        ObservableList<ActionPlans> actionPlansListData = FXCollections.observableArrayList(actionPlanList);

        actionPlans.setAll(actionPlansListData);
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
        timeStampingActionsTable.setCellValueFactory(
                new PropertyValueFactory<>("timeStamping"));
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
    private ObservableList<Long> getDisasterIdsForActionPlan() {
        ObservableList<Long> disasterIds = FXCollections.observableArrayList();
        for (ActionPlans actionPlan : actionPlans) {
            if (!disasterIds.contains(actionPlan.getDisasterId())) {
                disasterIds.add(actionPlan.getDisasterId());
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
        Long selectedId = planSelectionCombobox.getValue();
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
     * This section is to handle the complete action done button. It validates
     * if an action plan was selected and the actions done were provided.
     *
     * @param event The user clicks the completeActionDone button.
     */
    @FXML
    private void completeActionDoneButton(ActionEvent event) {

        if (selectedActionPlan != null && providedActionsDone != null) {

            // Capture the data from the selected action plan.
            Long disasterId = selectedActionPlan.getDisasterId();
            ResponderAuthority authorityRequired
                    = ResponderAuthority.valueOf(selectedActionPlan.getAuthorityRequired());
            String actionsDone = providedActionsDone;

            try {
                // Query to check if an action done for this disasterId already exists
                Query queryActionDone = em.createNamedQuery("findRegisteredActionsDone");
                queryActionDone.setParameter("disasterId", disasterId);

                List<ActionsDone> existingActionsDone = queryActionDone.getResultList();

                em.getTransaction().begin();

                if (!existingActionsDone.isEmpty()) {
                    // If an action plan exists, overwrite it with the new data
                    ActionsDone actionDone = existingActionsDone.get(0); // Get the existing plan
                    actionDone.setAuthorityRequired(authorityRequired);
                    actionDone.setActionsDone(actionsDone);
                    actionDone.setActionsDoneReview("Pending Review");
                    // changes required are empty because the responder just adjust 
                    // the report.
                    actionDone.setAdditionalActions("");
                    LocalDateTime.now();
                    actionDone.setCreatedBy(loggedInUser); // Pass the logged-in user

                    em.merge(actionDone); // Update the existing action done

                } else {
                    // If no actions done exist, create a new one
                    ActionsDone actionsReport = new ActionsDone(
                            disasterId,
                            authorityRequired,
                            actionsDone,
                            // Manager review, it is filled with 0 because they are not 
                            //provided in this screen.
                            "Pending Review",
                            // Additional actions, it is filled with 0 because they are not 
                            //provided in this screen.
                            "",
                            LocalDateTime.now(),
                            loggedInUser // Pass the logged-in user
                    );
                    // Persist the new action done
                    em.persist(actionsReport);
                }

                em.getTransaction().commit(); // Commit the transaction

                // Hide error label after creating/updating the plan
                planErrorLabel.setVisible(false);

                // Redirect to the Emergency responder menu screen
                try {
                    App.setRoot("EmergencyResponderMenu");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close(); // Close the EntityManager
            }

        } else {
            // Displays a message to inform there is an error 
            planErrorLabel.setText("Error. Please select an Action Plan and "
                    + "Describe the actions done");
            planErrorLabel.setVisible(true);
        }
    }

    /**
     * This section is to handle the button go back to the main menu. If clicked
     * it will display the main menu screen.
     *
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
