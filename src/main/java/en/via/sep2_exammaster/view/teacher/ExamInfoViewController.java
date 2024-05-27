package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.ExamInfoViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Optional;

/**
 * This ViewController class gives functionalities to the "exam info" view by providing code to its methods,
 * making it responsible for handling user interactions when viewing information about an exam.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class ExamInfoViewController implements PropertyChangeListener {
  @FXML public TextField titleField;
  @FXML public TextField roomField;
  @FXML public TextArea contentArea;
  @FXML public TextField timeField;
  @FXML public TextField dateField;
  @FXML public TextField typeField;
  @FXML public TextField examinerField;
  @FXML public ListView<Announcement> announcementsList;
  @FXML public ListView<Student> studentsList;
  @FXML public Button addResultButton;
  @FXML public Button viewAnnouncementButton;
  @FXML public Button editButton;
  @FXML public Button completeButton;

  private ViewHandler viewHandler;
  private ExamInfoViewModel viewModel;
  private Region root;

  /**
   * Marks the given exam as completed.
   *
   * @throws IOException if an I/O exception occurs while marking an exam as completed
   */
  @FXML
  void onMarkCompleted() throws IOException
  {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to mark this exam as completed?\n"+
        "This action is irreversible and will make editing the exam information impossible. Do you want to continue?",
        ButtonType.OK, ButtonType.CANCEL);
    alert.setHeaderText(null);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK) {
      viewModel.markExamCompleted();
      completeButton.setDisable(true);
      editButton.setDisable(true);
      addResultButton.setDisable(false);
    }
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "add results" view.
   */
  @FXML void onAddResults() {
    viewModel.viewAddResults();
  }

  /**
   * Loads the InfoCourseView into the application window, replacing this one.
   */
  @FXML void onBack() {
    viewModel.removeListener(this);
    viewModel.viewCourseInfo(false);
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  /**
   * This method is called whenever the announcements ListView is clicked, making sure the user can only view an announcement when one is already selected.
   */
  @FXML void onClickAnnouncement() {
    viewAnnouncementButton.setDisable(true);
    if(announcementsList.getSelectionModel().getSelectedItem() != null)
      viewAnnouncementButton.setDisable(false);
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "create announcement" view.
   */
  @FXML
  void onMakeAnnouncement() {
    viewModel.viewCreateAnnouncement();
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.ANNOUNCEMENT_CREATE);
  }

  /**
   * Deletes the given exam after the action is confirmed by the user.
   *
   * @throws IOException if an I/O exception occurs while deleting the exam
   */
  @FXML void onDelete() throws IOException {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this exam?\n"+
        "It will cause all associated information to be deleted, i.e. corresponding results.",
        ButtonType.OK, ButtonType.CANCEL);
    alert.setHeaderText(null);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK){
      viewModel.onDelete();
      viewHandler.openView(ViewFactory.COURSE_INFO);
      viewModel.removeListener(this);
    }
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "edit exam" view.
   */
  @FXML void onEdit() {
    viewModel.onEdit();
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "announcement info" view.
   */
  @FXML void onViewAnnouncement() {
    viewModel.viewAnnouncementInfo(announcementsList.getSelectionModel().getSelectedItem());
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, ExamInfoViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param examInfoViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, ExamInfoViewModel examInfoViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = examInfoViewModel;
    this.root = root;

    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindRoom(roomField.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindTime(timeField.textProperty());
    viewModel.bindDate(dateField.textProperty());
    viewModel.bindType(typeField.textProperty());
    viewModel.bindExaminer(examinerField.textProperty());
    viewModel.bindStudents(studentsList.itemsProperty());
    viewModel.bindAnnouncements(announcementsList.itemsProperty());
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
    viewAnnouncementButton.setDisable(true);
    editButton.setDisable(viewModel.getExam() != null && viewModel.getExam().isCompleted());
    addResultButton.setDisable(viewModel.getExam() != null && !viewModel.getExam().isCompleted());
    viewModel.reset();
  }

  /**
   * Handles property change events fired by its subject (ViewModel).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch(evt.getPropertyName()){
      case "view exam" ->
        Platform.runLater(() -> {
          editButton.setDisable(((Exam) evt.getNewValue()).isCompleted());
          addResultButton.setDisable(!((Exam) evt.getNewValue()).isCompleted());
          completeButton.setDisable(((Exam) evt.getNewValue()).isCompleted());
        });
      case "edit exam" ->
        Platform.runLater(() -> {
          viewHandler.openView(ViewFactory.EXAM_EDIT);
          viewModel.removeListener(this);
        });
      case "add results" ->
        Platform.runLater(() -> {
          viewHandler.openView(ViewFactory.RESULTS_ADD);
          viewModel.removeListener(this);
        });
      default ->
        Platform.runLater(() -> {
          viewHandler.openView(ViewFactory.ANNOUNCEMENT_INFO_TEACHER);
          viewModel.removeListener(this);
        });
    }

  }
}

