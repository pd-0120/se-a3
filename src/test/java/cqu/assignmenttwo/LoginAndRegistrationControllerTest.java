package cqu.assignmenttwo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import javafx.application.Platform;
import javafx.scene.control.*;
import org.junit.jupiter.api.*;
import java.lang.reflect.Method;
import java.util.List;
import javafx.event.ActionEvent;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import javafx.scene.Parent;
import org.mockito.MockedStatic;

public class LoginAndRegistrationControllerTest {

    private LoginAndRegistrationController controller;
    private PasswordField staffPassword;
    private TextField staffEmail;
    private Label staffNotRegisteredLabel;
    private ComboBox<Role> roleComboboxRegistration;
    private TextField staffNameRegistration;
    private TextField staffEmailRegistration;
    private PasswordField staffPasswordRegistration;
    private Label nameWrongStaffRegistrationLabel;
    private Label emptyFieldsStaffRegistrationLabel;

    // Mocks for JPA related operations
    private EntityManager em;
    private EntityTransaction transaction;

    @BeforeAll
    public static void initJavaFX() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);  // Initializes JavaFX Platform
        latch.await();
    }

    @BeforeEach
    public void setUp() {
        // Instantiate controller and JavaFX components
        controller = new LoginAndRegistrationController();

        // Initialize actual JavaFX components for the test
        staffPassword = new PasswordField();
        staffEmail = new TextField();
        staffNotRegisteredLabel = new Label();
        roleComboboxRegistration = new ComboBox<>();
        staffNameRegistration = new TextField();
        staffEmailRegistration = new TextField();
        staffPasswordRegistration = new PasswordField();
        nameWrongStaffRegistrationLabel = new Label();
        emptyFieldsStaffRegistrationLabel = new Label();

        // Inject the real components into the controller
        setPrivateField(controller, "staffPassword", staffPassword);
        setPrivateField(controller, "staffEmail", staffEmail);
        setPrivateField(controller, "staffNotRegisteredLabel", staffNotRegisteredLabel);
        setPrivateField(controller, "roleComboboxRegistration", roleComboboxRegistration);
        setPrivateField(controller, "staffNameRegistration", staffNameRegistration);
        setPrivateField(controller, "staffEmailRegistration", staffEmailRegistration);
        setPrivateField(controller, "staffPasswordRegistration", staffPasswordRegistration);
        setPrivateField(controller, "nameWrongStaffRegistrationLabel", nameWrongStaffRegistrationLabel);
        setPrivateField(controller, "emptyFieldsStaffRegistrationLabel", emptyFieldsStaffRegistrationLabel);

        // Mock EntityManager and EntityTransaction
        em = mock(EntityManager.class);
        transaction = mock(EntityTransaction.class);

        // Setup EntityManager to return mock transaction
        when(em.getTransaction()).thenReturn(transaction);
    }

    // Utility method to set private fields using reflection
    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to invoke private methods using reflection
    private Object invokePrivateMethod(String methodName, Object... args) throws Exception {
        Method method = null;
        for (Method m : controller.getClass().getDeclaredMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        if (method != null) {
            method.setAccessible(true);
            return method.invoke(controller, args);
        } else {
            throw new NoSuchMethodException("Method " + methodName + " not found");
        }
    }

    @Test
    public void testLoginButton() throws Exception {
        // Arrange: Set values for email and password
        staffEmail.setText("test@example.com");
        staffPassword.setText("password");

        // Mock the EntityManager and Query objects
        Query query = mock(Query.class);
        List<Staff> mockStaffList = new LinkedList<>();
        Staff mockStaff = new Staff(Role.DISASTER_EVENT_ASSISTANT, "John Doe", "test@example.com", "hashed_password", new byte[0]);
        mockStaffList.add(mockStaff);

        // Setup the mock behavior
        when(em.createNamedQuery("findByEmailId")).thenReturn(query);
        when(query.setParameter("emailAddress", "test@example.com")).thenReturn(query);
        when(query.getResultList()).thenReturn(mockStaffList);

        // Inject the mock EntityManager into the controller
        setPrivateField(controller, "em", em);

        // Act: Invoke the loginButton method
        invokePrivateMethod("loginButton", mock(ActionEvent.class));

        
    }

  @Test
public void testRegisterStaffButton() throws Exception {
    // Arrange: Set valid values for registration
    staffNameRegistration.setText("John Doe");
    staffEmailRegistration.setText("johndoe@example.com");
    staffPasswordRegistration.setText("password");
    roleComboboxRegistration.setValue(Role.DISASTER_EVENT_ASSISTANT);

    // Mock the EntityManager and Query objects
    Query query = mock(Query.class);
    when(em.createNamedQuery("findByEmailId")).thenReturn(query);
    when(query.setParameter("emailAddress", "johndoe@example.com")).thenReturn(query);
    when(query.getResultList()).thenReturn(new LinkedList<>());

    // Inject the mock EntityManager into the controller
    setPrivateField(controller, "em", em);

    // Mock the static method setRoot() of the App class
    try (MockedStatic<App> mockedApp = mockStatic(App.class)) {
        // Simply mock the static setRoot method without worrying about the argument
        mockedApp.when(() -> App.setRoot(any())).thenAnswer(invocation -> null);

        // Act: Invoke the registerStaffButton method
        invokePrivateMethod("registerStaffButton", mock(ActionEvent.class));

        // Verify that the transaction was started and committed
        verify(transaction).begin();
        verify(transaction).commit();
    }

    // Assert: Check that the registration is successful (e.g., no error messages)
    assertFalse(nameWrongStaffRegistrationLabel.isVisible());
    assertFalse(emptyFieldsStaffRegistrationLabel.isVisible());
}

    @Test
    public void testPrivateHelperMethods() throws Exception {
        // Test staffNameIsWrong method
        staffNameRegistration.setText("John123");
        assertTrue((boolean) invokePrivateMethod("staffNameIsWrong"));

        // Test staffRegistrationFieldsEmpty method
        staffNameRegistration.setText("");
        assertTrue((boolean) invokePrivateMethod("staffRegistrationFieldsEmpty"));
    }
}
