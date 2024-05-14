package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.EditCourseViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class EditCourseViewController implements PropertyChangeListener {

  @FXML public TextField codeField;
  @FXML public TextField semesterField;
  @FXML public TextField titleField;
  @FXML public TextArea descriptionArea;
  @FXML public TextField additonalTeacherField;
  @FXML public TextField studentField;
  @FXML public ListView<Student> studentsList;
  @FXML public Button removeButton;

  private ViewHandler viewHandler;
  private EditCourseViewModel viewModel;
  private Region root;

  @FXML void onClick() {
    removeButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      removeButton.setDisable(false);
  }

  @FXML void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
  }

  @FXML void onAdd() {
    viewModel.addStudent();
  }

  @FXML void onSave() throws IOException {
    viewModel.onSave();
  }

  @FXML void onCancel() {
    reset();
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.MY_COURSES);
  }

  public void init(ViewHandler viewHandler, EditCourseViewModel editCourseViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = editCourseViewModel;
    this.root = root;

    viewModel.bindCode(codeField.textProperty());
    viewModel.bindSemester(semesterField.textProperty());
    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindDescription(descriptionArea.textProperty());
    viewModel.bindAdditionalTeacher(additonalTeacherField.textProperty());
    viewModel.bindStudent(studentField.textProperty());
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
      case "edit success" ->
          Platform.runLater(() -> {
            viewModel.removeListener(this);
            viewModel.viewCourse();
            viewHandler.openView(ViewFactory.COURSE_INFO);
          });
      case "semester error" ->
          Platform.runLater(() -> {
            showError("The \"Semester\" field only accepts numbers.");
          });
      case "student parsing error" ->
          Platform.runLater(() -> {
            showError("The \"Student\" field only accepts numbers.");
          });
      case "student adding error" ->
          Platform.runLater(() -> {
            showError("The provided Student ID belongs to a student already enrolled in semester.");
          });
      case "student not found" ->
          Platform.runLater(() -> {
            showError("Student ID is incorrect.");
          });
      case "teacher not found" ->
          Platform.runLater(() -> {
            showError("Teacher initials are incorrect.");
          });
    }

  }

}

