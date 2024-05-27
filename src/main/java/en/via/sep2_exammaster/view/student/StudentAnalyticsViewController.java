package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.StudentAnalyticsViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;

/**
 * This ViewController class gives functionalities to the student's "analytics" view by providing code to its methods,
 * making it responsible for handling user interactions when viewing t.
 */
public class StudentAnalyticsViewController {
  @FXML public LineChart<Number, Number> averageLineChart;
  @FXML public PieChart gradePieChart;
  @FXML public ChoiceBox<String> semesterChoiceBox;

  private ViewHandler viewHandler;
  private StudentAnalyticsViewModel viewModel;
  private Region root;

  /**
   * Loads the MyExamsView into the application window, replacing this one.
   */
  @FXML
  void onBack() {
    viewHandler.openView(ViewFactory.MY_EXAMS);
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, StudentAnalyticsViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param studentAnalyticsViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, StudentAnalyticsViewModel studentAnalyticsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = studentAnalyticsViewModel;
    this.root = root;

    semesterChoiceBox.valueProperty().addListener(
        (observable, oldValue, newValue) -> viewModel.pieChartUpdate());

    viewModel.bindLineChart(averageLineChart.dataProperty());
    viewModel.bindPieChart(gradePieChart.dataProperty());
    viewModel.bindSemesterItems(semesterChoiceBox.itemsProperty());
    viewModel.bindSemesterChosen(semesterChoiceBox.valueProperty());
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
    semesterChoiceBox.getSelectionModel().selectFirst();
  }

}
