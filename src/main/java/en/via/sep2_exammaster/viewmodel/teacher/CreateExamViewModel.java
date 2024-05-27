package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Examiners;
import en.via.sep2_exammaster.shared.Student;
import javafx.beans.property.*;
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

/**
 * The CreateExamViewModel class represents the view model for creating a new exam.
 * It interacts with the Model in order to save the input information
 * and provides properties for each of an exam's value (such as title, room, content, etc.).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class CreateExamViewModel implements PropertyChangeListener {
  private Course course;
  private final ArrayList<Student> studentArrayList;
  private final Model model;
  private final StringProperty room, title, content, time, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final ObjectProperty<LocalDate> date;
  private final ObjectProperty<String> examiner, type;
  private final PropertyChangeSupport support;

  /**
   * Constructs a CreateExamViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
  public CreateExamViewModel(Model model){
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

  /**
   * Creates a new exam using the information provided by the user in the view. Displays appropriate errors if necessary.
   * 
   * @throws IOException if an I/O error occurs while creating a new exam
   */
  public void onCreate() throws IOException {
    try
    {
      String title = this.title.getValue();
      String room = this.room.getValue();
      String content = this.content.getValue();
      LocalTime time = LocalTime.parse(this.time.getValue());
      LocalDate date = this.date.getValue();
      boolean type = this.type.getValue().equals("Written");
      Examiners examiners = Examiners.valueOf(this.examiner.getValue());

      if (title.isBlank() || room.isBlank() || content.isBlank())
      {
        support.firePropertyChange("information blank", null, "error");
        return;
      }
      if(studentArrayList.isEmpty()){
        support.firePropertyChange("no students", null, "error");
        return;
      }

      model.createExam(title, content, room, course, date, time, type,
          examiners, studentArrayList);
    }
    catch (DateTimeParseException e){
      support.firePropertyChange("time parsing error", null, false);
    }
  }

  /**
   * Adds a student to an exam's participation list.
   */
  public void addStudent(){
    try {
      Student temp = model.getStudent(Integer.parseInt(student.getValue()));
      if(temp != null) {
        if(studentArrayList.contains(temp)){
          support.firePropertyChange("student already enrolled", null, student);
          return;
        }
        if(!course.getStudents().contains(temp)){
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

  /**
   * Adds all students enrolled in a course to the participation list of the newly created exam.
   */
  public void addAllStudents(){
    for(Student temp : course.getStudents()) {
      studentArrayList.add(temp);
      studentsList.getValue().add(temp);
      student.set("");
    }
  }

  /**
   * Removes a student from a course's enrolled list.
   *
   * @param student student to be removed from course
   */
  public void remove(Student student){
    studentArrayList.remove(student);
    studentsList.getValue().remove(student);
  }

  /**
   * Views information about the Course instance of this object.
   */
  public void viewCourse(Course course){
    try
    {
      model.viewCourseRelated(course, ModelManager.VIEW_COURSE);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /** 
   * Resets the ViewModel and its ViewController by setting all the properties to empty Strings, clearing them or setting to today's date.
   */
  public void reset(){
    title.set("");
    room.set("");
    content.set("");
    time.set("");
    student.set("");
    studentsList.getValue().setAll();
    studentArrayList.clear();
    date.set(LocalDate.now());
  }

  /**
   * Bidirectionally binds a property to the title StringProperty for two-way accessing and managing of the title field.
   *
   * @param property the StringProperty value to which title will be bound to
   */
  public void bindTitle(StringProperty property){
    property.bindBidirectional(title);
  }

  /**
   * Bidirectionally binds a property to the room StringProperty for two-way accessing and managing of the room field.
   *
   * @param property the StringProperty value to which room will be bound to
   */
  public void bindRoom(StringProperty property){
    property.bindBidirectional(room);
  }

  /**
   * Bidirectionally binds a property to the content StringProperty for two-way accessing and managing of the content TextArea.
   *
   * @param property the StringProperty value to which content will be bound to
   */
  public void bindContent(StringProperty property){
    property.bindBidirectional(content);
  }

  /**
   * Bidirectionally binds a property to the time StringProperty for two-way accessing and managing of the time field.
   *
   * @param property the StringProperty value to which time will be bound to
   */
  public void bindTime(StringProperty property){
    property.bindBidirectional(time);
  }

  /**
   * Bidirectionally binds a property to the student StringProperty for two-way accessing and managing of the student field.
   *
   * @param property the StringProperty value to which student will be bound to
   */
  public void bindStudent(StringProperty property){
    property.bindBidirectional(student);
  }

  /**
   * Bidirectionally binds a property to the studentsList ObjectProperty for two-way accessing and managing of the ListView of students.
   *
   * @param property the ObjectProperty value to which studentsList will be bound to
   */
  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bindBidirectional(studentsList);
  }

  /**
   * Bidirectionally binds a property to the examiners ObjectProperty for two-way accessing and managing of the examiner ChoiceBox.
   *
   * @param property the ObjectProperty value to which examiner will be bound to
   */
  public void bindExaminer(ObjectProperty<String> property){
    property.bindBidirectional(examiner);
  }

  /**
   * Bidirectionally binds a property to the type ObjectProperty for two-way accessing and managing of the type ChoiceBox.
   *
   * @param property the ObjectProperty value to which type will be bound to
   */
  public void bindType(ObjectProperty<String> property){
    property.bindBidirectional(type);
  }

  /**
   * Bidirectionally binds a property to the date ObjectProperty for two-way accessing and managing of the date DatePicker.
   *
   * @param property the ObjectProperty value to which date will be bound to
   */
  public void bindDate(ObjectProperty<LocalDate> property){
    property.bindBidirectional(date);
  }

  /**
   * Adds a PropertyChangeListener to listen for property change events.
   *
   * @param listener the PropertyChangeListener to add
   */
  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a PropertyChangeListener from listening for property change events.
   *
   * @param listener the PropertyChangeListener to remove
   */
  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  /**
   * Handles property change events fired by its subjects (ModelManager).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("create exam"))
    {
      this.course = (Course) evt.getNewValue();
      reset();
    }
    support.firePropertyChange(evt);
  }
}
