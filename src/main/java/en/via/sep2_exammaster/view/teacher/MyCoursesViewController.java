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

public class MyCoursesViewController {
  @FXML public ListView<Course> coursesList;
  @FXML public Button viewButton;

  private ViewHandler viewHandler;
  private MyCoursesViewModel viewModel;
  private Region root;

  @FXML void onAdd() throws IOException
  {
    viewHandler.openView(ViewFactory.COURSE_CREATE);
  }

  @FXML void onView() {
      if(coursesList.getSelectionModel().getSelectedItem() != null)
      {
        viewModel.viewCourse(coursesList.getSelectionModel().getSelectedItem());
        viewHandler.openView(ViewFactory.COURSE_INFO);
      }
  }

  @FXML public void onClick() {
    viewButton.setDisable(true);

    if(coursesList.getSelectionModel().getSelectedItem() != null)
      viewButton.setDisable(false);
  }

  public void init(ViewHandler viewHandler, MyCoursesViewModel myCoursesViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = myCoursesViewModel;
    this.root = root;

    viewModel.bindCourses(coursesList.itemsProperty());
    viewButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

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
