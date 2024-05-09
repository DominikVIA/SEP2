package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.viewmodel.LoginViewModel;
import en.via.sep2_exammaster.viewmodel.MyCoursesViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.io.IOException;

public class MyCoursesViewController {
  @FXML public ListView<Course> coursesList;

  private ViewHandler viewHandler;
  private MyCoursesViewModel viewModel;
  private Region root;

  @FXML void onAdd() throws IOException
  {
//    viewModel.login();
  }

  @FXML void onView() throws IOException
  {
    //    viewModel.login();
  }

  public void init(ViewHandler viewHandler, MyCoursesViewModel myCoursesViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = myCoursesViewModel;
    this.root = root;

    viewModel.bindCourses(coursesList.itemsProperty());
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    try{
      viewModel.reset();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
