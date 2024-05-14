package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class EditCourseViewModel implements PropertyChangeListener {
  private Course course;
  private final Model model;
  private final StringProperty code, semester, title, description, additionalTeacher, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;
  private ArrayList<Student> studentArrayList;

  public EditCourseViewModel(Model model){
    this.model = model;
    this.model.addListener(this);
    this.code = new SimpleStringProperty("");
    this.title = new SimpleStringProperty("");
    this.semester = new SimpleStringProperty("");
    this.description = new SimpleStringProperty("");
    this.additionalTeacher = new SimpleStringProperty("");
    this.student = new SimpleStringProperty("");
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.studentArrayList = new ArrayList<>();
    this.support = new PropertyChangeSupport(this);
  }

  public void onSave() throws IOException {
    try{
      String code = this.code.getValue();
      int semester = Integer.parseInt(this.semester.getValue());
      String title = this.title.getValue();
      String description = this.description.getValue();
      String additionalTeacherInitials = this.additionalTeacher.getValue();
      model.editCourse(code, semester, title, description, additionalTeacherInitials, studentArrayList);
    }
    catch (NumberFormatException e){
      support.firePropertyChange("semester error", null, semester);
    }

  }

  public void addStudent(){
    try {
      Student temp = model.getStudent(Integer.parseInt(student.getValue()));
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

  public void viewCourse(){
    model.viewCourse(course);
  }

  public void reset() {
    if(course != null){
      Platform.runLater(() -> {
        studentArrayList = course.getStudents();
        code.set(course.getCode());
        semester.set(course.getSemester() + "");
        title.set(course.getTitle());
        description.set(course.getDescription());
        additionalTeacher.set(course.getTeacher(1) != null ? course.getTeacher(1).toString() : "");
        student.set("");
        studentsList.getValue().setAll(course.getStudents());
      });
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
    if(!evt.getPropertyName().contains("login")) {
      if(evt.getPropertyName().equals("edit course") || evt.getPropertyName().equals("edit success")){
        course = (Course) evt.getNewValue();
        reset();
      }
      support.firePropertyChange(evt);
    }
  }
}
