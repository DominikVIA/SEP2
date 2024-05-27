package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.CreateCourseViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * This ViewController class gives functionalities to the "create course" view by providing code to its methods,
 * making it responsible for handling user interactions when creating a new course.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
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

  /**
   * Adds a student to a course's enrolled list.
   */
  @FXML void onAdd() {
    viewModel.addStudent();
  }

  /**
   * Cancels the creation process and opens the MyCoursesView.
   */
  @FXML void onCancel() {
    reset();
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.MY_COURSES);
  }

  /**
   * This method is called whenever the students ListView is clicked, making sure the user can only remove a student when one is already selected.
   */
  @FXML void onClick() {
    removeButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      removeButton.setDisable(false);
  }

  /**
   * Creates a new course using the information provided by the user in the view. Displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O error occurs while creating a new course
   */
  @FXML
  void onCreate() throws IOException{
    viewModel.createCourse();
  }

  /**
   * Removes a student from a course's enrolled list.
   */
  @FXML void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, CreateCourseViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param createCourseViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, CreateCourseViewModel createCourseViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = createCourseViewModel;
    this.root = root;

    TextFormatter<String> formatterCode = new TextFormatter<>(change -> {
      if (change.isAdded() && codeField.getText().length() >= 12) {
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
      case "course create success" ->
        Platform.runLater(() -> {
          viewModel.removeListener(this);
          viewModel.viewCourse((Course) evt.getNewValue());
          viewHandler.openView(ViewFactory.COURSE_INFO);
        });
      case "course create fail" ->
          Platform.runLater(() -> showError("A course with this course code already exists. A different code must be used."));
      case "semester error" ->
          Platform.runLater(() -> showError("The \"Semester\" field only accepts numbers."));
      case "semester number error" ->
          Platform.runLater(() -> showError("The Semester can only be between 1 and 9 (inclusive)."));
      case "student parsing error" ->
          Platform.runLater(() -> showError("The \"Student\" field only accepts numbers."));
      case "student adding error" ->
          Platform.runLater(() -> showError("The provided Student ID belongs to a student already enrolled in course."));
      case "student not found" ->
          Platform.runLater(() -> showError("Student ID is incorrect."));
      case "teacher not found" ->
          Platform.runLater(() -> showError("Teacher initials are incorrect."));
      case "information blank" ->
          Platform.runLater(() -> showError("Code, title and description cannot be left empty."));
    }

  }
}

