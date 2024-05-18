package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.ExamInfoViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Optional;

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
    }
  }

  @FXML void onAddResults() {
    viewModel.viewAddResults();
  }

  @FXML void onBack() {
    viewModel.removeListener(this);
    viewModel.viewCourseInfo(false);
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  @FXML void onClickAnnouncement() {
    viewAnnouncementButton.setDisable(true);
    if(announcementsList.getSelectionModel().getSelectedItem() != null)
      viewAnnouncementButton.setDisable(false);
  }


  @FXML
  void onMakeAnnouncement() {
    viewModel.viewCreateAnnouncement();
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.ANNOUNCEMENT_CREATE);
  }

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

  @FXML void onEdit() {
    viewModel.onEdit();
  }

  @FXML void onViewAnnouncement() {
    viewModel.viewInfoAnnouncement(announcementsList.getSelectionModel().getSelectedItem());
  }

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

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewAnnouncementButton.setDisable(true);
    editButton.setDisable(viewModel.getExam() != null && viewModel.getExam().isCompleted());
    addResultButton.setDisable(viewModel.getExam() != null && !viewModel.getExam().isCompleted());
    viewModel.reset();
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch(evt.getPropertyName()){
      case "view exam" -> {
        Platform.runLater(() -> {
          editButton.setDisable(((Exam) evt.getNewValue()).isCompleted());
          addResultButton.setDisable(!((Exam) evt.getNewValue()).isCompleted());
          completeButton.setDisable(((Exam) evt.getNewValue()).isCompleted());
        });
      }
      case "edit exam" -> {
        Platform.runLater(() -> {
          viewHandler.openView(ViewFactory.EXAM_EDIT);
          viewModel.removeListener(this);
        });
      }
      case "add results" -> {
        Platform.runLater(() -> {
          viewHandler.openView(ViewFactory.RESULTS_ADD);
          viewModel.removeListener(this);
        });
      }
      default -> {
        Platform.runLater(() -> {
          viewHandler.openView(ViewFactory.ANNOUNCEMENT_INFO);
          viewModel.removeListener(this);
        });
      }
    }

  }
}

