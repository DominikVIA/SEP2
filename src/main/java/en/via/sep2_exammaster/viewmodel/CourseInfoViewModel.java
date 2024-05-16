package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class CourseInfoViewModel implements PropertyChangeListener {
  private Course course;
  private final Model model;
  private final StringProperty code, semester, title, description;
  private final ObjectProperty<ObservableList<Exam>> examsList;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

  public CourseInfoViewModel(Model model){
    this.model = model;
    this.code = new SimpleStringProperty("");
    this.semester = new SimpleStringProperty("");
    this.title = new SimpleStringProperty("");
    this.description = new SimpleStringProperty("");
    this.examsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.support = new PropertyChangeSupport(this);
    this.model.addListener(this);
  }

  public void reset() {
    if(course != null){
      code.set(course.getCode());
      semester.set(course.getSemester() + "");
      title.set(course.getTitle());
      description.set(course.getDescription());
      studentsList.getValue().setAll(course.getStudents());
      examsList.getValue().setAll(course.getExams());
    }
  }

  public void onCreateExam(){
    model.viewCreateExam(course);
  }

  public void onDelete() throws IOException {
    model.deleteCourse(course.getCode());
  }

  public void onEdit() {
    model.viewEditCourse(course);
  }

  public void onViewExam(Exam exam){
    model.viewExamInfo(exam);
  }

  public void bindCode(StringProperty property){
    property.bind(code);
  }

  public void bindSemester(StringProperty property){
    property.bind(semester);
  }

  public void bindTitle(StringProperty property){
    property.bind(title);
  }

  public void bindDescription(StringProperty property){
    property.bind(description);
  }

  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bind(studentsList);
  }

  public void bindExams(ObjectProperty<ObservableList<Exam>> property){
    property.bind(examsList);
  }

  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "view course" -> {
        course = (Course) evt.getNewValue();
        reset();
      }
      case "view exam", "create exam" -> {
        support.firePropertyChange(evt);
      }
    }
  }
}
