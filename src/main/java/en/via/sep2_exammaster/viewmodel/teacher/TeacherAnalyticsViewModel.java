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

public class TeacherAnalyticsViewModel implements PropertyChangeListener {
  private List<Result> resultList;
  private final Model model;
  private final ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> gradeChart;
  private final StringProperty examLabel;

  public TeacherAnalyticsViewModel(Model model){
    this.model = model;
    this.model.addListener(this);
    gradeChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    examLabel = new SimpleStringProperty("");
  }

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

  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
  }
  
  public void bindGradeChart(ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> property){
    property.bind(gradeChart);
  }

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
