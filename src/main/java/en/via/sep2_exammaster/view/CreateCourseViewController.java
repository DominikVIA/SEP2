package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.CreateCourseViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CreateCourseViewController implements PropertyChangeListener {
  @FXML private TextField additionalTeacherField;
  @FXML private TextField codeField;
  @FXML private TextArea descriptionArea;
  @FXML private Button removeButton;
  @FXML private TextField semesterField;
  @FXML private TextField studentField;
  @FXML private ListView<Student> studentsList;
  @FXML private TextField titleField;

  private ViewHandler viewHandler;
  private CreateCourseViewModel viewModel;
  private Region root;

  @FXML void onAdd() {
    viewModel.addStudent();
  }

  @FXML void onCancel() {
    reset();
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  @FXML void onClick() {
    removeButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      removeButton.setDisable(false);
  }

  @FXML
  void onCreate() throws IOException{
    viewModel.createCourse();
  }

  @FXML void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
  }

  public void init(ViewHandler viewHandler, CreateCourseViewModel createCourseViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = createCourseViewModel;
    this.root = root;

    TextFormatter<String> formatterCode = new TextFormatter<>(change -> {
      if (change.isAdded() && codeField.getText().length() >= 4) {
        return null;
      }
      return change;
    });
    codeField.setTextFormatter(formatterCode);

    TextFormatter<String> formatterStudent = new TextFormatter<>(change -> {
      if (change.isAdded() && studentField.getText().length() >= 6) {
        return null;
      }
      return change;
    });
    studentField.setTextFormatter(formatterStudent);

    viewModel.bindCode(codeField.textProperty());
    viewModel.bindSemester(semesterField.textProperty());
    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindDescription(descriptionArea.textProperty());
    viewModel.bindStudent(studentField.textProperty());
    viewModel.bindAdditionalTeacher(additionalTeacherField.textProperty());
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
      case "course create success" ->
        Platform.runLater(() -> {
          viewModel.removeListener(this);
          viewModel.viewCourse((Course) evt.getNewValue());
          viewHandler.openView(ViewFactory.COURSE_INFO);
        });
      case "course create fail" ->
          Platform.runLater(() -> {
            showError("A course with this course code already exists. A different code must be used.");
          });
      case "semester error" ->
          Platform.runLater(() -> {
            showError("The \"Semester\" field only accepts numbers.");
          });
      case "semester number error" ->
          Platform.runLater(() -> {
            showError("The Semester can only be between 1 and 9 (inclusive).");
          });
      case "student parsing error" ->
          Platform.runLater(() -> {
            showError("The \"Student\" field only accepts numbers.");
          });
      case "student adding error" ->
          Platform.runLater(() -> {
            showError("The provided Student ID belongs to a student already enrolled in course.");
          });
      case "student not found" ->
          Platform.runLater(() -> {
            showError("Student ID is incorrect.");
          });
      case "teacher not found" ->
          Platform.runLater(() -> {
            showError("Teacher initials are incorrect.");
          });
      case "information blank" ->
          Platform.runLater(() -> {
            showError("Code, title and description cannot be left empty.");
          });
    }

  }
}

