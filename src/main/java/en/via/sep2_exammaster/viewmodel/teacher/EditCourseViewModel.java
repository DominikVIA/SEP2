package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import javafx.application.Platform;
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

/**
 * The EditCourseViewModel class represents the view model for displaying and editing information about a given course.
 * It interacts with the Model in order to save the edited information
 * and provides properties for each of a course's value (such as title, semester, description, etc.).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class EditCourseViewModel implements PropertyChangeListener {
  private Course course;
  private final Model model;
  private final StringProperty code, semester, title, description, additionalTeacher, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;
  private ArrayList<Student> studentArrayList;

  /**
   * Constructs a EditCourseViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
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

  /**
   * Saves the newly input information about a course and displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O exception occurs while saving the information
   */
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

  /**
   * Adds a student to a course's enrolled list.
   */
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
  public void viewCourse(){
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
   * Resets this ViewModel and its ViewController by setting each property to the information of the editable course.
   */
  public void reset() {
    if(course != null){
      Platform.runLater(() -> {
        studentArrayList = course.getStudents();
        code.set(course.getCode());
        semester.set(course.getSemester() + "");
        title.set(course.getTitle());
        description.set(course.getDescription());
        additionalTeacher.set(course.getTeacher(1) != null ? course.getTeacher(1).getInitials() : "");
        student.set("");
        studentsList.getValue().setAll(course.getStudents());
      });
    }
  }

  /**
   * Binds a property to the code StringProperty for one-way accessing
   * and managing of the code field (not bidirectional since this value cannot be edited).
   *
   * @param property the StringProperty value to which code will be bound to
   */
  public void bindCode(StringProperty property){
    property.bind(code);
  }

  /**
   * Bidirectionally binds a property to the semester StringProperty for two-way accessing and managing of the semester field.
   *
   * @param property the StringProperty value to which semester will be bound to
   */
  public void bindSemester(StringProperty property){
    property.bindBidirectional(semester);
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
   * Bidirectionally binds a property to the description StringProperty for two-way accessing and managing of the description TextArea.
   *
   * @param property the StringProperty value to which description will be bound to
   */
  public void bindDescription(StringProperty property){
    property.bindBidirectional(description);
  }

  /**
   * Bidirectionally binds a property to the additionalTeacher StringProperty for two-way accessing and managing of the additionalTeacher field.
   *
   * @param property the StringProperty value to which additionalTeacher will be bound to
   */
  public void bindAdditionalTeacher(StringProperty property){
    property.bindBidirectional(additionalTeacher);
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
   * Bidirectionally binds a property to the studentsList ObjectProperty for two-way accessing and managing of the ListView of enrolled students.
   *
   * @param property the ObjectProperty value to which studentsList will be bound to
   */
  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bindBidirectional(studentsList);
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
    if (evt.getPropertyName().equals("edit course") || evt.getPropertyName().equals("course edit success")) {
      course = (Course) evt.getNewValue();
      reset();
    }
    support.firePropertyChange(evt);
  }
}
