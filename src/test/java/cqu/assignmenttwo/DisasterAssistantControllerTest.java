package cqu.assignmenttwo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisasterAssistantControllerTest {

    private DisasterAssistantController controller;
    private EntityManager mockEntityManager;
    private Query mockQuery;
    private ActionEvent mockEvent;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize the JavaFX Platform if not already running
        initializeJavaFX();

        // Initialize the controller
        controller = new DisasterAssistantController();

        // Mock the EntityManager
        mockEntityManager = Mockito.mock(EntityManager.class);

        // Mock the Query
        mockQuery = Mockito.mock(Query.class);

        // Set up the EntityManager in the controller using the setter
        controller.setEm(mockEntityManager);

        // Mock ActionEvent for testing
        mockEvent = Mockito.mock(ActionEvent.class);

        // Initialize Labels and other UI components
        Label mockNotificationErrorLabel = new Label();
        Label mockNotificationCreatedLabel = new Label();
        controller.setNotificationErrorLabel(mockNotificationErrorLabel);
        controller.setNotificationCreatedLabel(mockNotificationCreatedLabel);

        // Initialize ComboBox and TableView (as examples)
        ComboBox<Long> disasterSelectionComboBox = new ComboBox<>();
        controller.setDisasterSelectionCombobox(disasterSelectionComboBox);

        ComboBox<String> priorityLevelComboBox = new ComboBox<>();
        controller.setPriorityLevelCombobox(priorityLevelComboBox);

        ComboBox<ResponderAuthority> authorityRequiredComboBox = new ComboBox<>();
        controller.setAuthorityRequiredCombobox(authorityRequiredComboBox);

        TableView<DisasterEvent> disasterInformationTableView = new TableView<>();
        controller.setDisasterInformationTableView(disasterInformationTableView);
    }

    private void initializeJavaFX() throws InterruptedException {
        if (Platform.isFxApplicationThread()) {
            return; // JavaFX is already initialized
        }

        CountDownLatch latch = new CountDownLatch(1);
        new JFXPanel(); // Initializes JavaFX platform
        Platform.runLater(latch::countDown);
        latch.await(5, TimeUnit.SECONDS); // Wait for JavaFX initialization
    }

   @Test
public void testHandleDisasterSelection() throws Exception {
    // Set up necessary test data
    DisasterEvent disasterEvent = new DisasterEvent();
    disasterEvent.setId(1L);
    disasterEvent.setDisasterDate(LocalDate.now());
    disasterEvent.setTypeOfDisaster(TypeOfDisaster.FLOOD);

    controller.getDisasterSelectionCombobox().getItems().add(disasterEvent.getId());
    controller.setDisasterEvents(FXCollections.observableArrayList(disasterEvent)); // Make sure events are set

    // Simulate the user selecting the event
    controller.getDisasterSelectionCombobox().setValue(1L); // Simulate user selecting ID 1

    // Access the private method using reflection
    Method method = DisasterAssistantController.class.getDeclaredMethod("handleDisasterSelection", ActionEvent.class);
    method.setAccessible(true);  // Make the private method accessible

    // Invoke the method
    method.invoke(controller, mockEvent);

    // Assert that the selected event was set
    assertNotNull(controller.getSelectedEvent(), "Disaster event should be selected.");
    assertEquals(1L, controller.getSelectedEvent().getId(), "Selected disaster event ID should match.");
}

    @Test
    public void testHandlerPrioritySelection() throws Exception {
        ComboBox<String> priorityComboBox = controller.getPriorityLevelCombobox();
        priorityComboBox.getItems().add("High");
        priorityComboBox.setValue("High");

        // Access the private method using reflection
        Method method = DisasterAssistantController.class.getDeclaredMethod("handlerPrioritySelection", ActionEvent.class);
        method.setAccessible(true);  // Make the private method accessible

        // Invoke the method
        method.invoke(controller, mockEvent);

        // Verify the selected priority level
        assertEquals("High", controller.getSelectedPriorityLevel(), "Priority level should be High.");
    }

    @Test
    public void testHandlerAuthoritySelection() throws Exception {
        ComboBox<ResponderAuthority> authorityComboBox = controller.getAuthorityRequiredCombobox();
        authorityComboBox.getItems().add(ResponderAuthority.FIREFIGHTERS);
        authorityComboBox.setValue(ResponderAuthority.FIREFIGHTERS);

        // Access the private method using reflection
        Method method = DisasterAssistantController.class.getDeclaredMethod("handlerAuthoritySelection", ActionEvent.class);
        method.setAccessible(true);  // Make the private method accessible

        // Invoke the method
        method.invoke(controller, mockEvent);

        // Verify the selected authority
        assertEquals(ResponderAuthority.FIREFIGHTERS, controller.getSelectedAuthorityRequired(), "Authority should be Firefighters.");
    }

    @Test
public void testAlertButton_NoDuplicateNotification() throws Exception {
    // Set up test data
    DisasterEvent disasterEvent = new DisasterEvent();
    disasterEvent.setId(1L);
    disasterEvent.setDisasterDate(LocalDate.now());
    disasterEvent.setTypeOfDisaster(TypeOfDisaster.FLOOD); // Correct enum type
    controller.setSelectedEvent(disasterEvent);  // Use setter for selectedEvent
    controller.setSelectedPriorityLevel("High"); // Use setter for selectedPriorityLevel

    // Mock the query to return an empty list (no duplicate notifications)
    when(mockEntityManager.createNamedQuery("findRegisteredNotifications")).thenReturn(mockQuery);
    when(mockQuery.setParameter("disasterId", 1L)).thenReturn(mockQuery);
    when(mockQuery.getResultList()).thenReturn(new ArrayList<>()); // No existing alerts

    // Mock the transaction to ensure begin and commit
    EntityTransaction mockTransaction = Mockito.mock(EntityTransaction.class);
    when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

    // Access the private method using reflection
    Method method = DisasterAssistantController.class.getDeclaredMethod("alertButton", ActionEvent.class);
    method.setAccessible(true);  // Make the private method accessible

    // Invoke the method
    method.invoke(controller, mockEvent);

    // Verify transaction management
    verify(mockTransaction, times(1)).begin();
    verify(mockTransaction, times(1)).commit();

    // Verify that the notification is persisted (no duplicates exist)
    verify(mockEntityManager, times(1)).persist(any(NotificationAlert.class));

    // Verify that the success message is displayed using the getter
    assertEquals("The Notification Alert has been created",
            controller.getNotificationCreatedLabel().getText(),
            "Notification should be created.");
}


    @Test
    public void testAlertButton_DuplicateNotification() throws Exception {
        // Set up test data
        DisasterEvent disasterEvent = new DisasterEvent();
        disasterEvent.setId(1L);
        disasterEvent.setDisasterDate(LocalDate.now());
        disasterEvent.setReporterName("Test Reporter");
        disasterEvent.setDisasterLocation("Test Location");
        disasterEvent.setDisasterDescription("Test Description");
        disasterEvent.setTypeOfDisaster(TypeOfDisaster.FLOOD); // Correct enum type
        controller.setSelectedEvent(disasterEvent);  // Use setter for selectedEvent
        controller.setSelectedPriorityLevel("High"); // Use setter for selectedPriorityLevel

        // Mock the query to return a non-empty list (duplicate notifications exist)
        when(mockEntityManager.createNamedQuery("findRegisteredNotifications")).thenReturn(mockQuery);
        when(mockQuery.setParameter("disasterId", 1L)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(List.of(new NotificationAlert()));

        // Access the private method using reflection
        Method method = DisasterAssistantController.class.getDeclaredMethod("alertButton", ActionEvent.class);
        method.setAccessible(true);  // Make the private method accessible

        // Invoke the method
        method.invoke(controller, mockEvent);

        // Verify that the error message is shown (duplicate notification)
        assertEquals("A Notification Alert already exists for this disaster.",
                controller.getNotificationErrorLabel().getText(),
                "Error message should indicate duplicate notification.");
    }
}
