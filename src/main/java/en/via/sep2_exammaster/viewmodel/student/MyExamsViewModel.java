package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyExamsViewModel {
  private final Model model;
  private final ObjectProperty<ObservableList<Exam>> upcoming;
  private final ObjectProperty<ObservableList<Exam>> completed;

  public MyExamsViewModel(Model model){
    this.model = model;
    upcoming = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    completed = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  public void viewExam(Exam exam){
    model.viewExamInfo(exam);
  }

  public void reset() throws IOException
  {
    upcoming.getValue().setAll();
    completed.getValue().setAll();
    ArrayList<Exam> exams = (ArrayList<Exam>) model.getExams();
    for(Exam temp : exams){
      if(temp.isCompleted()) completed.getValue().add(temp);
      else upcoming.getValue().add(temp);
    }

  }

  public void bindUpcoming(ObjectProperty<ObservableList<Exam>> property){
    property.bind(upcoming);
  }

  public void bindCompleted(ObjectProperty<ObservableList<Exam>> property){
    property.bind(completed);
  }
}
