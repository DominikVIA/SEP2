package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.InfoExamViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

/**
 * This ViewController class gives functionalities to the "exam info" view by providing code to its methods,
 * making it responsible for handling user interactions when viewing a given exam's and its result's information.
 */
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

  /**
   * Loads the MyExamsView into the application window, replacing this one.
   */
  @FXML void onBack() {
    viewHandler.openView(ViewFactory.MY_EXAMS);
  }

  /**
   * This method is called whenever the announcements ListView is clicked,
   * making sure the user can only view an announcement's information when one is already selected.
   */
  @FXML void onClick() {
    viewButton.setDisable(true);
    if(announcementsList.getSelectionModel().getSelectedItem() != null){
      viewButton.setDisable(false);
    }
  }

  /**
   * Loads the AnnouncementInfoView into the application window, replacing this one, displaying the selected announcement's information.
   */
  @FXML void onView() {
    viewModel.viewAnnouncementInfo(announcementsList.getSelectionModel().getSelectedItem());
    viewHandler.openView(ViewFactory.ANNOUNCEMENT_INFO_STUDENT);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, InfoExamViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param infoExamViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
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
    viewModel.bindAnnouncements(announcementsList.itemsProperty());

    viewButton.setDisable(true);
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
    viewButton.setDisable(true);
    viewModel.reset();
  }

}

