package cqu.assignmenttwo;
import static com.google.protobuf.JavaFeaturesProto.java;
import static org.junit.jupiter.api.Assertions.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.time.LocalDate;

public class DisasterRegistrationControllerTest {

    private TextField reporterNameController;
    private TextField reporterMobileController;
    private TextField reporterAddressController;
    private TextField disasterLocationController;
    private TextArea disasterDescriptionTextArea;
    private DatePicker disasterDateController;
    private ComboBox<TypeOfDisaster> typeOfDisasterCombobox;
    private Label nameErrorLabel;
    private Label mobileErrorLabel;
    private Label incompleteFieldsLabel;

    @InjectMocks
    private DisasterRegistrationController controller;

    @BeforeEach
    public void setup() {
        // Initializes the JavaFX toolkit (necessary for JavaFX components)
        new JFXPanel();

        // Initialize JavaFX components as real objects
        reporterNameController = new TextField();
        reporterMobileController = new TextField();
        reporterAddressController = new TextField();
        disasterLocationController = new TextField();
        disasterDescriptionTextArea = new TextArea();
        disasterDateController = new DatePicker();
        typeOfDisasterCombobox = new ComboBox<>();
        nameErrorLabel = new Label();
        mobileErrorLabel = new Label();
        incompleteFieldsLabel = new Label();

        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Inject real controls into the controller using setter methods
        controller.setReporterNameController(reporterNameController);
        controller.setReporterMobileController(reporterMobileController);
        controller.setReporterAddressController(reporterAddressController);
        controller.setDisasterLocationController(disasterLocationController);
        controller.setDisasterDescriptionTextArea(disasterDescriptionTextArea);
        controller.setDisasterDateController(disasterDateController);
        controller.setTypeOfDisasterCombobox(typeOfDisasterCombobox);
        controller.setNameErrorLabel(nameErrorLabel);
        controller.setMobileErrorLabel(mobileErrorLabel);
        controller.setIncompleteFieldsLabel(incompleteFieldsLabel);
    }

    

    @Test
    public void testRegisterDisasterController_InvalidName() throws Exception {
        // Set invalid input for the name
        reporterNameController.setText("12345");
        reporterMobileController.setText("123456789");
        reporterAddressController.setText("123 Main St");
        disasterLocationController.setText("City Center");
        disasterDescriptionTextArea.setText("A major fire incident.");
        disasterDateController.setValue(LocalDate.now());
        typeOfDisasterCombobox.setValue(TypeOfDisaster.FIRE);

        // Use reflection to access the private method
        Method method = DisasterRegistrationController.class.getDeclaredMethod("registerDisasterController", javafx.event.ActionEvent.class);
        method.setAccessible(true); // Make the private method accessible

        // Invoke the private method using reflection
        method.invoke(controller, (javafx.event.ActionEvent) null);

        // Verify that the name error label is visible
        assertTrue(nameErrorLabel.isVisible());
        assertEquals("Invalid input. \nReport Name must contain only alphabets.", nameErrorLabel.getText());
    }

    @Test
    public void testRegisterDisasterController_InvalidMobile() throws Exception {
        // Set invalid input for the mobile number
        reporterNameController.setText("John Doe");
        reporterMobileController.setText("abc123");
        reporterAddressController.setText("123 Main St");
        disasterLocationController.setText("City Center");
        disasterDescriptionTextArea.setText("A major fire incident.");
        disasterDateController.setValue(LocalDate.now());
        typeOfDisasterCombobox.setValue(TypeOfDisaster.FIRE);

        // Use reflection to access the private method
        Method method = DisasterRegistrationController.class.getDeclaredMethod("registerDisasterController", javafx.event.ActionEvent.class);
        method.setAccessible(true); // Make the private method accessible

        // Invoke the private method using reflection
        method.invoke(controller, (javafx.event.ActionEvent) null);

        // Verify that the mobile error label is visible
        assertTrue(mobileErrorLabel.isVisible());
        assertEquals("Invalid input. \nReporter Mobile must contain only numbers.", mobileErrorLabel.getText());
    }

    @Test
    public void testRegisterDisasterController_EmptyFields() throws Exception {
        // Set up empty input values
        reporterNameController.setText("");
        reporterMobileController.setText("");
        reporterAddressController.setText("");
        disasterLocationController.setText("");
        disasterDescriptionTextArea.setText("");
        typeOfDisasterCombobox.setValue(null);
        disasterDateController.setValue(null);

        // Use reflection to access the private method
        Method method = DisasterRegistrationController.class.getDeclaredMethod("registerDisasterController", javafx.event.ActionEvent.class);
        method.setAccessible(true); // Make the private method accessible

        // Invoke the private method using reflection
        method.invoke(controller, (javafx.event.ActionEvent) null);

        // Verify that the incomplete fields label is visible
        assertTrue(incompleteFieldsLabel.isVisible());
        assertEquals("Disaster Event Registration failed: \nPlease make sure none of the fields are empty.", incompleteFieldsLabel.getText());
    }
}
