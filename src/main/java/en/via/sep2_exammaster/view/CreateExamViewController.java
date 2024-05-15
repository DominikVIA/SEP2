package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CreateCourseViewModel;
import en.via.sep2_exammaster.viewmodel.CreateExamViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.Chronology;

public class CreateExamViewController implements PropertyChangeListener {

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
  @FXML public Button addAllButton;
  @FXML public Button removeButton;

  private ViewHandler viewHandler;
  private CreateExamViewModel viewModel;
  private Region root;

  @FXML void onAdd() {
    viewModel.addStudent();
    if(!addAllButton.isDisabled()) addAllButton.setDisable(true);
  }

  @FXML void onAddAll() {
    viewModel.addAllStudents();
    addButton.setDisable(true);
  }

  @FXML
  void onCancel(ActionEvent event) {

  }

  @FXML void onClick() {
    removeButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      removeButton.setDisable(false);
  }

  @FXML
  void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
    if(addButton.isDisabled()) addButton.setDisable(false);
  }

  @FXML
  void onSave() throws IOException {
    viewModel.onSave();
  }

  public void init(ViewHandler viewHandler, CreateExamViewModel createExamViewModel, Region root){
    this.viewHandler = viewHandler;
    this.viewModel = createExamViewModel;
    this.root = root;

    typeBox.getItems().add("Written");
    typeBox.getItems().add("Oral");
    typeBox.getSelectionModel().select(1);

    examinerBox.getItems().add("Internal");
    examinerBox.getItems().add("External");
    examinerBox.getItems().add("Both");
    examinerBox.getSelectionModel().select(1);

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
      case "exam create success" ->
          Platform.runLater(() -> {
            viewModel.removeListener(this);
            viewHandler.openView(ViewFactory.MY_COURSES);
          });
      case "student parsing error" ->
          Platform.runLater(() -> {
            showError("The \"Student\" field only accepts numbers.");
          });
      case "student adding error" ->
          Platform.runLater(() -> {
            showError("The provided Student ID belongs to a student already enrolled in exam.");
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
    }
  }
}

