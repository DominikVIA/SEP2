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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StudentAnalyticsViewModel {
  private ArrayList<ArrayList<Grade>> semesterGrades;
  private final Model model;
  private final ObjectProperty<ObservableList<XYChart.Series<Number, Number>>> lineChart;
  private final ObjectProperty<ObservableList<PieChart.Data>> pieChart;
  private final ObjectProperty<ObservableList<String>> semesterItems;
  private final ObjectProperty<String> semesterChosen;

  public StudentAnalyticsViewModel(Model model){
    this.model = model;
    semesterGrades = new ArrayList<>();
    lineChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    pieChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    semesterItems = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    semesterChosen = new SimpleObjectProperty<>();
  }

  public void reset()
  {
    semesterItems.getValue().setAll();
    pieChart.getValue().setAll();
    semesterGrades.clear();
    lineChart.getValue().add(new XYChart.Series<>());

    List<Result> resultList = model.getResults();
    int semesterCounter = 0;
    ArrayList<Grade> grades = new ArrayList<>();

    for(Result temp : resultList){
      int semester = temp.getExam().getCourse().getSemester();
      if(!temp.getGrade().equals(Grade.Null)) {
        if (semesterCounter < semester) {
          semesterGrades.add(grades);
          grades.clear();
          semesterCounter = semester;
          semesterItems.getValue().add(getStringFromNumber(semester));
        }
        grades.add(temp.getGrade());
      }
    }

    int counter = 1;
    for(ArrayList<Grade> temp : semesterGrades){
      lineChart.get().get(0).getData().add(new XYChart.Data<>(counter, gradeAverage(temp)));
      counter++;
    }
    pieChartGradesForSemester(semesterGrades.get(0));
  }

  public void pieChartUpdate(){
    pieChart.getValue().clear();
    int index = getNumberFromString(semesterChosen.get());
    pieChartGradesForSemester(semesterGrades.get(index));
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

  private void pieChartGradesForSemester(ArrayList<Grade> grades){
    int split = 100 / grades.size();
    int[] gradeCounter = new int[8];
    for(Grade temp : grades){
      switch (temp.getGrade()){
        case 12 -> gradeCounter[0]++;
        case 10 -> gradeCounter[1]++;
        case 7 -> gradeCounter[2]++;
        case 4 -> gradeCounter[3]++;
        case 2 -> gradeCounter[4]++;
        case 0 -> gradeCounter[5]++;
        case -3 -> gradeCounter[6]++;
        case -2 -> gradeCounter[7]++;
      }
    }
    gradeCounter = Arrays.stream(gradeCounter).map(number -> number * split).toArray();
    pieChart.getValue().add(new PieChart.Data(Grade.A.toString(), gradeCounter[0]));
    pieChart.getValue().add(new PieChart.Data(Grade.B.toString(), gradeCounter[1]));
    pieChart.getValue().add(new PieChart.Data(Grade.C.toString(), gradeCounter[2]));
    pieChart.getValue().add(new PieChart.Data(Grade.D.toString(), gradeCounter[3]));
    pieChart.getValue().add(new PieChart.Data(Grade.E.toString(), gradeCounter[4]));
    pieChart.getValue().add(new PieChart.Data(Grade.Fx.toString(), gradeCounter[5]));
    pieChart.getValue().add(new PieChart.Data(Grade.F.toString(), gradeCounter[6]));
    pieChart.getValue().add(new PieChart.Data(Grade.Sick.toString(), gradeCounter[7]));
  }

  private String getStringFromNumber(int number){
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

  private int getNumberFromString(String number){
    return switch (number){
      case "First" -> 0;
      case "Second" -> 1;
      case "Third" -> 2;
      case "Fourth" -> 3;
      case "Fifth" -> 4;
      case "Sixth" -> 5;
      case "Seventh" -> 6;
      case "Eighth" -> 7;
      case "Ninth" -> 8;
      default -> -1;
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
