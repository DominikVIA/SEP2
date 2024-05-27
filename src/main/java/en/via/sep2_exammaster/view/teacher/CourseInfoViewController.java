package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.CourseInfoViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Optional;

/**
 * This ViewController class gives functionalities to the "course info" view by providing code to its methods,
 * making it responsible for handling user interactions when viewing the information about a course.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class CourseInfoViewController implements PropertyChangeListener {
  @FXML public TextField codeField;
  @FXML public TextArea descriptionArea;
  @FXML public ListView<Exam> examsList;
  @FXML public TextField semesterField;
  @FXML public ListView<Student> studentsList;
  @FXML public TextField titleField;
  @FXML public Button viewButton;
  @FXML public Button getAnalyticsButton;

  private ViewHandler viewHandler;
  private CourseInfoViewModel viewModel;
  private Region root;

  /**
   * Loads the CreateExamView into the application window, replacing this one.
   *
   * @throws IOException if an I/O exception occurs while loading/opening the view
   */
  @FXML void onCreateExam() throws IOException {
    viewModel.onCreateExam();
  }

  /**
   * Deletes the course that's being displayed after the action is confirmed by the user.
   *
   * @throws IOException if an I/O exception occurs while deleting the course
   */
  @FXML void onDeleteCourse() throws IOException {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this course?\n"+
        "It will cause all associated information to be deleted, such as exams, the corresponding results, etc.",
        ButtonType.OK, ButtonType.CANCEL);
    alert.setHeaderText(null);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK){
      viewModel.onDelete();
      viewHandler.openView(ViewFactory.MY_COURSES);
      viewModel.removeListener(this);
    }
  }

  /**
   * Loads the EditCourseView into the application window, replacing this one.
   */
  @FXML void onEditCourse() throws IOException
  {
    viewHandler.openView(ViewFactory.COURSE_EDIT);
    viewModel.onEdit();
    viewModel.removeListener(this);
  }

  /**
   * Opens a view for viewing a selected exam's information.
   *
   * @throws IOException if an I/O exception occurs while opening the view
   */
  @FXML void onViewExam() throws IOException
  {
    viewModel.onViewExam(examsList.getSelectionModel().getSelectedItem());
  }

  /**
   * Opens a view for viewing exam analytics.
   *
   * @throws IOException if an I/O exception occurs while opening the view
   */
  @FXML void onGetAnalytics() throws IOException{
    viewModel.onViewAnalytics(examsList.getSelectionModel().getSelectedItem());
  }

  /**
   * This method is called whenever the exams ListView is clicked, 
   * making sure the user can only view an exam or view exam analytics when one is already selected.
   */
  @FXML void onClick(){
    viewButton.setDisable(true);
    getAnalyticsButton.setDisable(true);

    if(examsList.getSelectionModel().getSelectedItem() != null)
    {
      viewButton.setDisable(false);
      getAnalyticsButton.setDisable(false);
    }
  }

  /**
   * Loads the MyCoursesView into the application window, replacing this one.
   */
  @FXML void onBack(){
    viewHandler.openView(ViewFactory.MY_COURSES);
    viewModel.removeListener(this);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, CourseInfoViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param courseInfoViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, CourseInfoViewModel courseInfoViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = courseInfoViewModel;
    this.root = root;

    viewModel.bindCode(codeField.textProperty());
    viewModel.bindSemester(semesterField.textProperty());
    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindDescription(descriptionArea.textProperty());
    viewModel.bindStudents(studentsList.itemsProperty());
    viewModel.bindExams(examsList.itemsProperty());

    viewButton.setDisable(true);
    getAnalyticsButton.setDisable(true);
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
    viewButton.setDisable(true);
    getAnalyticsButton.setDisable(true);
    viewModel.reset();
  }

  /**
   * Handles property change events fired by its subject (ViewModel).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "create exam" -> viewHandler.openView(ViewFactory.EXAM_CREATE);
      case "view exam" -> viewHandler.openView(ViewFactory.EXAM_INFO);
      case "view analytics" -> viewHandler.openView(ViewFactory.TEACHER_ANALYTICS);
    }

    viewModel.removeListener(this);
  }
}
