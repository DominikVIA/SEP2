package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.EditCourseViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * This ViewController class gives functionalities to the "edit course" view by providing code to its methods,
 * making it responsible for handling user interactions when editing an existing course.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class EditCourseViewController implements PropertyChangeListener {

  @FXML public TextField codeField;
  @FXML public TextField semesterField;
  @FXML public TextField titleField;
  @FXML public TextArea descriptionArea;
  @FXML public TextField additionalTeacherField;
  @FXML public TextField studentField;
  @FXML public ListView<Student> studentsList;
  @FXML public Button removeButton;

  private ViewHandler viewHandler;
  private EditCourseViewModel viewModel;
  private Region root;

  /**
   * This method is called whenever the students ListView is clicked, making sure the user can only remove a student when one is already selected.
   */
  @FXML void onClick() {
    removeButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      removeButton.setDisable(false);
  }

  /**
   * Removes a selected student from a course's enrolled list.
   */
  @FXML void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
  }

  /**
   * Adds a student to a course's enrolled list.
   */
  @FXML void onAdd() {
    viewModel.addStudent();
  }

  /**
   * Saves the newly input information about a course and displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O exception occurs while saving the information
   */
  @FXML void onSave() throws IOException {
    viewModel.onSave();
  }

  /**
   * Cancels the editing process and loads the MyCoursesView into the application window, replacing this one.
   */
  @FXML void onCancel() {
    reset();
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.MY_COURSES);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, EditCourseViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param editCourseViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, EditCourseViewModel editCourseViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = editCourseViewModel;
    this.root = root;

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
    viewModel.bindAdditionalTeacher(additionalTeacherField.textProperty());
    viewModel.bindStudent(studentField.textProperty());
    viewModel.bindStudents(studentsList.itemsProperty());

    removeButton.setDisable(true);
  }

  /**
   * Returns the Region of this ViewController.
   *
   * @return the Region saved in the root variable
   */
  public Region getRoot() {
    return root;
  }

  /**
   * Resets this ViewController and its ViewModel.
   */
  public void reset() {
    removeButton.setDisable(true);
    viewModel.reset();
  }

  /**
   * Shows an error with the specified message.
   *
   * @param message the error message to be displayed
   */
  public void showError(String message){
    Alert alert = new Alert(Alert.AlertType.WARNING, message);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  /**
   * Handles property change events fired by its subject (ViewModel).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "course edit success" ->
          Platform.runLater(() -> {
            viewModel.removeListener(this);
            viewModel.viewCourse();
            viewHandler.openView(ViewFactory.COURSE_INFO);
          });
      case "semester error" ->
          Platform.runLater(() -> showError("The \"Semester\" field only accepts numbers."));
      case "student parsing error" ->
          Platform.runLater(() -> showError("The \"Student\" field only accepts numbers."));
      case "student adding error" ->
          Platform.runLater(() -> showError("The provided Student ID belongs to a student already enrolled in semester."));
      case "student not found" ->
          Platform.runLater(() -> showError("Student ID is incorrect."));
      case "teacher not found" ->
          Platform.runLater(() -> showError("Teacher initials are incorrect."));
    }

  }

}

