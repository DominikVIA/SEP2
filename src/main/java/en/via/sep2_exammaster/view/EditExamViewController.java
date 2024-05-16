package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CreateExamViewModel;
import en.via.sep2_exammaster.viewmodel.EditExamViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;

public class EditExamViewController implements PropertyChangeListener {
  @FXML public TextField titleField;
  @FXML public TextField roomField;
  @FXML public TextArea contentArea;
  @FXML public DatePicker datePicker;
  @FXML public TextField timeField;
  @FXML public TextField studentField;
  @FXML public ListView<Student> studentsList;
  @FXML public ChoiceBox<String> examinerBox;
  @FXML public ChoiceBox<String> typeBox;
  @FXML public Button addButton;
  @FXML public Button removeButton;

  private ViewHandler viewHandler;
  private EditExamViewModel viewModel;
  private Region root;

  @FXML void onAdd() {
    viewModel.addStudent();
  }

  @FXML void onCancel() {
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  @FXML void onClick() {
    removeButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      removeButton.setDisable(false);
  }

  @FXML void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
  }

  @FXML void onSave() throws IOException {
    viewModel.onSave();
  }

  public void init(ViewHandler viewHandler, EditExamViewModel editExamViewModel, Region root){
    this.viewHandler = viewHandler;
    this.viewModel = editExamViewModel;
    this.root = root;

    typeBox.getItems().add("Written");
    typeBox.getItems().add("Oral");
    typeBox.getSelectionModel().selectFirst();

    examinerBox.getItems().add("Internal");
    examinerBox.getItems().add("External");
    examinerBox.getItems().add("Both");
    examinerBox.getSelectionModel().selectFirst();

    datePicker.setDayCellFactory(picker -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();
        setDisable(empty || date.compareTo(today) < 0);
      }
    });

    TextFormatter<String> formatterStudent = new TextFormatter<>(change -> {
      if (change.isAdded() && studentField.getText().length() >= 6) {
        return null;
      }
      return change;
    });
    studentField.setTextFormatter(formatterStudent);

    TextFormatter<String> formatterRoom = new TextFormatter<>(change -> {
      if (change.isAdded() && roomField.getText().length() >= 7) {
        return null;
      }
      return change;
    });
    roomField.setTextFormatter(formatterRoom);

    TextFormatter<String> formatterTime = new TextFormatter<>(change -> {
      if (change.isAdded() && timeField.getText().length() >= 5) {
        return null;
      }
      return change;
    });
    timeField.setTextFormatter(formatterTime);

    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindRoom(roomField.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindTime(timeField.textProperty());
    viewModel.bindStudent(studentField.textProperty());
    viewModel.bindDate(datePicker.valueProperty());
    viewModel.bindExaminer(examinerBox.valueProperty());
    viewModel.bindType(typeBox.valueProperty());
    viewModel.bindStudents(studentsList.itemsProperty());

    removeButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    removeButton.setDisable(true);
    viewModel.reset();
  }

  public void showError(String message){
    Alert alert = new Alert(Alert.AlertType.WARNING, message);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "exam edit success" ->
          Platform.runLater(() -> {
            viewModel.viewExamInfo();
            viewModel.removeListener(this);
            viewHandler.openView(ViewFactory.EXAM_INFO);
          });
      case "student parsing error" ->
          Platform.runLater(() -> {
            showError("The \"Student\" field only accepts numbers.");
          });
      case "student already enrolled" ->
          Platform.runLater(() -> {
            showError("The provided Student ID belongs to a student already enrolled in exam.");
          });
      case "student not in course" ->
          Platform.runLater(() -> {
            showError("The provided Student ID belongs to a student that is not enrolled in the course. "
                + "Student cannot be added to the exam.");
          });
      case "student not found" ->
          Platform.runLater(() -> {
            showError("Student ID is incorrect.");
          });
      case "information blank" ->
          Platform.runLater(() -> {
            showError("Title, room and content cannot be left empty.");
          });
      case "time parsing error" ->
          Platform.runLater(() -> {
            showError("The time has to be input in a HH:MM pattern.");
          });
      case "no students" ->
          Platform.runLater(() -> {
            showError("An exam cannot be created without any students.");
          });
    }
  }
}
