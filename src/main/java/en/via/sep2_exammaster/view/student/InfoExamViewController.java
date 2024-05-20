package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.InfoExamViewModel;
import en.via.sep2_exammaster.viewmodel.student.MyExamsViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.io.IOException;

public class InfoExamViewController {

  @FXML public TextField titleField;
  @FXML public TextField roomField;
  @FXML public TextArea contentArea;
  @FXML public TextField timeField;
  @FXML public TextField dateField;
  @FXML public TextField typeField;
  @FXML public TextField examinerField;
  @FXML public Label gradeLabel;
  @FXML public TextArea feedbackArea;
  @FXML public ListView<Announcement> announcementsList;
  @FXML public Button viewButton;

  private ViewHandler viewHandler;
  private InfoExamViewModel viewModel;
  private Region root;

  @FXML void onBack() {
    viewHandler.openView(ViewFactory.MY_EXAMS);
  }

  @FXML void onClick() {
    viewButton.setDisable(true);
    if(announcementsList.getSelectionModel().getSelectedItem() != null){
      viewButton.setDisable(false);
    }
  }

  @FXML void onView() {
    viewModel.viewAnnouncement(announcementsList.getSelectionModel().getSelectedItem());
//    viewHandler.openView();
  }

  public void init(ViewHandler viewHandler, InfoExamViewModel infoExamViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = infoExamViewModel;
    this.root = root;


    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindRoom(roomField.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindTime(timeField.textProperty());
    viewModel.bindDate(dateField.textProperty());
    viewModel.bindType(typeField.textProperty());
    viewModel.bindExaminer(examinerField.textProperty());
    viewModel.bindGradeLabel(gradeLabel.textProperty());
    viewModel.bindFeedback(feedbackArea.textProperty());

    viewButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewButton.setDisable(true);
    viewModel.reset();
  }

}

