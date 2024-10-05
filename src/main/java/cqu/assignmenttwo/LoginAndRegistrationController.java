package cqu.assignmenttwo;

import static cqu.assignmenttwo.Role.DISASTER_EVENT_ASSISTANT;
import static cqu.assignmenttwo.Role.DISASTER_EVENT_MANAGER;
import static cqu.assignmenttwo.Role.EMERGENCY_RESPONDER;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author AndresPinilla 12243141
 *
 * This class is to control the DisasterAssistantMenu.fxml,
 * DisasterManagerMenu.fxml, EmergencyResponderMenu.fxml, and the Login.fxml. It
 * has multiple a List to store the user's input and key events and key actions
 * methods to handle the user interaction.
 */
public class LoginAndRegistrationController {

    // Stores all the staff.
    private List<Staff> staffList = new LinkedList<>();

    // FXML buttons, labels, combobox text areas and text fields from Login screen.
    @FXML
    private PasswordField staffPassword;
    @FXML
    private TextField staffEmail;
    @FXML
    private Label staffNotRegisteredLabel;
    
    
    // Declare entity manager in class level to avoid redundancy 
    EntityManagerUtils emu = new EntityManagerUtils();
    EntityManager em = emu.getEm();

    /**
     * Initializes the comboboxes in the login and the staff registration
     * screen. Initializes the staffIdRegistration in the staff registration
     * screen.
     *
     */
    @FXML
    private void initialize() {
        initializeStaffRegistrationScreen();
    }

   

    /**
     * This method is to initialize the roleComboboxRegistration with the
     * options of role enum.
     */
    private void initializeStaffRegistrationScreen() {
        if (roleComboboxRegistration != null) {
            ObservableList<Role> roleOptions
                    = FXCollections.observableArrayList(Role.values());
            roleComboboxRegistration.setItems(roleOptions);

            // Sets the font style.
            roleComboboxRegistration.setStyle("-fx-font-family: 'Arial'");
        }
    }

    // Event Controllers from Login screen.
    @FXML
    private void staffEmail(KeyEvent event) {
    }

    @FXML
    private void staffPassword(KeyEvent event) {
    }

    /**
     * This section is to control the signUp button in the login screen. If the
     * user click the button the staff registration screen is displayed.
     *
     * @param event the user clicks the signUp button.
     */
    @FXML
    private void signUpStaffButton(ActionEvent event) {
        try {
            App.setRoot("StaffRegistration");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen.
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the back button in the login screen. If the
     * user click the button the primary screen is displayed.
     *
     * @param event the user clicks the signUp button.
     */
    @FXML
    private void backButton(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen.
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the login button in the Login screen, if the
     * email is registered and the password is correct the program will display
     * the next screen based on the role selected. If the email is not
     * registered or the password is incorrect the program will make a label
     * visible to display the error message.
     */
    @FXML
    private void loginButton(ActionEvent event) throws NoSuchAlgorithmException {
        try {
            // Get the input values from the TextFields
            String emailAddress = staffEmail.getText();
            String password = staffPassword.getText();

            // Check if the email exists in the database
            Query query = em.createNamedQuery("findByEmailId");
            query.setParameter("emailAddress", emailAddress);

            List<Staff> staffList = query.getResultList();

            if (!staffList.isEmpty()) {
                Staff loggedInStaff = staffList.get(0);

                // Retrieve the stored hashed password and salt from the database
                String storedPasswordHash = loggedInStaff.getPassword();
                byte[] storedSalt = loggedInStaff.getSalt();

                // Hash the entered password with the stored salt
                String hashedInputPassword = PasswordEncryptionUtil.hashPassword(password, storedSalt);

                // Compare the hashed input password with the stored hash
                if (hashedInputPassword.equals(storedPasswordHash)) {
                    // Password is correct, proceed with login

                    // Get the role of the staff
                    Role staffRole = loggedInStaff.getRole();

                    // Store the logged-in user in the SessionManager
                    SessionManager.getInstance().setLoggedInUser(loggedInStaff);

                    // Display the appropriate screen based on the role
                    switch (staffRole) {
                        case DISASTER_EVENT_ASSISTANT:
                            App.setRoot("DisasterAssistant");
                            break;
                        case DISASTER_EVENT_MANAGER:
                            App.setRoot("DisasterManager");
                            break;
                        case EMERGENCY_RESPONDER:
                            App.setRoot("EmergencyResponder");
                            break;
                    }
                } else {
                    // Display "Login Failed" message if the password is incorrect
                    showErrorMessage("Login Failed: The password is incorrect.");
                }

            } else {
                // Display "Login Failed" message if the email is not found
                showErrorMessage("Login Failed: No account found with the given email.");
            }
        } catch (IOException e) {
            // Handle IOException if there is an issue
            e.printStackTrace();
        }
    }

    /**
     * This section is to check if the email and password are registered. It
     * reads the information from the Staff.csv file and validates if the user
     * is registered. It also handles the exception to load the fxml file and
     * the array out bonds.
     */
    private boolean isRegistered(String emailAddress, String password) {
        try {

            Query query = em.createNamedQuery("findByEmailIdAndPassword");
            query.setParameter("emailAddress", emailAddress);
            query.setParameter("password", password);
            query.setMaxResults(1);
            List<Staff> staff =  query.getResultList();
            return !staff.isEmpty();
            
        } catch (Exception e) {
            e.printStackTrace();
            // Handle IO exception, return false (credentials don't exist)
        }
        // Return false if credentials don't exist or an error occurred
        return false;
    }

    /**
     * This section is to make the error label visible and to set the message in
     * the Login screen.
     *
     */
    private void showErrorMessage(String message) {

        staffNotRegisteredLabel.setText(message);
        staffNotRegisteredLabel.setVisible(true);
    }

    // FXML buttons, labels, combobox text areas and text fields from 
    // StaffRegistration screen.
    @FXML
    private ComboBox<Role> roleComboboxRegistration;
    @FXML
    private TextField staffNameRegistration;
    @FXML
    private TextField staffEmailRegistration;
    @FXML
    private PasswordField staffPasswordRegistration;
    @FXML
    private Label nameWrongStaffRegistrationLabel;
    @FXML
    private Label emptyFieldsStaffRegistrationLabel;

    // Event Controllers from StaffRegistration screen.
    @FXML
    private void staffNameRegistration(KeyEvent event) {
    }

    @FXML
    private void staffEmailRegistration(KeyEvent event) {
    }

    @FXML
    private void staffPasswordRegistration(KeyEvent event) {
    }

    /**
     * This section is to control the back button in the staff registration
     * screen. If the user click the button the primary screen is displayed.
     *
     * @param event the user clicks the signUp button.
     */
    @FXML
    private void registrationBackButtonController(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to handle the register staff button. It validates if the
     * user provided a name in the right format and all the fields are filled,
     * if that so it proceed to create a new staff object.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void registerStaffButton(ActionEvent event) {

        // Check if there are only alphabets.
        if (staffNameIsWrong()) {
            // Display the error message using a label
            showStaffNameErrorMessage("Invalid input. \nStaff Name must contain "
                    + "only alphabets.");
        } else {
            // Hide the error message if the input becomes valid.
            nameWrongStaffRegistrationLabel.setVisible(false);
        }

        // Check if any of the specified fields is empty.
        if (staffRegistrationFieldsEmpty()) {
            // Display the error message using a label
            showStaffRegistrationErrorMessage("Staff Registration failed: "
                    + "\nPlease make sure none of the fields are empty.");
        } else if (isUserExists(staffEmailRegistration.getText())) {
            emptyFieldsStaffRegistrationLabel.setText("User with same email id is exists. Please try different.");
            emptyFieldsStaffRegistrationLabel.setVisible(true);
        } else {
            // Hide the error message if the input is valid.
            emptyFieldsStaffRegistrationLabel.setVisible(false);

            if (!staffRegistrationFieldsEmpty() && !staffNameIsWrong()) {
                try {
                    // Generate salt and hash the password
                    byte[] salt = PasswordEncryptionUtil.generateSalt();
                    String hashedPassword
                            = PasswordEncryptionUtil.generateHash(staffPasswordRegistration.getText(), salt);

                    // Create and persist the Staff object with hashed password and salt
                    Staff staff = new Staff(
                            roleComboboxRegistration.getValue(),
                            staffNameRegistration.getText(),
                            staffEmailRegistration.getText(),
                            hashedPassword,
                            salt // Ensure you store the salt in the Staff entity
                    );

                    em.getTransaction().begin();
                    em.persist(staff);
                    em.getTransaction().commit();

                    // Redirect based on the role selected
                    switch (roleComboboxRegistration.getValue()) {
                        case DISASTER_EVENT_ASSISTANT:
                            App.setRoot("DisasterAssistant");
                            break;
                        case DISASTER_EVENT_MANAGER:
                            App.setRoot("DisasterManager");
                            break;
                        case EMERGENCY_RESPONDER:
                            App.setRoot("EmergencyResponder");
                            break;
                    }
                } catch (NoSuchAlgorithmException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * This section is to check if reporter name contains only alphabets.
     *
     */
    private boolean staffNameIsWrong() {
        // Add checks for each specified field
        return !staffNameRegistration.getText().matches("^[a-zA-Z\\s]+$");
    }

    /**
     * This section is to check if any of the fields are empty.
     *
     */
    private boolean staffRegistrationFieldsEmpty() {
        // Add checks for each specified field
        return roleComboboxRegistration.getValue() == null
                || staffNameRegistration.getText().isEmpty()
                || staffEmailRegistration.getText().isEmpty()
                || staffPasswordRegistration.getText().isEmpty();
    }

    private void showStaffNameErrorMessage(String message) {
        nameWrongStaffRegistrationLabel.setText(message);
        nameWrongStaffRegistrationLabel.setVisible(true);
    }

    private boolean isUserExists(String email) {
        
        Query query = em.createNamedQuery("findByEmailId");
        query.setParameter("emailAddress", email);
        List<Staff> staffList = query.getResultList();

        return !staffList.isEmpty();

    }

    private void showStaffRegistrationErrorMessage(String message) {
        emptyFieldsStaffRegistrationLabel.setText(message);
        emptyFieldsStaffRegistrationLabel.setVisible(true);
    }

    /**
     * This section is to control the prioritize button in the disaster
     * assistant menu screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void prioritizeButton(ActionEvent event) {
        try {
            App.setRoot("DisasterAssistant");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the Go back to the main menu button in the
     * disaster assistant menu screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void mainMenuButton(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the prioritize button in the emergency
     * responder menu screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void actionDoneButton(ActionEvent event) {
        try {
            App.setRoot("EmergencyResponder");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the Go back to the main menu button in the
     * emergency responder menu screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void mainMenuActionDoneButton(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the review a new report button in the disaster
     * manager menu screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void reviewNewReportButton(ActionEvent event) {
        try {
            App.setRoot("DisasterManager");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section is to control the Go back to the main menu button in the
     * emergency responder menu screen.
     *
     * @param event the user clicks the button.
     */
    @FXML
    private void mainMenuActionManagerButton(ActionEvent event) {
        try {
            App.setRoot("Primary");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }
}
