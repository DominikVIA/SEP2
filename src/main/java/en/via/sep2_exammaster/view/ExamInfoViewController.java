package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.ExamInfoViewModel;
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
  @FXML public Button viewButton;
  @FXML public Button editButton;

  private ViewHandler viewHandler;
  private ExamInfoViewModel viewModel;
  private Region root;

  @FXML void onAddResult() {

  }

  @FXML void onBack() {
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  @FXML void onClickAnnouncement() {
    viewButton.setDisable(true);
    if(announcementsList.getSelectionModel().getSelectedItem() != null)
      viewButton.setDisable(false);
  }

  @FXML void onClickStudent() {
    addResultButton.setDisable(true);
    if(studentsList.getSelectionModel().getSelectedItem() != null)
      addResultButton.setDisable(false);
  }

  @FXML void onDelete() throws IOException
  {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this exam?\n"+
        "It will cause all associated information to be deleted, i.e. corresponding results.",
        ButtonType.OK, ButtonType.CANCEL);
    alert.setHeaderText(null);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK){
      viewModel.onDelete();
      viewHandler.openView(ViewFactory.MY_COURSES);
      viewModel.removeListener(this);
    }
  }

  @FXML void onEdit() {
    viewModel.onEdit();
  }

  @FXML void onMake(ActionEvent event) {

  }

  @FXML void onView(ActionEvent event) {

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

    viewButton.setDisable(true);
    addResultButton.setDisable(true);
    if(viewModel.getExam().isCompleted()) editButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewButton.setDisable(true);
    addResultButton.setDisable(true);
    if(viewModel.getExam().isCompleted()) editButton.setDisable(true);
    viewModel.reset();
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    viewHandler.openView(ViewFactory.EXAM_EDIT);
    viewModel.removeListener(this);
  }
}

