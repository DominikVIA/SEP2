package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;
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
import java.util.ArrayList;
import java.util.List;

public class CreateCourseViewModel implements PropertyChangeListener {
  private final ArrayList<Student> studentArrayList;
  private final Model model;
  private final StringProperty code, semester, title, description, additionalTeacher, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

  public CreateCourseViewModel(Model model){
    this.studentArrayList = new ArrayList<>();
    this.model = model;
    this.code = new SimpleStringProperty("");
    this.semester = new SimpleStringProperty("");
    this.title = new SimpleStringProperty("");
    this.description = new SimpleStringProperty("");
    this.additionalTeacher = new SimpleStringProperty("");
    this.student = new SimpleStringProperty("");
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.model.addListener(this);
    this.support = new PropertyChangeSupport(this);
  }

  public void reset(){
    code.set("");
    semester.set("");
    title.set("");
    description.set("");
    additionalTeacher.set("");
    student.set("");
    studentsList.getValue().setAll();
    studentArrayList.clear();
  }

  public void createCourse() throws IOException {
    try {
      String code = this.code.get();
      int semester = Integer.parseInt(this.semester.get());
      String title = this.title.get();
      String description = this.description.get();
      String additionalTeacherInitials = this.additionalTeacher.getValue();
      model.createCourse(code, semester, title, description, additionalTeacherInitials, studentArrayList);
    }
    catch(NumberFormatException e){
      support.firePropertyChange("semester error", null, semester);
    }
  }

  public void addStudent(){
    try {
      Student temp = model.getStudent(Integer.parseInt(student.getValue()));
      System.out.println(student.getValue());
      if(temp != null) {
        if(studentArrayList.contains(temp)){
          support.firePropertyChange("student adding error", null, student);
          return;
        }
        studentArrayList.add(temp);
        studentsList.getValue().add(temp);
        student.set("");
      }
    }
    catch (NumberFormatException e){
      support.firePropertyChange("student parsing error", null, student);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void remove(Student student){
    studentArrayList.remove(student);
    studentsList.getValue().remove(student);
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
