package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

/**
 * The TeacherAnalyticsViewModel class represents the view model for displaying
 * analytics regarding an exam for teachers.
 * It interacts with the Model as a source of exam result-related information
 * and provides properties for the grade chart and a title within the view containing the name of the exam.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class TeacherAnalyticsViewModel implements PropertyChangeListener {
  /**
   * List of results from an exam, gotten through a fired property change event.
   */
  private List<Result> resultList;
  /**
   * Instance of the Model class to allow communication with the server.
   */
  private final Model model;
  /**
   * Property for binding with the BoxChart in the view to add values to the graph.
   */
  private final ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> gradeChart;
  /**
   * StringProperty for setting the exam title in the FXML view.
   */
  private final StringProperty examLabel;

  /**
   * Constructs a TeacherAnalyticsViewModel with the given model.
   *
   * @param model the model for getting exam result information
   */
  public TeacherAnalyticsViewModel(Model model){
    this.model = model;
    this.model.addListener(this);
    gradeChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    examLabel = new SimpleStringProperty("");
  }

  /**
   * Resets the viewModel by setting the examLabel to the title of a result's exam and by
   * adding the grades from all the gotten results to the graph.
   */
  public void reset(){
    if(resultList != null){
      examLabel.set(resultList.get(0).getExam().getTitle());
      gradeChart.getValue().clear();
      gradeChart.getValue().add(new XYChart.Series<>());
      int[] gradeCounter = new int[9];
      for(Result temp : resultList){
        switch (temp.getGrade()){
          case A -> gradeCounter[0]++;
          case B -> gradeCounter[1]++;
          case C -> gradeCounter[2]++;
          case D -> gradeCounter[3]++;
          case E -> gradeCounter[4]++;
          case Fx -> gradeCounter[5]++;
          case F -> gradeCounter[6]++;
          case Sick -> gradeCounter[7]++;
          case Null -> gradeCounter[8]++;
        }
      }
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.A.toString(), gradeCounter[0]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.B.toString(), gradeCounter[1]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.C.toString(), gradeCounter[2]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.D.toString(), gradeCounter[3]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.E.toString(), gradeCounter[4]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.Fx.toString(), gradeCounter[5]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.F.toString(), gradeCounter[6]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>(Grade.Sick.toString(), gradeCounter[7]));
      gradeChart.getValue().get(0).getData().add(new XYChart.Data<>("Not graded", gradeCounter[8]));
    }
  }

  /**
   * Binds the provided StringProperty to the examLabel property,
   * enabling one-way accessing and changing of the exam title label in the FXML view.
   *
   * @param property the StringProperty to bind to the examLabel property
   */
  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
  }

  /**
   * Binds the provided ObjectProperty to the gradeChart property,
   * enabling one-way accessing and changing of the BarChart in the FXML view.
   *
   * @param property the ObjectProperty to bind to the gradeChart property
   */
  public void bindGradeChart(ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> property){
    property.bind(gradeChart);
  }

  /**
   * Handles property change events, specifically one relating to viewing analytics to get the exam results.
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    try {

      if (evt.getPropertyName().equals("view analytics"))
      {
        resultList = model.getResultsByExam((Exam) evt.getNewValue());
        reset();
      }

    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
