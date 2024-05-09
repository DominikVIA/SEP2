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
import java.io.IOException;

public class CourseInfoViewModel implements PropertyChangeListener {
  private Course course;
  private final Model model;
  private final StringProperty code;
  private final IntegerProperty semester;
  private final StringProperty title;
  private final StringProperty description;
  private final ObjectProperty<ObservableList<Exam>> examsList;
  private final ObjectProperty<ObservableList<Student>> studentsList;

  public CourseInfoViewModel(Model model){
    this.model = model;
    this.code = new SimpleStringProperty("");
    this.semester = new SimpleIntegerProperty();
    this.title = new SimpleStringProperty("");
    this.description = new SimpleStringProperty("");
    this.examsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.model.addListener(this);
  }

  public void reset() {
    if(course != null){
      code.set(course.getCode());
      semester.set(course.getSemester());
      title.set(course.getTitle());
      description.set(course.getDescription());
      studentsList.getValue().setAll(course.getStudents());
      examsList.getValue().setAll(course.getExams());
    }
  }

  public void bindCode(StringProperty property){
    property.bind(code);
  }

  public void bindSemester(StringProperty property){
    StringConverter<Number> converter = new NumberStringConverter();
    IntegerProperty ip = new SimpleIntegerProperty();
    Bindings.bindBidirectional(property, ip, converter);
    property.set(property.getValue());
    ip.bindBidirectional(semester);
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

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().equals("view course")){
      course = (Course) evt.getNewValue();
      reset();
    }
  }
}
