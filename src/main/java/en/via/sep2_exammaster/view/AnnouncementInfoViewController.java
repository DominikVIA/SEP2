package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.viewmodel.AnnouncementInfoViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class AnnouncementInfoViewController {

  @FXML public Text announcementTitle;
  @FXML public Label examLabel;
  @FXML public TextArea contentArea;
  @FXML public Label dateAndTimeLabel;

  private ViewHandler viewHandler;
  private AnnouncementInfoViewModel viewModel;
  private Region root;

  @FXML void onBack() {
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  public void init(ViewHandler viewHandler, AnnouncementInfoViewModel announcementInfoViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = announcementInfoViewModel;
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

