package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class MyExamsViewModel {
  private final Model model;
  private final ObjectProperty<ObservableList<Exam>> exams;

  public MyExamsViewModel(Model model){
    this.model = model;
    exams = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  public void viewExam(Exam exam){
    model.viewExamInfo(exam);
  }

  public void reset() throws IOException
  {
    exams.getValue().setAll(model.getExams());
  }

  public void bindExam(ObjectProperty<ObservableList<Exam>> property){
    property.bind(exams);
  }
}
