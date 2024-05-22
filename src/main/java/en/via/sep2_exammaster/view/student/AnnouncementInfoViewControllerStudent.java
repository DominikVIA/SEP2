package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.AnnouncementInfoViewModelStudent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class AnnouncementInfoViewControllerStudent
{

  @FXML public Text announcementTitle;
  @FXML public Label examLabel;
  @FXML public TextArea contentArea;
  @FXML public Label dateAndTimeLabel;

  private ViewHandler viewHandler;
  private AnnouncementInfoViewModelStudent viewModel;
  private Region root;

  @FXML void onBack() {
    viewHandler.openView(ViewFactory.RESULT_INFO);
  }

  public void init(ViewHandler viewHandler, AnnouncementInfoViewModelStudent announcementInfoViewModelStudent, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = announcementInfoViewModelStudent;
    this.root = root;

    viewModel.bindTitleLabel(announcementTitle.textProperty());
    viewModel.bindExamLabel(examLabel.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindDayAndTimeLabel(dateAndTimeLabel.textProperty());
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewModel.reset();
  }

}

