package cqu.assignmenttwo;

import java.io.IOException;
import java.time.LocalDate;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 *
 * @author AndresPinilla 12243141
 *
 * This class is to control the DisasterAssistant.fxml. It has multiple data
 * types to store the user's input and key events and key actions methods to
 * handle the user interaction.
 */
public class DisasterAssistantController {

    // Stores all the notification alerts.
    private List<NotificationAlert> notificationList = new LinkedList<>();
    // Stores all the action plans.
    private List<ActionPlans> planList = new LinkedList<>();
    // Stores the disaster event selected.
    private ObservableList<DisasterEvent> disasterEvents
            = FXCollections.observableArrayList();
    // Stores the selected disaster event from the disasterSelectionCombobox.
    private DisasterEvent selectedEvent;
    // Stores the selected priority level from the priorityLevelCombobox.
    private String selectedPriorityLevel;
    // Stores the selected authority required from the authorityRequiredCombobox
    private ResponderAuthority selectedAuthorityRequired;
    // Stores the actions required from the actionRequiredTextArea.
    private String providedActions;

    // FXML labels, comboboxes, tableviews and tablecolumns. 
    @FXML
    private Label notificationErrorLabel;
    @FXML
    private Label notificationCreatedLabel;
    @FXML
    private Label planErrorLabel;
    @FXML
    private ComboBox<String> disasterSelectionCombobox;
    @FXML
    private ComboBox<ResponderAuthority> authorityRequiredCombobox;
    @FXML
    private TextArea actionRequiredTextArea;
    @FXML
    private ComboBox<String> priorityLevelCombobox;
    @FXML
    private TableView<DisasterEvent> disasterInformationTableView;
    @FXML
    private TableColumn<DisasterEvent, String> reporterNameTable;
    @FXML
    private TableColumn<DisasterEvent, String> disasterIdTable;
    @FXML
    private TableColumn<DisasterEvent, LocalDate> disasterDateTable;
    @FXML
    private TableColumn<DisasterEvent, String> typeOfDisasterTable;
    @FXML
    private TableColumn<DisasterEvent, String> disasterLocationTable;
    @FXML
    private TableColumn<DisasterEvent, String> disasterDescriptionTable;

    /**
     * This section is to initialize the Tableviews, Tablecolumns and to set
     * arial font so it can be compatible with mac OS system.
     */
    @FXML
    private void initialize() {
        // Initialize TableView columns.
        reporterNameTable.setCellValueFactory(
                new PropertyValueFactory<>("reporterName"));
        disasterIdTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterId"));
        disasterDateTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterDate"));
        // Use a string converter for typeOfDisaster enum.
        typeOfDisasterTable.setCellValueFactory(cellData
                -> new SimpleStringProperty(
                        cellData.getValue().getTypeOfDisasterAsString()));
        disasterLocationTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterLocation"));
        disasterDescriptionTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterDescription"));

        // Sets the font style.
        disasterInformationTableView.setStyle("-fx-font-family: 'Arial'");

        // Load the notification data from the CSV file
        EntityManagerUtils emu = new EntityManagerUtils();
        EntityManager em = emu.getEm();
        Query query = em.createNamedQuery("getAllDisasterEvents");
        List<DisasterEvent> disasterEventsList = query.getResultList();

        // Convert the list to an ObservableList
        ObservableList<DisasterEvent> disasterEvents = FXCollections.observableArrayList(disasterEventsList);

        // Load data from CSV file in the observableList.
        disasterEvents.setAll(disasterEvents);

        // Populate ComboBox with disaster IDs.
        disasterSelectionCombobox.getItems().addAll(getDisasterIds());

        // Set up event handler for ComboBox.
        disasterSelectionCombobox.setOnAction(this::handleDisasterSelection);

        // Sets the font style.
        disasterSelectionCombobox.setStyle("-fx-font-family: 'Arial'");

        // Initializes the combobox with the authority options.
        if (authorityRequiredCombobox != null) {
            ObservableList<ResponderAuthority> responders
                    = FXCollections.observableArrayList(ResponderAuthority.values());
            authorityRequiredCombobox.setItems(responders);
            // Sets the font style
            authorityRequiredCombobox.setStyle("-fx-font-family: 'Arial'");
        }

        // Initializes the combobox with the priority level options.
        if (priorityLevelCombobox != null) {
            // Initialize the ComboBox with values Yes and No
            ObservableList priorityOptions
                    = FXCollections.observableArrayList("High", "Moderate", "Low");
            priorityLevelCombobox.setItems(priorityOptions);
            // Sets the font style
            priorityLevelCombobox.setStyle("-fx-font-family: 'Arial'");
        }

        // Sets the font style
        actionRequiredTextArea.setStyle("-fx-font-family: 'Arial'");
    }

    /**
     * This section is to filter the disaster events by disaster id.
     *
     * @return the disasterId selected from the DisasterEvents.csv
     */
    private ObservableList<String> getDisasterIds() {
        ObservableList<String> disasterIds = FXCollections.observableArrayList();
        for (DisasterEvent disasterEvent : disasterEvents) {
            if (!disasterIds.contains(disasterEvent.getId().toString())) {
                disasterIds.add(disasterEvent.getId().toString());
            }
        }
        return disasterIds;
    }

    /**
     * This section handles the selection from the disasterSelectionCombobox.
     *
     * @param event the disaster selection done by the user.
     */
    @FXML
    private void handleDisasterSelection(ActionEvent event) {
        String selectedId = disasterSelectionCombobox.getValue();
        if (selectedId != null) {
            // Filter the disasterEvents to find the selected one
            ObservableList<DisasterEvent> filteredReports
                    = disasterEvents.filtered(disasterEvent
                            -> disasterEvent.getId().toString().equals(selectedId));
            // Set the items in the table view
            disasterInformationTableView.setItems(filteredReports);

            // If there is a selected event, store it in the class-level variable
            if (!filteredReports.isEmpty()) {
                selectedEvent = filteredReports.get(0);
            }
        }
    }

    /**
     * This section handles the selection from the priorityLevelCombobox.
     *
     * @param event The priority selection done by the user.
     */
    @FXML
    private void handlerPrioritySelection(ActionEvent event) {
        selectedPriorityLevel = priorityLevelCombobox.getValue();
    }

    /**
     * This section handles the selection from the authorityRequiredCombobox
     *
     * @param event the authority selection done by the user.
     */
    @FXML
    private void handlerAuthoritySelection(ActionEvent event) {
        selectedAuthorityRequired = authorityRequiredCombobox.getValue();
    }

    /**
     * This section handles the main menu button, if clicked it displays the
     * primary screen.
     *
     * @param event the user click the main menu button
     */
    @FXML
    private void assistantBackButtonController(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen.
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the alert button and create the notification
     * alert. It creates the notification alert and store it in a csv file.
     *
     * @param event
     */
    @FXML
    private void alertButton(ActionEvent event) {

        // Validates if there is an event and a priority level selected.
        if (selectedEvent != null && selectedPriorityLevel != null) {
            // Capture the data from the selected event
            Long disasterId = selectedEvent.getId();
            String disasterDate = selectedEvent.getDisasterDate();
            String typeOfDisaster = selectedEvent.getTypeOfDisasterAsString();
            String disasterLocation = selectedEvent.getDisasterLocation();
            String disasterDescription = selectedEvent.getDisasterDescription();

            // Create a new NotificationAlert
            NotificationAlert notificationAlert = new NotificationAlert(
                    disasterId,
                    disasterDate,
                    typeOfDisaster,
                    disasterLocation,
                    disasterDescription,
                    selectedPriorityLevel
            );

            // Save the data in the database
            EntityManagerUtils emu = new EntityManagerUtils();
            EntityManager em = emu.getEm();
            em.getTransaction().begin();
            em.persist(notificationAlert);
            em.getTransaction().commit();

            // Displays a message to confirm the notification has been created
            notificationCreatedLabel.setText("The Notification Alert has been "
                    + "created");
            notificationCreatedLabel.setVisible(true);

            // Hides the error message when the notification is created.
            notificationErrorLabel.setVisible(false);

        } else {
            // Displays a message to inform there is an error 
            notificationErrorLabel.setText("Error. Please select a disaster "
                    + "event and level of priority");
            notificationErrorLabel.setVisible(true);
        }
    }

    /**
     * This section handles the actions provided in the actionRequiredTextArea.
     *
     * @param event the user write down the actions required.
     */
    @FXML
    private void actionRequiredTextArea(KeyEvent event) {
        providedActions = actionRequiredTextArea.getText();
    }

    /**
     * This section is to control the Create action plan button. It creates the
     * action plan and store it in a CSV file.
     *
     * @param event
     */
    @FXML
    private void createActionPlanButton(ActionEvent event) {
        // Validates if an event, a priority level and an authority required 
        // were selected.
        if (selectedEvent != null && selectedPriorityLevel != null
                && selectedAuthorityRequired != null && providedActions != null) {
            // Capture the data from the selected event
            Long disasterId = selectedEvent.getId();

            // Create a new Action Plan
            ActionPlans actionPlan = new ActionPlans(
                    disasterId,
                    selectedPriorityLevel,
                    selectedAuthorityRequired,
                    providedActions,
                    // Manager review, it is filled with 0 because they are not 
                    //provided in this screen.
                    "0",
                    // Changes required, it is filled with 0 because they are 
                    // not provided in this screen.
                    "0"
            );

            // Store action plan in the database
            EntityManagerUtils emu = new EntityManagerUtils();
            EntityManager em = emu.getEm();
            em.getTransaction().begin();
            em.persist(actionPlan);
            em.getTransaction().commit();
            // Save the action plan in the plan list.
//            planList.add(actionPlan);
//
//            // Save the plan list to a CSV file.
//            FileUtility.saveActionPlanToCsv(planList, "ActionPlan.csv");
            // Hides the error message because the action plan was created.
            planErrorLabel.setVisible(false);

            // Displays the Disaster assistant menu screen.
            try {
                App.setRoot("DisasterAssistantMenu");
            } catch (IOException e) {
                // Handle IOException if there is an issue loading the new screen
                e.printStackTrace();
            }

        } else {
            // Displays a message to inform there is an error 
            planErrorLabel.setText("Error. Please select a disaster event, "
                    + "the level of priority, \nthe authority required and "
                    + "provide the actions required");
            planErrorLabel.setVisible(true);

        }
    }

}
