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
import java.time.LocalDateTime;

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
    
    // Declare entity manager in class level to avoid redundancy 
    EntityManagerUtils emu = new EntityManagerUtils();
    EntityManager em = emu.getEm();
    
    // Get the logged-in user
    Staff loggedInUser = SessionManager.getInstance().getLoggedInUser();

    // FXML labels, comboboxes, tableviews and tablecolumns. 
    @FXML
    private Label notificationErrorLabel;
    @FXML
    private Label notificationCreatedLabel;
    @FXML
    private Label planErrorLabel;
    @FXML
    private ComboBox<Long> disasterSelectionCombobox;
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
    private TableColumn<DisasterEvent, Long> disasterIdTable;
    @FXML
    private TableColumn<DisasterEvent, LocalDate> disasterDateTable;
    @FXML
    private TableColumn<DisasterEvent, String> typeOfDisasterTable;
    @FXML
    private TableColumn<DisasterEvent, String> disasterLocationTable;
    @FXML
    private TableColumn<DisasterEvent, String> disasterDescriptionTable;
    @FXML
    private TableColumn<DisasterEvent, LocalDateTime> timeStampingTable;

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
                new PropertyValueFactory<>("id"));
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
        timeStampingTable.setCellValueFactory(
                new PropertyValueFactory<>("timeStamping"));

        // Sets the font style.
        disasterInformationTableView.setStyle("-fx-font-family: 'Arial'");

        // Load the notification data from the CSV file
        Query query = em.createNamedQuery("getAllDisasterEvents");
        List<DisasterEvent> disasterEventsList = query.getResultList();

        // Convert the list to an ObservableList
        ObservableList<DisasterEvent> disasterEventsListData = 
                FXCollections.observableArrayList(disasterEventsList);

        // Load data from CSV file in the observableList.
        disasterEvents.setAll(disasterEventsListData);

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
    private ObservableList<Long> getDisasterIds() {
        ObservableList<Long> disasterIds = FXCollections.observableArrayList();
        for (DisasterEvent disasterEvent : disasterEvents) {
            if (!disasterIds.contains(disasterEvent.getId())) {
                disasterIds.add(disasterEvent.getId());
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
        Long selectedId = disasterSelectionCombobox.getValue();
        if (selectedId != null) {
            // Filter the disasterEvents to find the selected one
            ObservableList<DisasterEvent> filteredReports = 
                    disasterEvents.filtered(disasterEvent -> 
                            disasterEvent.getId().equals(selectedId));
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
            String reporterName = selectedEvent.getReporterName();
            String disasterDate = selectedEvent.getDisasterDate();
            String typeOfDisaster = selectedEvent.getTypeOfDisasterAsString();
            String disasterLocation = selectedEvent.getDisasterLocation();
            String disasterDescription = selectedEvent.getDisasterDescription();
            
            // Validate existent notifications to prevent duplicities.
            try {
            Query queryNotification = em.createNamedQuery("findRegisteredNotifications");
            queryNotification.setParameter("disasterId", disasterId);
            List<NotificationAlert> existingAlerts =  queryNotification.getResultList();

            if (!existingAlerts.isEmpty()) {
                // Display an error message if an alert already exists
                notificationErrorLabel.setText("A Notification Alert already exists for this disaster.");
                notificationErrorLabel.setVisible(true);
                notificationCreatedLabel.setVisible(false);
                return; // Stop further execution
            }
            
            // Create a new NotificationAlert
            NotificationAlert notificationAlert = new NotificationAlert(
                    disasterId,
                    reporterName,
                    disasterDate,
                    typeOfDisaster,
                    disasterLocation,
                    disasterDescription,
                    selectedPriorityLevel,
                    LocalDateTime.now(),
                    loggedInUser // Pass the logged-in user
            );
 
            em.getTransaction().begin();
            em.persist(notificationAlert);
            em.getTransaction().commit();

            // Displays a message to confirm the notification has been created
            notificationCreatedLabel.setText("The Notification Alert has been "
                    + "created");
            notificationCreatedLabel.setVisible(true);

            // Hides the error message when the notification is created.
            notificationErrorLabel.setVisible(false);
            
            } catch (Exception e) {
            e.printStackTrace();
        }

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

            try {
                // Query to check if an action plan for this disasterId already exists
                Query queryActionPlan = em.createNamedQuery("findRegisteredActionPlans");
                queryActionPlan.setParameter("disasterId", disasterId);

                List<ActionPlans> existingActionPlans = queryActionPlan.getResultList();

                em.getTransaction().begin();

                if (!existingActionPlans.isEmpty()) {
                    // If an action plan exists, overwrite it with the new data
                    ActionPlans actionPlan = existingActionPlans.get(0); // Get the existing plan
                    actionPlan.setLevelOfPriority(selectedPriorityLevel);
                    actionPlan.setAuthorityRequired(selectedAuthorityRequired);
                    actionPlan.setActionsRequired(providedActions);
                    actionPlan.setPlanReview("Pending Review");
                    // changes required are empty because the assistant just adjust 
                    // the report.
                    actionPlan.setPlanChanges("");
                    LocalDateTime.now();
                    actionPlan.setCreatedBy(loggedInUser); // Pass the logged-in user

                    em.merge(actionPlan); // Update the existing action plan

                } else {
                    // If no action plan exists, create a new one
                    ActionPlans actionPlan = new ActionPlans(
                            disasterId,
                            selectedPriorityLevel,
                            selectedAuthorityRequired,
                            providedActions,
                            "Pending Review",
                            "", // No changes required initially
                            LocalDateTime.now(),
                            loggedInUser // Pass the logged-in user
                            
                    );

                    em.persist(actionPlan); // Persist the new action plan

                }

                em.getTransaction().commit(); // Commit the transaction

                // Hide error label after creating/updating the plan
                planErrorLabel.setVisible(false);

                // Redirect to the Disaster Assistant menu screen
                try {
                    App.setRoot("DisasterAssistantMenu");
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
            planErrorLabel.setText("Error. Please select a disaster event, "
                    + "the level of priority, \nthe authority required and "
                    + "provide the actions required");
            planErrorLabel.setVisible(true);
        }
    }

    public List<NotificationAlert> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationAlert> notificationList) {
        this.notificationList = notificationList;
    }

    public List<ActionPlans> getPlanList() {
        return planList;
    }

    public void setPlanList(List<ActionPlans> planList) {
        this.planList = planList;
    }

    public ObservableList<DisasterEvent> getDisasterEvents() {
        return disasterEvents;
    }

    public void setDisasterEvents(ObservableList<DisasterEvent> disasterEvents) {
        this.disasterEvents = disasterEvents;
    }

    public DisasterEvent getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(DisasterEvent selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public String getSelectedPriorityLevel() {
        return selectedPriorityLevel;
    }

    public void setSelectedPriorityLevel(String selectedPriorityLevel) {
        this.selectedPriorityLevel = selectedPriorityLevel;
    }

    public ResponderAuthority getSelectedAuthorityRequired() {
        return selectedAuthorityRequired;
    }

    public void setSelectedAuthorityRequired(ResponderAuthority selectedAuthorityRequired) {
        this.selectedAuthorityRequired = selectedAuthorityRequired;
    }

    public String getProvidedActions() {
        return providedActions;
    }

    public void setProvidedActions(String providedActions) {
        this.providedActions = providedActions;
    }

    public EntityManagerUtils getEmu() {
        return emu;
    }

    public void setEmu(EntityManagerUtils emu) {
        this.emu = emu;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Staff getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Staff loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Label getNotificationErrorLabel() {
        return notificationErrorLabel;
    }

    public void setNotificationErrorLabel(Label notificationErrorLabel) {
        this.notificationErrorLabel = notificationErrorLabel;
    }

    public Label getNotificationCreatedLabel() {
        return notificationCreatedLabel;
    }

    public void setNotificationCreatedLabel(Label notificationCreatedLabel) {
        this.notificationCreatedLabel = notificationCreatedLabel;
    }

    public Label getPlanErrorLabel() {
        return planErrorLabel;
    }

    public void setPlanErrorLabel(Label planErrorLabel) {
        this.planErrorLabel = planErrorLabel;
    }

    public ComboBox<Long> getDisasterSelectionCombobox() {
        return disasterSelectionCombobox;
    }

    public void setDisasterSelectionCombobox(ComboBox<Long> disasterSelectionCombobox) {
        this.disasterSelectionCombobox = disasterSelectionCombobox;
    }

    public ComboBox<ResponderAuthority> getAuthorityRequiredCombobox() {
        return authorityRequiredCombobox;
    }

    public void setAuthorityRequiredCombobox(ComboBox<ResponderAuthority> authorityRequiredCombobox) {
        this.authorityRequiredCombobox = authorityRequiredCombobox;
    }

    public TextArea getActionRequiredTextArea() {
        return actionRequiredTextArea;
    }

    public void setActionRequiredTextArea(TextArea actionRequiredTextArea) {
        this.actionRequiredTextArea = actionRequiredTextArea;
    }

    public ComboBox<String> getPriorityLevelCombobox() {
        return priorityLevelCombobox;
    }

    public void setPriorityLevelCombobox(ComboBox<String> priorityLevelCombobox) {
        this.priorityLevelCombobox = priorityLevelCombobox;
    }

    public TableView<DisasterEvent> getDisasterInformationTableView() {
        return disasterInformationTableView;
    }

    public void setDisasterInformationTableView(TableView<DisasterEvent> disasterInformationTableView) {
        this.disasterInformationTableView = disasterInformationTableView;
    }

    public TableColumn<DisasterEvent, String> getReporterNameTable() {
        return reporterNameTable;
    }

    public void setReporterNameTable(TableColumn<DisasterEvent, String> reporterNameTable) {
        this.reporterNameTable = reporterNameTable;
    }

    public TableColumn<DisasterEvent, Long> getDisasterIdTable() {
        return disasterIdTable;
    }

    public void setDisasterIdTable(TableColumn<DisasterEvent, Long> disasterIdTable) {
        this.disasterIdTable = disasterIdTable;
    }

    public TableColumn<DisasterEvent, LocalDate> getDisasterDateTable() {
        return disasterDateTable;
    }

    public void setDisasterDateTable(TableColumn<DisasterEvent, LocalDate> disasterDateTable) {
        this.disasterDateTable = disasterDateTable;
    }

    public TableColumn<DisasterEvent, String> getTypeOfDisasterTable() {
        return typeOfDisasterTable;
    }

    public void setTypeOfDisasterTable(TableColumn<DisasterEvent, String> typeOfDisasterTable) {
        this.typeOfDisasterTable = typeOfDisasterTable;
    }

    public TableColumn<DisasterEvent, String> getDisasterLocationTable() {
        return disasterLocationTable;
    }

    public void setDisasterLocationTable(TableColumn<DisasterEvent, String> disasterLocationTable) {
        this.disasterLocationTable = disasterLocationTable;
    }

    public TableColumn<DisasterEvent, String> getDisasterDescriptionTable() {
        return disasterDescriptionTable;
    }

    public void setDisasterDescriptionTable(TableColumn<DisasterEvent, String> disasterDescriptionTable) {
        this.disasterDescriptionTable = disasterDescriptionTable;
    }

    public TableColumn<DisasterEvent, LocalDateTime> getTimeStampingTable() {
        return timeStampingTable;
    }

    public void setTimeStampingTable(TableColumn<DisasterEvent, LocalDateTime> timeStampingTable) {
        this.timeStampingTable = timeStampingTable;
    }
}
