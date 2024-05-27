package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.AnnouncementInfoViewModelTeacher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * This ViewController class gives functionalities to the "announcement info"
 * view for the teacher by providing code to its methods,
 * making it responsible for handling user interactions when viewing the information about an announcement.
 */
public class AnnouncementInfoViewControllerTeacher
{

  @FXML public Text announcementTitle;
  @FXML public Label examLabel;
  @FXML public TextArea contentArea;
  @FXML public Label dateAndTimeLabel;

  private ViewHandler viewHandler;
  private AnnouncementInfoViewModelTeacher viewModel;
  private Region root;

  /**
   * Loads the InfoExamView into the application window, replacing this one.
   */
  @FXML void onBack() {
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, AnnouncementInfoViewModelTeacher and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param announcementInfoViewModelTeacher  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, AnnouncementInfoViewModelTeacher announcementInfoViewModelTeacher, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = announcementInfoViewModelTeacher;
    this.root = root;

    viewModel.bindTitleLabel(announcementTitle.textProperty());
    viewModel.bindExamLabel(examLabel.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindDayAndTimeLabel(dateAndTimeLabel.textProperty());
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
    viewModel.reset();
  }

}

