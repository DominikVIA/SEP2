package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.StudentAnalyticsViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;

public class StudentAnalyticsViewController {
  @FXML public LineChart<Number, Number> averageLineChart;
  @FXML public PieChart gradePieChart;
  @FXML public ChoiceBox<String> semesterChoiceBox;

  private ViewHandler viewHandler;
  private StudentAnalyticsViewModel viewModel;
  private Region root;

  @FXML
  void onBack() {
    viewHandler.openView(ViewFactory.MY_EXAMS);
  }

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

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewModel.reset();
    semesterChoiceBox.getSelectionModel().selectFirst();
  }

}
