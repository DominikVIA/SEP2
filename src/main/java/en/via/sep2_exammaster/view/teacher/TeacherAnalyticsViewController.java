package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.TeacherAnalyticsViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class TeacherAnalyticsViewController {
  @FXML public Label examTitleLabel;
  @FXML public BarChart<String, Integer> gradeChart;

  private ViewHandler viewHandler;
  private TeacherAnalyticsViewModel viewModel;
  private Region root;

  @FXML void onBack() {
    viewHandler.openView(ViewFactory.COURSE_INFO);
  }

  public void init(ViewHandler viewHandler, TeacherAnalyticsViewModel teacherAnalyticsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = teacherAnalyticsViewModel;
    this.root = root;

    viewModel.bindExamLabel(examTitleLabel.textProperty());
    viewModel.bindGradeChart(gradeChart.dataProperty());

  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewModel.reset();
  }

}
