/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author AndresPinilla 12243141
 *
 * This class is to control the NotificationAlert.fxml. It has multiple key
 * events and key actions methods to handle the user interaction.
 *
 */
public class NotificationsController {

    // FXML tableview and tablecolumns. 
    @FXML
    private TableView<NotificationAlert> notificationTableView;
    @FXML
    private TableColumn<NotificationAlert, String> disasterIdTable;
    @FXML
    private TableColumn<NotificationAlert, String> dateTable;
    @FXML
    private TableColumn<NotificationAlert, String> typeTable;
    @FXML
    private TableColumn<NotificationAlert, String> locationTable;
    @FXML
    private TableColumn<NotificationAlert, String> descriptionTable;
    @FXML
    private TableColumn<NotificationAlert, String> priorityTable;

    /**
     * This section is to handle the main menu button, it displays the primary
     * screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void mainMenuController(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to initialize the Tableview, Tablecolumns and to set
     * arial font so it can be compatible with mac OS system.
     */
    @FXML
    private void initialize() {
        // Sets the font style.
        notificationTableView.setStyle("-fx-font-family: 'Arial'");

        // Initialize actionPlanTableView columns
        disasterIdTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterId"));
        dateTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterDate"));
        typeTable.setCellValueFactory(
                new PropertyValueFactory<>("typeOfDisaster"));
        locationTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterLocation"));
        descriptionTable.setCellValueFactory(
                new PropertyValueFactory<>("disasterDescription"));
        priorityTable.setCellValueFactory(
                new PropertyValueFactory<>("levelOfPriority"));

        // Load the notification data from the DB
        EntityManagerUtils emu = new EntityManagerUtils();
        EntityManager em = emu.getEm();
        Query query = em.createNamedQuery("getAllNotifications");
        List<NotificationAlert> notifications = query.getResultList();

        // Convert the list to an ObservableList
        ObservableList<NotificationAlert> notificationList = FXCollections.observableArrayList(notifications);

        // Set the items for the TableView
        notificationTableView.setItems(notificationList);
    }
}
