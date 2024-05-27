package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.AnnouncementInfoViewModelStudent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * This ViewController class gives functionalities to the "announcement info" view for a student by providing code to its methods,
 * making it responsible for handling user interactions when viewing a given announcement's information.
 */
public class AnnouncementInfoViewControllerStudent
{

  @FXML public Text announcementTitle;
  @FXML public Label examLabel;
  @FXML public TextArea contentArea;
  @FXML public Label dateAndTimeLabel;

  private ViewHandler viewHandler;
  private AnnouncementInfoViewModelStudent viewModel;
  private Region root;

  /**
   * Loads the InfoExamView into the application window, replacing this one.
   */
  @FXML void onBack() {
    viewHandler.openView(ViewFactory.RESULT_INFO);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, AnnouncementInfoViewModelStudent and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param announcementInfoViewModelStudent  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, AnnouncementInfoViewModelStudent announcementInfoViewModelStudent, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = announcementInfoViewModelStudent;
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

