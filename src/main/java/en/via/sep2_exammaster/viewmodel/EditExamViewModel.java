package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Examiners;
import en.via.sep2_exammaster.shared.Student;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EditExamViewModel implements PropertyChangeListener {
  private Exam exam;
  private ArrayList<Student> studentArrayList;
  private final Model model;
  private final StringProperty room, title, content, time, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final ObjectProperty<LocalDate> date;
  private final ObjectProperty<String> examiner, type;
  private final PropertyChangeSupport support;

  public EditExamViewModel(Model model){
    this.model = model;
    this.model.addListener(this);

    this.room = new SimpleStringProperty("");
    this.title = new SimpleStringProperty("");
    this.content = new SimpleStringProperty("");
    this.time = new SimpleStringProperty("");
    this.student = new SimpleStringProperty("");

    this.examiner = new SimpleObjectProperty<>();
    this.type = new SimpleObjectProperty<>();
    this.studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    this.date = new SimpleObjectProperty<>();

    this.support = new PropertyChangeSupport(this);
    this.studentArrayList = new ArrayList<>();
  }

  public void reset(){
    if(exam != null){
      title.set(exam.getTitle());
      room.set(exam.getRoom());
      content.set(exam.getContent());
      time.set(exam.getTime().toString());
      student.set("");
      studentsList.getValue().setAll(exam.getStudents());
      studentArrayList = exam.getStudents();
      date.set(exam.getDate());
    }
  }

  public void onSave() throws IOException {
    try
    {
      String title = this.title.getValue();
      System.out.println(title);
      String room = this.room.getValue();
      String content = this.content.getValue();
      LocalTime time = LocalTime.parse(this.time.getValue());
      LocalDate date = this.date.getValue();
      boolean type = this.type.getValue().equals("Written");
      Examiners examiners = Examiners.valueOf(this.examiner.getValue());

      if (title.isBlank() || room.isBlank() || content.isBlank()) {
        support.firePropertyChange("information blank", null, "error");
        return;
      }

      if(studentArrayList.isEmpty()){
        support.firePropertyChange("no students", null, "error");
        return;
      }

      model.editExam(exam.getId(), title, content, room, exam.getCourse(), date, time, type, examiners, studentArrayList);
    }
    catch (DateTimeParseException e){
      support.firePropertyChange("time parsing error", null, false);
    }
  }

  public void addStudent(){
    try {
      Student temp = model.getStudent(Integer.parseInt(student.getValue()));
      if(temp != null) {
        if(studentArrayList.contains(temp)){
          support.firePropertyChange("student already enrolled", null, student);
          return;
        }
        if(!exam.getCourse().getStudents().contains(temp)){
          support.firePropertyChange("student not in course", null, student);
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

  public void viewExamInfo(){
    model.viewExamInfo(exam);
  }

  public void bindTitle(StringProperty property){
    property.bindBidirectional(title);
  }

  public void bindRoom(StringProperty property){
    property.bindBidirectional(room);
  }

  public void bindContent(StringProperty property){
    property.bindBidirectional(content);
  }

  public void bindTime(StringProperty property){
    property.bindBidirectional(time);
  }

  public void bindStudent(StringProperty property){
    property.bindBidirectional(student);
  }

  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bindBidirectional(studentsList);
  }

  public void bindExaminer(ObjectProperty<String> property){
    property.bindBidirectional(examiner);
  }

  public void bindType(ObjectProperty<String> property){
    property.bindBidirectional(type);
  }

  public void bindDate(ObjectProperty<LocalDate> property){
    property.bindBidirectional(date);
  }

  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().equals("edit exam")) {
        this.exam = (Exam) evt.getNewValue();
        reset();
      }
    if(evt.getPropertyName().equals("exam edit success")){
      this.exam = (Exam) evt.getNewValue();
    }
    support.firePropertyChange(evt);
  }

}
