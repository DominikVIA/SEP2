package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EditCourseViewModel implements PropertyChangeListener {
  private Course course;
  private final Model model;
  private final StringProperty code;
  private final StringProperty semester;
  private final StringProperty title;
  private final StringProperty description;
  private final StringProperty additionalTeacher;
  private final StringProperty student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

  public EditCourseViewModel(Model model){
    this.model = model;
    this.code = new SimpleStringProperty("");
    this.title = new SimpleStringProperty("");
    this.semester = new SimpleStringProperty("");
    this.description = new SimpleStringProperty("");
    this.additionalTeacher = new SimpleStringProperty("");
    this.student = new SimpleStringProperty("");
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.support = new PropertyChangeSupport(this);
  }

  public void reset() {
    if(course != null){
      code.set(course.getCode());
      semester.set(course.getSemester() + "");
      title.set(course.getTitle());
      description.set(course.getDescription());
      additionalTeacher.set(course.getTeacher(1) != null ? course.getTeacher(1).toString() : "");
      student.set("");
      studentsList.getValue().setAll(course.getStudents());
    }
  }

  public void bindCode(StringProperty property){
    property.bindBidirectional(code);
  }

  public void bindSemester(StringProperty property){
    property.bindBidirectional(semester);
  }

  public void bindTitle(StringProperty property){
    property.bindBidirectional(title);
  }

  public void bindDescription(StringProperty property){
    property.bindBidirectional(description);
  }

  public void bindAdditionalTeacher(StringProperty property){
    property.bindBidirectional(additionalTeacher);
  }

  public void bindStudent(StringProperty property){
    property.bindBidirectional(student);
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
    if(!evt.getPropertyName().contains("login"))
      support.firePropertyChange(evt);
  }
}
