package cqu.assignmenttwo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author AndresPinilla 12243141
 * 
 * This class is to control the Primary.fxml. It has multiple key events and 
 * key actions methods to handle the user interaction.
 */
public class PrimaryController {

    /**
     * 
     * This section handles the register a disaster event button, it displays
     * the disaster registration screen.
     * 
     * @param event the user clicks the button. 
     */
    @FXML
    private void disasterRegistrationButton(ActionEvent event) {
        try {
            App.setRoot("DisasterRegistration");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }

    /**
     * This section handles the Log in button, it displays the log in screen.
     * 
     * @param event the user clicks the button. 
     */
    @FXML
    private void logInButton(ActionEvent event) {
        try {
            App.setRoot("Login");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }
    
    /**
     * This section handles the check notification alert button, it displays
     * the notifications screen.
     * 
     * @param event the user clicks the button. 
     */
    @FXML
    private void checkNotificationButton(ActionEvent event) {
        try {
            App.setRoot("Notifications");
        } catch (IOException e) {
            // Handle IOException if there is an issue loading the new screen
            e.printStackTrace();
        }
    }
}
