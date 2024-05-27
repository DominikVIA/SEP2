package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.MyCoursesViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * This ViewController class gives functionalities to the "my courses" view by providing code to its methods,
 * making it responsible for handling user interactions when viewing the logged-in user's list of courses
 * (acts as a main menu after logging in as a teacher).
 * It is directly connected to the FXML file making it responsible for handling user interactions.
 */
public class MyCoursesViewController {
  @FXML public ListView<Course> coursesList;
  @FXML public Button viewButton;

  private ViewHandler viewHandler;
  private MyCoursesViewModel viewModel;
  private Region root;

  /**
   * Opens the CreateCourseView.
   */
  @FXML void onAdd() throws IOException
  {
    viewHandler.openView(ViewFactory.COURSE_CREATE);
  }

  /**
   * Opens the InfoCourseView using the selected Course.
   */
  @FXML void onView() {
      if(coursesList.getSelectionModel().getSelectedItem() != null)
      {
        viewModel.viewCourse(coursesList.getSelectionModel().getSelectedItem());
        viewHandler.openView(ViewFactory.COURSE_INFO);
      }
  }

  /**
   * This method is called whenever the courses ListView is clicked, making sure the user can only view a course when one is already selected.
   */
  @FXML public void onClick() {
    viewButton.setDisable(true);

    if(coursesList.getSelectionModel().getSelectedItem() != null)
      viewButton.setDisable(false);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, MyCoursesViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param myCoursesViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, MyCoursesViewModel myCoursesViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = myCoursesViewModel;
    this.root = root;

    viewModel.bindCourses(coursesList.itemsProperty());
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
    try{
      viewButton.setDisable(true);
      viewModel.reset();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
