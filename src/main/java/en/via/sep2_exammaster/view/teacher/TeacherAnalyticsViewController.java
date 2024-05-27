package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.TeacherAnalyticsViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * This ViewController class gives functionalities to the teacher's analytics view by providing code to its methods,
 * making it responsible for handling user interactions when viewing the analytics about a given exam.
 * It is directly connected to the FXML file making it responsible for handling user interactions.
 */
public class TeacherAnalyticsViewController {
  @FXML public Label examTitleLabel;
  @FXML public BarChart<String, Integer> gradeChart;

  private ViewHandler viewHandler;
  private TeacherAnalyticsViewModel viewModel;
  private Region root;

  /**
   * Loads the InfoCourseView into the application window, replacing this one.
   */
  @FXML void onBack() {
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, TeacherAnalyticsViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param teacherAnalyticsViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, TeacherAnalyticsViewModel teacherAnalyticsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = teacherAnalyticsViewModel;
    this.root = root;

    viewModel.bindExamLabel(examTitleLabel.textProperty());
    viewModel.bindGradeChart(gradeChart.dataProperty());

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
