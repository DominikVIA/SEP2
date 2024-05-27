package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.CreateExamViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;

/**
 * This ViewController class gives functionalities to the "create exam" view by providing code to its methods,
 * making it responsible for handling user interactions when creating a new exam.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
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

  /**
   * Adds a student to an exam's participation list.
   */
  @FXML void onAdd() {
    viewModel.addStudent();
    if(!addAllButton.isDisabled()) addAllButton.setDisable(true);
  }

  /**
   * Adds all students enrolled in a course to the participation list of the newly created exam.
   */
  @FXML void onAddAll() {
    viewModel.addAllStudents();
    addButton.setDisable(true);
    addAllButton.setDisable(true);
  }

  /**
   * Cancels the creation process and loads the InfoCourseView into the application window, replacing this one.
   */
  @FXML
  void onCancel() {
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.COURSE_INFO);
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
   * Removes the selected student from a course's enrolled list.
   */
  @FXML
  void onRemove() {
    viewModel.remove(studentsList.getSelectionModel().getSelectedItem());
    removeButton.setDisable(true);
    if(addButton.isDisabled()) addButton.setDisable(false);
    if(studentsList.getItems().isEmpty()) addAllButton.setDisable(false);
  }

  /**
   * Creates a new exam using the information provided by the user in the view. Displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O error occurs while creating a new exam
   */
  @FXML
  void onCreate() throws IOException {
    viewModel.onCreate();
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, CreateExamViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param createExamViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, CreateExamViewModel createExamViewModel, Region root){
    this.viewHandler = viewHandler;
    this.viewModel = createExamViewModel;
    this.root = root;

    typeBox.getItems().add("Written");
    typeBox.getItems().add("Oral");

    examinerBox.getItems().add("Internal");
    examinerBox.getItems().add("External");
    examinerBox.getItems().add("Both");

    datePicker.setDayCellFactory(picker -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();
        setDisable(empty || date.isBefore(today));
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

    addButton.setDisable(false);
    addAllButton.setDisable(false);
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
    typeBox.getSelectionModel().selectFirst();
    examinerBox.getSelectionModel().selectFirst();
    removeButton.setDisable(true);
    addButton.setDisable(false);
    addAllButton.setDisable(false);
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
      case "exam create success" ->
          Platform.runLater(() -> {
            viewModel.removeListener(this);
            Exam temp = (Exam) evt.getNewValue();
            temp.getCourse().addExam(temp);
            viewModel.viewCourse(temp.getCourse());
            viewHandler.openView(ViewFactory.COURSE_INFO);
          });
      case "student parsing error" ->
          Platform.runLater(() -> showError("The \"Student\" field only accepts numbers."));
      case "student already enrolled" ->
          Platform.runLater(() -> showError("The provided Student ID belongs to a student already enrolled in exam."));
      case "student not in course" ->
      Platform.runLater(() -> showError("The provided Student ID belongs to a student that is not enrolled in the course. "
          + "Student cannot be added to the exam."));
      case "student not found" ->
          Platform.runLater(() -> showError("Student ID is incorrect."));
      case "information blank" ->
          Platform.runLater(() -> showError("Title, room and content cannot be left empty."));
      case "time parsing error" ->
          Platform.runLater(() -> showError("The time has to be input in a HH:MM pattern."));
      case "no students" ->
          Platform.runLater(() -> showError("An exam cannot be created without any students."));
    }
  }
}

