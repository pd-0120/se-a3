package cqu.assignmenttwo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

class DisasterManagerControllerTest {

    private DisasterManagerController controller;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize JavaFX toolkit if necessary
        if (!Platform.isFxApplicationThread()) {
            try {
                final CountDownLatch latch = new CountDownLatch(1);
                Platform.startup(() -> {});
                latch.await(1, TimeUnit.SECONDS);
            } catch (IllegalStateException e) {
                // Ignore the exception if the toolkit is already initialized
            }
        }

        controller = new DisasterManagerController();

        // Mock the ComboBoxes and their getItems() method
        ComboBox<Long> mockPlanSelectionCombobox = Mockito.mock(ComboBox.class);
        when(mockPlanSelectionCombobox.getItems()).thenReturn(FXCollections.observableArrayList(1L, 2L, 3L));

        ComboBox<String> mockPlanReviewCombobox = Mockito.mock(ComboBox.class);
        when(mockPlanReviewCombobox.getItems()).thenReturn(FXCollections.observableArrayList("Approve", "Reject"));

        ComboBox<Long> mockActionDoneSelectionCombobox = Mockito.mock(ComboBox.class);
        when(mockActionDoneSelectionCombobox.getItems()).thenReturn(FXCollections.observableArrayList(1L, 2L, 3L));

        ComboBox<String> mockActionDoneReviewCombobox = Mockito.mock(ComboBox.class);
        when(mockActionDoneReviewCombobox.getItems()).thenReturn(FXCollections.observableArrayList("Complete", "Incomplete"));

        // Mock other components
        TableView<?> mockActionPlanTableView = Mockito.mock(TableView.class);
        TableView<?> mockActionDoneTableView = Mockito.mock(TableView.class);
        TextArea mockPlanChangesTextArea = Mockito.mock(TextArea.class);
        TextArea mockActionDoneChangesTextArea = Mockito.mock(TextArea.class);
        Label mockActionDoneErrorLabel = Mockito.mock(Label.class);
        Label mockPlanErrorLabel = Mockito.mock(Label.class);

        // Mock the TableColumns
        TableColumn<?, Long> mockDisasterIdTable = Mockito.mock(TableColumn.class);
        TableColumn<?, String> mockDisasterNameTable = Mockito.mock(TableColumn.class);

        // Set the mocked fields in the controller using reflection
        setPrivateField(controller, "planSelectionCombobox", mockPlanSelectionCombobox);
        setPrivateField(controller, "planReviewCombobox", mockPlanReviewCombobox);
        setPrivateField(controller, "actionDoneSelectionCombobox", mockActionDoneSelectionCombobox);
        setPrivateField(controller, "actionDoneReviewCombobox", mockActionDoneReviewCombobox);
        setPrivateField(controller, "actionPlanTableView", mockActionPlanTableView);
        setPrivateField(controller, "actionDoneTableView", mockActionDoneTableView);
        setPrivateField(controller, "planChangesTextArea", mockPlanChangesTextArea);
        setPrivateField(controller, "actionDoneChangesTextArea", mockActionDoneChangesTextArea);
        setPrivateField(controller, "actionDoneErrorLabel", mockActionDoneErrorLabel);
        setPrivateField(controller, "planErrorLabel", mockPlanErrorLabel);
        setPrivateField(controller, "disasterIdTable", mockDisasterIdTable);
      

        // Mock behavior for TableColumn setCellValueFactory
        when(mockDisasterIdTable.getCellValueFactory()).thenReturn(new PropertyValueFactory<>("id"));
        when(mockDisasterNameTable.getCellValueFactory()).thenReturn(new PropertyValueFactory<>("name"));
    }

    
    @Test
    void testCompletePlanReviewButton() throws Exception {
        // Mock the ComboBox and TextArea behavior for completing a plan review
        ComboBox<String> planReviewCombobox = (ComboBox<String>) getPrivateField(controller, "planReviewCombobox");
        when(planReviewCombobox.getValue()).thenReturn("Approve");
        TextArea planChangesTextArea = (TextArea) getPrivateField(controller, "planChangesTextArea");
        when(planChangesTextArea.getText()).thenReturn("Some changes required");

        // Invoke completePlanReviewButton method
        Method completePlanReviewButtonMethod = controller.getClass().getDeclaredMethod("completePlanReviewButton", javafx.event.ActionEvent.class);
        completePlanReviewButtonMethod.setAccessible(true);
        completePlanReviewButtonMethod.invoke(controller, new javafx.event.ActionEvent());

        // Add assertions to verify that the plan review completion works as expected
        // (Optional assertions based on your project needs)
    }

    // Helper methods for reflection to access private fields and methods
    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
