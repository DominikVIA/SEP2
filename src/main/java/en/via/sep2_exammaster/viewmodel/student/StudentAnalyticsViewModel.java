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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The StudentAnalyticsViewModel class represents the view model for displaying
 * analytics regarding all of a student's exams' results. It interacts with the Model as a source of results
 * and provides properties for the pieChart (semester grade breakdown), lineChart (grade average of each semester),
 * semesterItems (list of semesters a student has completed to be input into a ChoiceBox)
 * and semesterChosen (the semester chosen in the ChoiceBox in the view).
 */
public class StudentAnalyticsViewModel {
  private final ArrayList<ArrayList<Grade>> semesterGrades;
  private final Model model;
  private final ObjectProperty<ObservableList<XYChart.Series<Number, Number>>> lineChart;
  private final ObjectProperty<ObservableList<PieChart.Data>> pieChart;
  private final ObjectProperty<ObservableList<String>> semesterItems;
  private final ObjectProperty<String> semesterChosen;

  /**
   * Constructs a StudentAnalyticsViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
  public StudentAnalyticsViewModel(Model model){
    this.model = model;
    semesterGrades = new ArrayList<>();
    lineChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    pieChart = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    semesterItems = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    semesterChosen = new SimpleObjectProperty<>();
  }

  /**
   * Resets the ViewModel and its ViewController by first clearing/emptying all the properties
   * and then setting them to gotten/calculated information (for example filling the pieChart with a semester's grades).
   */
  public void reset() {
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
          if(!grades.isEmpty()){
            semesterGrades.add((ArrayList<Grade>) grades.clone());
          }
          grades.clear();
          semesterCounter = semester;
          semesterItems.getValue().add(getStringFromNumber(semester));
        }

        grades.add(temp.getGrade());
      }
    }
    semesterGrades.add((ArrayList<Grade>) grades.clone());
    int counter = 1;
    for(ArrayList<Grade> temp : semesterGrades){
      lineChart.get().get(0).getData().add(new XYChart.Data<>(counter, gradeAverage(temp)));
      counter++;
    }
    pieChartGradesForSemester(semesterGrades.get(0));
  }

  /**
   * When a value in the semester ChoiceBox in the view is changed the new grades in the PieChart should be shown,
   * this method is responsible for ensuring the pieChart is updated to reflect the proper information.
   */
  public void pieChartUpdate(){
    pieChart.getValue().clear();
    int index = getNumberFromString(semesterChosen.get());
    pieChartGradesForSemester(semesterGrades.get(index));
  }

  /**
   * Private method for calculating the grade average from an ArrayList of grades.
   *
   * @param grades ArrayList of Grade objects for which to calculate an average
   * @return a grade average as a double
   */
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

  /**
   * Populates the pieChart with the values from an ArrayList of Grade objects.
   *
   * @param grades to be input into the pieChart
   */
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

  /**
   * Turns an int (from 1 to 9 inclusive) into a String representation (for example, 1 -> "First", 2 -> "Second" and so on).
   *
   * @param number int to be turned to a String representation
   * @return String representation of the input int
   */
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

  /**
   * Turns a String into an int to be used as an array index
   * (for example, "First" -> 0, "Second" -> 1 and so on, with "Ninth" being the last possible entry that will yield a proper answer).
   *
   * @param number String to be turned into an int
   * @return int representation of the input String
   */
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

  /**
   * Binds a property to the lineChart ObjectProperty for one-way accessing and managing of the lineChart XYChart.
   *
   * @param property the ObjectProperty value to which lineChart will be bound to
   */
  public void bindLineChart(ObjectProperty<ObservableList<XYChart.Series<Number, Number>>> property){
    property.bind(lineChart);
  }

  /**
   * Binds a property to the pieChart ObjectProperty for one-way accessing and managing of the pieChart PieChart.
   *
   * @param property the ObjectProperty value to which pieChart will be bound to
   */
  public void bindPieChart(ObjectProperty<ObservableList<PieChart.Data>> property){
    property.bind(pieChart);
  }

  /**
   * Binds a property to the semesterItems ObjectProperty for one-way
   * accessing and managing of the itemsProperty of the semester ChoiceBox in the view.
   *
   * @param property the ObjectProperty value to which semesterItems will be bound to
   */
  public void bindSemesterItems(ObjectProperty<ObservableList<String>> property){
    property.bind(semesterItems);
  }

  /**
   * Bidirectionally binds a property to the semesterChosen ObjectProperty for two-way
   * accessing and managing of the valueProperty of the semester ChoiceBox in the view.
   *
   * @param property the ObjectProperty value to which semesterItems will be bound to
   */
  public void bindSemesterChosen(ObjectProperty<String> property){
    property.bindBidirectional(semesterChosen);
  }
}
