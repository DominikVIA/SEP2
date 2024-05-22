package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentAnalyticsViewModel {
  private List<Result> resultList;
  private final Model model;
  private final ObjectProperty<ObservableList<XYChart.Series<Number, Number>>> lineChart;
  private final ObjectProperty<ObservableList<PieChart.Data>> pieChart;
  private final ObjectProperty<ObservableList<String>> semesterItems;
  private final ObjectProperty<String> semesterChosen;

  public StudentAnalyticsViewModel(Model model){
    this.model = model;
    lineChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    pieChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    semesterItems = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    semesterChosen = new SimpleObjectProperty<>();
  }

  public void reset()
  {
    semesterItems.getValue().setAll();
    lineChart.getValue().add(new XYChart.Series<>());
    resultList = model.getResults();
    ArrayList<Integer> semesters = new ArrayList<>();
    ArrayList<Grade> grades = new ArrayList<>();
      for(Result temp : resultList){
        int semester = temp.getExam().getCourse().getSemester();
        if(!temp.getGrade().equals(Grade.Null)) {
          if (!semesters.contains(semester)) {
            if(!grades.isEmpty()) lineChart.getValue().get(0).getData().add(new XYChart.Data<>(semester, gradeAverage(grades)));
            grades.clear();
            semesters.add(semester);
            semesterItems.getValue().add(getNumberWord(semester));
          }
          grades.add(temp.getGrade());
        }
      }
  }

  private double gradeAverage(ArrayList<Grade> grades){
    int counter = 0;
    double total = 0;
    for(Grade temp : grades){
      if(!temp.equals(Grade.Null) && !temp.equals(Grade.Sick))
      {
        counter++;
        total += temp.getGrade();
      }
    }
    return total / counter;
  }

//  private double pieChartGradesForSemester(ArrayList<Grade> grades){
//    int split = 100 / grades.size();
//    for(Grade temp : grades){
//      if(!temp.equals(Grade.Null)) {
//        counter++;
//        total += temp.getGrade();
//      }
//    }
//    return total / counter;
//  }

  private String getNumberWord(int number){
    return switch (number){
      case 1 -> "First";
      case 2 -> "Second";
      case 3 -> "Third";
      case 4 -> "Fourth";
      case 5 -> "Fifth";
      case 6 -> "Sixth";
      case 7 -> "Seventh";
      case 8 -> "Eighth";
      case 9 -> "Ninth";
      default -> "Error";
    };
  }

  public void bindLineChart(ObjectProperty<ObservableList<XYChart.Series<Number, Number>>> property){
    property.bind(lineChart);
  }

  public void bindPieChart(ObjectProperty<ObservableList<PieChart.Data>> property){
    property.bind(pieChart);
  }

  public void bindSemesterItems(ObjectProperty<ObservableList<String>> property){
    property.bind(semesterItems);
  }

  public void bindSemesterChosen(ObjectProperty<String> property){
    property.bindBidirectional(semesterChosen);
  }
}
