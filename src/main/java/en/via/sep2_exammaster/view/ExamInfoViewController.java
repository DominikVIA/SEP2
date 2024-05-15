package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.ExamInfoViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

  private ViewHandler viewHandler;
  private ExamInfoViewModel viewModel;
  private Region root;

  @FXML void onAddResult(ActionEvent event) {

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

  @FXML void onDelete(ActionEvent event) {

  }

  @FXML void onEdit(ActionEvent event) {

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
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewButton.setDisable(true);
    addResultButton.setDisable(true);
    viewModel.reset();
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {

  }
}

