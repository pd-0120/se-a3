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
 * This class is to control the DisasterManager.fxml. It has multiple data types
 * to store the user's input and key events and key actions methods to handle
 * the user interaction.
 */
public class DisasterManagerController {

    // Variables related to the selection of the action plan.
    // Stores all the action plan.
    private List<ActionPlans> planList = new LinkedList<>();
    // Stores the action plan selected.
    private ObservableList<ActionPlans> actionPlans
            = FXCollections.observableArrayList();
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
    private ObservableList<ActionsDone> actionsDone
            = FXCollections.observableArrayList();
    // Stores the selected disaster event from the disasterSelectionCombobox.
    private ActionsDone selectedActionDone;
    // Stores the selected Review Plan Decision from the planReviewCombobox.
    private String selectedReviewActionDecision;
    // Stores the provided Changes Required from the actionRequiredTextArea.
    private String providedAdditionalActions;
    
    // Declare entity manager in class level to avoid redundancy 
    EntityManagerUtils emu = new EntityManagerUtils();
    EntityManager em = emu.getEm();
    
    // Get the logged-in user
    Staff loggedInUser = SessionManager.getInstance().getLoggedInUser();

    // FXML labels, comboboxes, tableviews and tablecolumns. 
    @FXML
    private ComboBox<Long> planSelectionCombobox;
    @FXML
    private ComboBox<String> planReviewCombobox;
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
    private TableColumn<ActionPlans, LocalDateTime> timeStampingTable;
    @FXML
    private TableColumn<ActionPlans, String> createdByTable;
    @FXML
    private Label planErrorLabel;
    @FXML
    private ComboBox<Long> actionDoneSelectionCombobox;
    @FXML
    private TableView<ActionsDone> actionDoneTableView;
    @FXML
    private TableColumn<ActionsDone, Long> disasterIdActionDoneTable;
    @FXML
    private TableColumn<ActionsDone, String> authorityActionDoneTable;
    @FXML
    private TableColumn<ActionsDone, String> actionsDoneTable;
    @FXML
    private TableColumn<ActionsDone, String> actionsReviewTable;
    @FXML
    private TableColumn<ActionsDone, String> additionalActionsTable;
    @FXML
    private TableColumn<ActionsDone, LocalDateTime> timeStampingActionsTable;
    @FXML
    private TableColumn<ActionsDone, String> createdByActionsTable;
    @FXML
    private ComboBox<String> actionDoneReviewCombobox;
    @FXML
    private Label actionDoneErrorLabel;
    @FXML
    private TextArea actionDoneChangesTextArea;
    @FXML
    private TextArea planChangesTextArea;

    /**
     * This section is to initialize the Table views, Table columns and to set
     * arial font so it can be compatible with mac OS system.
     */
    @FXML
    private void initialize() {
        // Sets the font style of planSelectionCombobox
        planSelectionCombobox.setStyle("-fx-font-family: 'Arial'");
        // Load data from CSV file in the observableList
        
        Query query = em.createNamedQuery("getAllActionPlans");
        List<ActionPlans> actionPlanList = query.getResultList();

        // Convert the list to an ObservableList
        ObservableList<ActionPlans> actionPlansListData = 
                FXCollections.observableArrayList(actionPlanList);

        actionPlans.setAll(actionPlansListData);
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
        timeStampingTable.setCellValueFactory(
                new PropertyValueFactory<>("timeStamping"));
        createdByTable.setCellValueFactory(cellData -> {
            // Get the Staff entity linked to the Action Plan (createdBy)
            Staff createdByStaff = cellData.getValue().getCreatedBy();

            // Stores the retrieved name
            String staffName = createdByStaff.getName();

            // Return a SimpleStringProperty wrapped in an ObservableValue
            return new SimpleStringProperty(staffName);
        });
        // Sets the font style
        actionPlanTableView.setStyle("-fx-font-family: 'Arial'");

        // This section initializes the planReviewCombobox by populating it 
        // with two String options.
        if (planReviewCombobox != null) {
            // Initialize the planReviewCombobox with values Approve or 
            // Request Changes.
            ObservableList planReviewDecision
                    = FXCollections.observableArrayList("Approve", "Request Changes");
            planReviewCombobox.setItems(planReviewDecision);
            // Sets the font style
            planReviewCombobox.setStyle("-fx-font-family: 'Arial'");
        }

        // Sets the font style of actionDoneSelectionCombobox
        actionDoneSelectionCombobox.setStyle("-fx-font-family: 'Arial'");
        
        // Query to retrieve the  actions done regustered
        Query queryActionsDone = em.createNamedQuery("getAllActionsDone");
        List<ActionsDone> actionsDoneList = queryActionsDone.getResultList();

        // Convert the list to an ObservableList
        ObservableList<ActionsDone> actionsDoneListData = 
                FXCollections.observableArrayList(actionsDoneList);

        actionsDone.setAll(actionsDoneListData);
               
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
        timeStampingActionsTable.setCellValueFactory(
                new PropertyValueFactory<>("timeStamping"));
        createdByActionsTable.setCellValueFactory(cellData -> {
            // Get the Staff entity linked to the Actions Done (createdBy)
            Staff createdByStaff = cellData.getValue().getCreatedBy();

            // Stores the retrieved name
            String staffName = createdByStaff.getName();

            // Return a SimpleStringProperty wrapped in an ObservableValue
            return new SimpleStringProperty(staffName);
        });
        // Sets the font style
        actionDoneTableView.setStyle("-fx-font-family: 'Arial'");

        // This section initializes the actionDoneReviewCombobox by populating 
        // it with two String options.
        if (actionDoneReviewCombobox != null) {
            // Initialize the planReviewCombobox with values Approve or Request Changes.
            ObservableList actionReviewDecision
                    = FXCollections.observableArrayList("Approve", "Request Additional Actions");
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
    private ObservableList<Long> getDisasterIdsForActionPlan() {
        ObservableList<Long> disasterIds = FXCollections.observableArrayList();
        for (ActionPlans actionPlan : actionPlans) {
            if (!disasterIds.contains(actionPlan.getDisasterId())) {
                disasterIds.add(actionPlan.getDisasterId());
            }
        }
        System.out.println(disasterIds);
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
        Long selectedId = planSelectionCombobox.getValue();
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
            Long disasterId = selectedActionPlan.getDisasterId();
            String levelOfPriority = selectedActionPlan.getLevelOfPriority();
            ResponderAuthority authorityRequired
                    = ResponderAuthority.valueOf(selectedActionPlan.getAuthorityRequired());
            String actionsRequired = selectedActionPlan.getActionsRequired();

            try {
            // Query to check if an action plan for this disasterId already exists
            Query queryActionPlan = em.createNamedQuery("findRegisteredActionPlans");
            queryActionPlan.setParameter("disasterId", disasterId);
            
            List<ActionPlans> existingActionPlans = queryActionPlan.getResultList();
            
            em.getTransaction().begin();
            
            if (!existingActionPlans.isEmpty()) {
                // If an action plan exists, overwrite it with the new data
                ActionPlans actionPlan = existingActionPlans.get(0); // Get the existing plan
                actionPlan.setLevelOfPriority(levelOfPriority);
                actionPlan.setAuthorityRequired(authorityRequired);
                actionPlan.setActionsRequired(actionsRequired);
                actionPlan.setPlanReview(selectedReviewPlanDecision);
                actionPlan.setPlanChanges(providedChangesRequired); 
                LocalDateTime.now();
                actionPlan.setCreatedBy(loggedInUser); // Pass the logged-in user

                em.merge(actionPlan); // Update the existing action plan
                 
            } else {
                // If no action plan exists, create a new one
                ActionPlans actionPlan = new ActionPlans(
                    disasterId,
                    levelOfPriority,
                    authorityRequired,
                    actionsRequired,
                    selectedReviewPlanDecision,
                    providedChangesRequired,
                    LocalDateTime.now(),
                    loggedInUser // Pass the logged-in user
                );
                
                em.persist(actionPlan); // Persist the new action plan
            }
            
            em.getTransaction().commit(); // Commit the transaction
            
            // Hide error label after creating/updating the plan
            planErrorLabel.setVisible(false);
            
            // Displays the Disaster manager menu screen.
            try {
                App.setRoot("DisasterManagerMenu");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close(); // Close the EntityManager
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
    private ObservableList<Long> getDisasterIdsForActionsDone() {
        ObservableList<Long> disasterIds = FXCollections.observableArrayList();
        for (ActionsDone actionDone : actionsDone) {
            if (!disasterIds.contains(actionDone.getDisasterId())) {
                disasterIds.add(actionDone.getDisasterId());
            }
        }
        return disasterIds;
    }

    /**
     * This section is to handle the selection in the
     * actionDoneSelectionCombobox.
     *
     * @param event the selection done by the user.
     */
    @FXML
    private void handlerActionDoneSelection(ActionEvent event) {
        Long selectedId = actionDoneSelectionCombobox.getValue();
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
            Long disasterId = selectedActionDone.getDisasterId();
            ResponderAuthority authorityRequired
                    = ResponderAuthority.valueOf(selectedActionDone.getAuthorityRequired());
            String actionsDoneObj = selectedActionDone.getActionsDone();

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
                    actionDone.setActionsDone(actionsDoneObj);
                    actionDone.setActionsDoneReview(selectedReviewActionDecision);
                    // changes required are empty because the responder just adjust 
                    // the report.
                    actionDone.setAdditionalActions(providedAdditionalActions);
                    LocalDateTime.now();
                    actionDone.setCreatedBy(loggedInUser); // Pass the logged-in user

                    em.merge(actionDone); // Update the existing action done

                } else {
                    // If no actions done exist, create a new one
                    ActionsDone actionsReport = new ActionsDone(
                            disasterId,
                            authorityRequired,
                            actionsDoneObj,
                            selectedReviewActionDecision,
                            providedAdditionalActions,
                            LocalDateTime.now(),
                            loggedInUser // Pass the logged-in user
                    );
                    // Persist the new action done
                    em.persist(actionsReport);
                }

                em.getTransaction().commit(); // Commit the transaction

                // Hide error label after creating/updating the plan
                planErrorLabel.setVisible(false);

                // Displays the Disaster manager menu screen.
                try {
                    App.setRoot("DisasterManagerMenu");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close(); // Close the EntityManager
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

    public List<ActionPlans> getPlanList() {
        return planList;
    }

    public void setPlanList(List<ActionPlans> planList) {
        this.planList = planList;
    }

    public ObservableList<ActionPlans> getActionPlans() {
        return actionPlans;
    }

    public void setActionPlans(ObservableList<ActionPlans> actionPlans) {
        this.actionPlans = actionPlans;
    }

    public ActionPlans getSelectedActionPlan() {
        return selectedActionPlan;
    }

    public void setSelectedActionPlan(ActionPlans selectedActionPlan) {
        this.selectedActionPlan = selectedActionPlan;
    }

    public String getSelectedReviewPlanDecision() {
        return selectedReviewPlanDecision;
    }

    public void setSelectedReviewPlanDecision(String selectedReviewPlanDecision) {
        this.selectedReviewPlanDecision = selectedReviewPlanDecision;
    }

    public String getProvidedChangesRequired() {
        return providedChangesRequired;
    }

    public void setProvidedChangesRequired(String providedChangesRequired) {
        this.providedChangesRequired = providedChangesRequired;
    }

    public List<ActionsDone> getActionsList() {
        return actionsList;
    }

    public void setActionsList(List<ActionsDone> actionsList) {
        this.actionsList = actionsList;
    }

    public ObservableList<ActionsDone> getActionsDone() {
        return actionsDone;
    }

    public void setActionsDone(ObservableList<ActionsDone> actionsDone) {
        this.actionsDone = actionsDone;
    }

    public ActionsDone getSelectedActionDone() {
        return selectedActionDone;
    }

    public void setSelectedActionDone(ActionsDone selectedActionDone) {
        this.selectedActionDone = selectedActionDone;
    }

    public String getSelectedReviewActionDecision() {
        return selectedReviewActionDecision;
    }

    public void setSelectedReviewActionDecision(String selectedReviewActionDecision) {
        this.selectedReviewActionDecision = selectedReviewActionDecision;
    }

    public String getProvidedAdditionalActions() {
        return providedAdditionalActions;
    }

    public void setProvidedAdditionalActions(String providedAdditionalActions) {
        this.providedAdditionalActions = providedAdditionalActions;
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

    public ComboBox<Long> getPlanSelectionCombobox() {
        return planSelectionCombobox;
    }

    public void setPlanSelectionCombobox(ComboBox<Long> planSelectionCombobox) {
        this.planSelectionCombobox = planSelectionCombobox;
    }

    public ComboBox<String> getPlanReviewCombobox() {
        return planReviewCombobox;
    }

    public void setPlanReviewCombobox(ComboBox<String> planReviewCombobox) {
        this.planReviewCombobox = planReviewCombobox;
    }

    public TableView<ActionPlans> getActionPlanTableView() {
        return actionPlanTableView;
    }

    public void setActionPlanTableView(TableView<ActionPlans> actionPlanTableView) {
        this.actionPlanTableView = actionPlanTableView;
    }

    public TableColumn<ActionPlans, Long> getDisasterIdTable() {
        return disasterIdTable;
    }

    public void setDisasterIdTable(TableColumn<ActionPlans, Long> disasterIdTable) {
        this.disasterIdTable = disasterIdTable;
    }

    public TableColumn<ActionPlans, String> getPriorityTable() {
        return priorityTable;
    }

    public void setPriorityTable(TableColumn<ActionPlans, String> priorityTable) {
        this.priorityTable = priorityTable;
    }

    public TableColumn<ActionPlans, String> getAuthorityTable() {
        return authorityTable;
    }

    public void setAuthorityTable(TableColumn<ActionPlans, String> authorityTable) {
        this.authorityTable = authorityTable;
    }

    public TableColumn<ActionPlans, String> getActionsRequiredTable() {
        return actionsRequiredTable;
    }

    public void setActionsRequiredTable(TableColumn<ActionPlans, String> actionsRequiredTable) {
        this.actionsRequiredTable = actionsRequiredTable;
    }

    public TableColumn<ActionPlans, String> getPlanReviewTable() {
        return planReviewTable;
    }

    public void setPlanReviewTable(TableColumn<ActionPlans, String> planReviewTable) {
        this.planReviewTable = planReviewTable;
    }

    public TableColumn<ActionPlans, String> getPlanChangesTable() {
        return planChangesTable;
    }

    public void setPlanChangesTable(TableColumn<ActionPlans, String> planChangesTable) {
        this.planChangesTable = planChangesTable;
    }

    public TableColumn<ActionPlans, LocalDateTime> getTimeStampingTable() {
        return timeStampingTable;
    }

    public void setTimeStampingTable(TableColumn<ActionPlans, LocalDateTime> timeStampingTable) {
        this.timeStampingTable = timeStampingTable;
    }

    public TableColumn<ActionPlans, String> getCreatedByTable() {
        return createdByTable;
    }

    public void setCreatedByTable(TableColumn<ActionPlans, String> createdByTable) {
        this.createdByTable = createdByTable;
    }

    public Label getPlanErrorLabel() {
        return planErrorLabel;
    }

    public void setPlanErrorLabel(Label planErrorLabel) {
        this.planErrorLabel = planErrorLabel;
    }

    public ComboBox<Long> getActionDoneSelectionCombobox() {
        return actionDoneSelectionCombobox;
    }

    public void setActionDoneSelectionCombobox(ComboBox<Long> actionDoneSelectionCombobox) {
        this.actionDoneSelectionCombobox = actionDoneSelectionCombobox;
    }

    public TableView<ActionsDone> getActionDoneTableView() {
        return actionDoneTableView;
    }

    public void setActionDoneTableView(TableView<ActionsDone> actionDoneTableView) {
        this.actionDoneTableView = actionDoneTableView;
    }

    public TableColumn<ActionsDone, Long> getDisasterIdActionDoneTable() {
        return disasterIdActionDoneTable;
    }

    public void setDisasterIdActionDoneTable(TableColumn<ActionsDone, Long> disasterIdActionDoneTable) {
        this.disasterIdActionDoneTable = disasterIdActionDoneTable;
    }

    public TableColumn<ActionsDone, String> getAuthorityActionDoneTable() {
        return authorityActionDoneTable;
    }

    public void setAuthorityActionDoneTable(TableColumn<ActionsDone, String> authorityActionDoneTable) {
        this.authorityActionDoneTable = authorityActionDoneTable;
    }

    public TableColumn<ActionsDone, String> getActionsDoneTable() {
        return actionsDoneTable;
    }

    public void setActionsDoneTable(TableColumn<ActionsDone, String> actionsDoneTable) {
        this.actionsDoneTable = actionsDoneTable;
    }

    public TableColumn<ActionsDone, String> getActionsReviewTable() {
        return actionsReviewTable;
    }

    public void setActionsReviewTable(TableColumn<ActionsDone, String> actionsReviewTable) {
        this.actionsReviewTable = actionsReviewTable;
    }

    public TableColumn<ActionsDone, String> getAdditionalActionsTable() {
        return additionalActionsTable;
    }

    public void setAdditionalActionsTable(TableColumn<ActionsDone, String> additionalActionsTable) {
        this.additionalActionsTable = additionalActionsTable;
    }

    public TableColumn<ActionsDone, LocalDateTime> getTimeStampingActionsTable() {
        return timeStampingActionsTable;
    }

    public void setTimeStampingActionsTable(TableColumn<ActionsDone, LocalDateTime> timeStampingActionsTable) {
        this.timeStampingActionsTable = timeStampingActionsTable;
    }

    public TableColumn<ActionsDone, String> getCreatedByActionsTable() {
        return createdByActionsTable;
    }

    public void setCreatedByActionsTable(TableColumn<ActionsDone, String> createdByActionsTable) {
        this.createdByActionsTable = createdByActionsTable;
    }

    public ComboBox<String> getActionDoneReviewCombobox() {
        return actionDoneReviewCombobox;
    }

    public void setActionDoneReviewCombobox(ComboBox<String> actionDoneReviewCombobox) {
        this.actionDoneReviewCombobox = actionDoneReviewCombobox;
    }

    public Label getActionDoneErrorLabel() {
        return actionDoneErrorLabel;
    }

    public void setActionDoneErrorLabel(Label actionDoneErrorLabel) {
        this.actionDoneErrorLabel = actionDoneErrorLabel;
    }

    public TextArea getActionDoneChangesTextArea() {
        return actionDoneChangesTextArea;
    }

    public void setActionDoneChangesTextArea(TextArea actionDoneChangesTextArea) {
        this.actionDoneChangesTextArea = actionDoneChangesTextArea;
    }

    public TextArea getPlanChangesTextArea() {
        return planChangesTextArea;
    }

    public void setPlanChangesTextArea(TextArea planChangesTextArea) {
        this.planChangesTextArea = planChangesTextArea;
    }
    
}
