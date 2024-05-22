package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.AnnouncementInfoViewModelTeacher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class AnnouncementInfoViewControllerTeacher
{

  @FXML public Text announcementTitle;
  @FXML public Label examLabel;
  @FXML public TextArea contentArea;
  @FXML public Label dateAndTimeLabel;

  private ViewHandler viewHandler;
  private AnnouncementInfoViewModelTeacher viewModel;
  private Region root;

  @FXML void onBack() {
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  public void init(ViewHandler viewHandler, AnnouncementInfoViewModelTeacher announcementInfoViewModelTeacher, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = announcementInfoViewModelTeacher;
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

