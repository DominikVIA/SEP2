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
  private final ObjectProperty<ObservableList<XYChart.Series<Grade, Integer>>> gradeChart;
  private final StringProperty examLabel;

  public TeacherAnalyticsViewModel(Model model){
    this.model = model;
    gradeChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    examLabel = new SimpleStringProperty("");
  }

  public void reset(){
    if(resultList != null){

    }
  }

  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
  }
  
  public void bindGradeChart(ObjectProperty<ObservableList<XYChart.Series<Grade, Integer>>> property){
    property.bind(gradeChart);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    try {
      if (evt.getPropertyName().equals("view analytics"))
        resultList = model.getResultsByExam((Exam) evt.getNewValue());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
