package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class AddResultsViewModel implements PropertyChangeListener {
  private Exam exam;
  private final Model model;
  private final StringProperty feedback;
  private final ObjectProperty<Grade> grade;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

  public AddResultsViewModel(Model model){
    this.model = model;
    model.addListener(this);
    grade = new SimpleObjectProperty<>();
    feedback = new SimpleStringProperty("");
    studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    support = new PropertyChangeSupport(this);

  }

  public void saveInformation(Student student) throws IOException {
    Grade grade = this.grade.get();
    String feedback = this.feedback.get();
    model.editResult(student, exam, grade, feedback);
  }

  public void studentClicked(Student student) throws IOException
  {
    Result temp = model.getStudentExamResult(exam, student);
    if(temp.getGrade().getGrade() != -2) grade.set(temp.getGrade());
    else grade.set(Grade.Null);
    if(temp.getFeedback() != null) feedback.set(temp.getFeedback());
    else feedback.set("");
  }

  public void reset() throws IOException {
    if(exam != null){
      studentsList.getValue().setAll(exam.getStudents());
      studentClicked(exam.getStudents().get(0));
    }
  }

  public void bindGrade(ObjectProperty<Grade> property){
    property.bindBidirectional(grade);
  }

  public void bindFeedback(StringProperty property){
    property.bindBidirectional(feedback);
  }

  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bindBidirectional(studentsList);
  }

  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().equals("add results")){
      exam = (Exam) evt.getNewValue();
      try
      {
        reset();
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    else if(evt.getPropertyName().equals("result edit success")){
      grade.set(((Result) evt.getNewValue()).getGrade());
      feedback.set(((Result) evt.getNewValue()).getFeedback());
    }
  }
}
