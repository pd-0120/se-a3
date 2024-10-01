/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.assignmenttwo;

import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author AndresPinilla 12243141
 *
 * This class is to control the DisasterRegistration.fxml. It has multiple data
 * types to store the user's input and key events and key actions methods to
 * handle the user interaction.
 */
public class DisasterRegistrationController {

    // Stores all the disaster events.
    private List<DisasterEvent> disasterList = new LinkedList<>();

    // FXML buttons, labels, combobox text areas and text fields.
    @FXML
    private Button mainMenuController;
    @FXML
    private Button registerDisasterController;
    @FXML
    private TextField reporterNameController;
    @FXML
    private TextField reporterAddressController;
    @FXML
    private TextField disasterLocationController;
    @FXML
    private TextField disasterAutoId;
    @FXML
    private Label incompleteFieldsLabel;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label mobileErrorLabel;

    @FXML
    private DatePicker disasterDateController;
    @FXML
    private ComboBox<TypeOfDisaster> typeOfDisasterCombobox;
    @FXML
    private TextField reporterMobileController;
    @FXML
    private TextArea disasterDescriptionTextArea;
    @FXML
    private TextField disasterIdController;

    /**
     * This section is to initialize the combo box, the disaster date and to
     * generate the disaster Id.
     */
    @FXML
    private void initialize() {

        // Initializes the combobox with the type of disaster options.
        if (typeOfDisasterCombobox != null) {
            ObservableList<TypeOfDisaster> disasters
                    = FXCollections.observableArrayList(TypeOfDisaster.values());
            typeOfDisasterCombobox.setItems(disasters);
            // Sets the font style.
            typeOfDisasterCombobox.setStyle("-fx-font-family: 'Arial'");
        }

        // Initiliaze the date with the local date by default.
        if (disasterDateController != null) {
            disasterDateController.setValue(LocalDate.now());
            // Sets the font style.
            disasterDateController.setStyle("-fx-font-family: 'Arial'");
        }
        // Generates the disaster event id.
        String autogeneratedId = generateAutoId();
        disasterIdController.setText(autogeneratedId);
    }

    // FXML action controllers
    @FXML
    private void reporterNameController(KeyEvent event) {
    }

    @FXML
    private void reporterAddressController(KeyEvent event) {
    }

    @FXML
    private void disasterLocationController(KeyEvent event) {
    }

    @FXML
    private void reporterMobileController(KeyEvent event) {
    }

    @FXML
    private void disasterDescriptionTextArea(KeyEvent event) {
    }

    /**
     * This section is to control the main menu button, if clicked the primary
     * screen will be display.
     *
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
     * This section is to control the Register button, if clicked it will check
     * if the reporter name contains only alphabets, if the reporter mobile
     * contains only numbers, and if all the fields are filled. If there are no
     * errors the registration is completed and the primary screen will be
     * displayed.
     *
     */
    @FXML
    private void registerDisasterController(ActionEvent event) {
        // Check if there are only alphabets
        if (nameIsWrong()) {
            // Display the error message using a label
            showNameErrorMessage("Invalid input. \nReport Name must "
                    + "contain only alphabets.");
        } else {
            // Hide the error message if the input becomes valid
            nameErrorLabel.setVisible(false);
        }
        // check if reporter mobile contains only numbers
        if (mobileIsWrong()) {
            // Display the error message using a label
            showMobileErrorMessage("Invalid input. \nReporter Mobile must "
                    + "contain only numbers.");
        } else {
            // Hide the error message if the input is valid
            mobileErrorLabel.setVisible(false);
        }
        // Check if any of the specified fields is empty
        if (areRegistrationFieldsEmpty()) {
            // Display the error message using a label
            showRegistrationErrorMessage("Disaster Event Registration failed: "
                    + "\nPlease make sure none of the fields are empty.");
        } else {
            // Hide the error message if the input is valid
            incompleteFieldsLabel.setVisible(false);

            if (!areRegistrationFieldsEmpty() && !nameIsWrong()
                    && !mobileIsWrong()) {

                // Store the information in the Resident object
                DisasterEvent disasterEvent = new DisasterEvent(
                        reporterNameController.getText(),
                        Integer.parseInt(reporterMobileController.getText()),
                        reporterAddressController.getText(),
                        disasterDateController.getValue(),
                        typeOfDisasterCombobox.getValue(),
                        disasterLocationController.getText(),
                        disasterDescriptionTextArea.getText()
                );

                // Store data in database
                EntityManagerUtils emu = new EntityManagerUtils();
                EntityManager em = emu.getEm();
                
                em.getTransaction().begin();
                em.persist(disasterEvent);
                em.getTransaction().commit();

                try {
                    App.setRoot("Primary");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This section is to check if reporter name contains only alphabets.
     *
     */
    private boolean nameIsWrong() {
        // Add checks for each specified field
        return !reporterNameController.getText().matches("^[a-zA-Z\\s]+$");
    }

    /**
     * This section is to check the age, number of years and contact phone
     * contains only numbers.
     *
     */
    private boolean mobileIsWrong() {
        // Add checks for each specified field
        return !reporterMobileController.getText().matches("^[0-9]+$");
    }

    /**
     * This section is to check if any of the fields are empty.
     *
     */
    private boolean areRegistrationFieldsEmpty() {
        // Add checks for each specified field
        return reporterNameController.getText().isEmpty()
                || reporterMobileController.getText().isEmpty()
                || reporterAddressController.getText().isEmpty()
                || disasterIdController.getText().isEmpty()
                || typeOfDisasterCombobox.getValue() == null
                || disasterLocationController.getText().isEmpty()
                || disasterDescriptionTextArea.getText().isEmpty();
    }

    /**
     * This method is to set the error message of nameErrorLabel and to make it
     * visible.
     *
     * @param message the message is set in registerDisasterController.
     */
    private void showNameErrorMessage(String message) {
        nameErrorLabel.setText(message);
        nameErrorLabel.setVisible(true);
    }

    /**
     * This method is to set the error message of mobileErrorLabel and to make
     * it visible.
     *
     * @param message the message is set in registerDisasterController.
     */
    private void showMobileErrorMessage(String message) {
        mobileErrorLabel.setText(message);
        mobileErrorLabel.setVisible(true);
    }

    /**
     * This method is to set the error message of showRegistrationErrorMessage
     * and to make it visible.
     *
     * @param message the message is set in registerDisasterController.
     */
    private void showRegistrationErrorMessage(String message) {
        incompleteFieldsLabel.setText(message);
        incompleteFieldsLabel.setVisible(true);
    }

    /**
     * This section generates an auto generated ID using a prefix and a
     * sequential number. It checks if the disaster id exists in the
     * DisasterEvents.csv to prevent duplicities.
     */
    // Counter for generating sequential IDs
    private static int disasterCounter = 1;

    private String generateAutoId() {
        String prefix = "DISASTER";
        String newId;

        // Load existing disaster events from the CSV file
        List<DisasterEvent> existingEvents
                = FileUtility.loadDataFromCsv("DisasterEvents.csv");

        while (true) {
            // Generate a simple sequential number
            String sequentialPart = String.format("%03d", disasterCounter);
            newId = prefix + sequentialPart;

            // Create a final variable for use in the lambda expression
            final String idToCheck = newId;

            // Check if the generated ID already exists in the list of 
            // disaster events.
            boolean idExists = existingEvents.stream()
                    .anyMatch(event -> event.getDisasterId().equals(idToCheck));

            if (!idExists) {
                break;  // If the ID doesn't exist, exit the loop
            }

            // Increment the counter for the next ID
            disasterCounter++;
        }

        return newId;
    }

}
