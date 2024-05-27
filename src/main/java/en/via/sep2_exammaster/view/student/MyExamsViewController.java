package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.shared.Result;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.MyExamsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * This ViewController class gives functionalities to the "my exams" view by providing code to its methods,
 * making it responsible for handling user interactions when viewing the logged-in user's list of exams
 * (acts as a main menu after logging in as a student).
 */
public class MyExamsViewController {

  @FXML public Button viewExamButton;
  @FXML public ListView<Result> upcomingList;
  @FXML public ListView<Result> completedList;
  @FXML public TabPane tabPane;

  private ViewHandler viewHandler;
  private MyExamsViewModel viewModel;
  private Region root;

  /**
   * This method is called whenever the upcoming ListView or completed ListView is clicked,
   * making sure the user can only view an exam when one is already selected.
   */
  @FXML void onClick() {
    viewExamButton.setDisable(true);
    if(upcomingList.getSelectionModel().getSelectedItem() != null
        || completedList.getSelectionModel().getSelectedItem() != null)
      viewExamButton.setDisable(false);
  }

  /**
   * Loads the MyExamsView into the application window, replacing this one.
   */
  @FXML void onViewExam() {
    if(completedList.getSelectionModel().getSelectedItem() != null) {
      viewModel.viewResult(completedList.getSelectionModel().getSelectedItem());
      viewHandler.openView(ViewFactory.RESULT_INFO);
    }
    else {
      viewModel.viewResult(upcomingList.getSelectionModel().getSelectedItem());
      viewHandler.openView(ViewFactory.RESULT_INFO);
    }
  }

  /**
   * Opens a view for viewing exam analytics.
   */
  @FXML void onViewAnalytics(){
    viewHandler.openView(ViewFactory.STUDENT_ANALYTICS);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, MyExamsViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param myExamsViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, MyExamsViewModel myExamsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = myExamsViewModel;
    this.root = root;

    tabPane.getSelectionModel().selectedItemProperty().addListener(
        (ov, t, t1) -> {
          upcomingList.getSelectionModel().clearSelection();
          completedList.getSelectionModel().clearSelection();
          viewExamButton.setDisable(true);
        });

    viewModel.bindUpcoming(upcomingList.itemsProperty());
    viewModel.bindCompleted(completedList.itemsProperty());
    viewExamButton.setDisable(true);
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
    try{
      viewExamButton.setDisable(true);
      viewModel.reset();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
