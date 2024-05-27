package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Course;
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
import java.util.ArrayList;

/**
 * The CreateCourseViewModel class represents the view model for creating a new course.
 * It interacts with the Model in order to save the input information
 * and provides properties for each of a course's value (such as title, semester, description, etc.).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class CreateCourseViewModel implements PropertyChangeListener {
  private final Model model;
  private final StringProperty code, semester, title, description, additionalTeacher, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final ArrayList<Student> studentArrayList;
  private final PropertyChangeSupport support;

  /**
   * Constructs a CreateCourseViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
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

  /**
   * Resets the ViewModel and its ViewController by setting all the properties to empty Strings or clearing them (if such a method is allowed).
   */
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

  /**
   * Creates a new course using the information provided by the user in the view. Displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O error occurs while creating a new course
   */
  public void createCourse() throws IOException {
    try {
      String code = this.code.get();
      int semester = Integer.parseInt(this.semester.get());
      String title = this.title.get();
      String description = this.description.get();
      String additionalTeacherInitials = this.additionalTeacher.getValue();

      if(code.isBlank() || title.isBlank() || description.isBlank()){
        support.firePropertyChange("information blank", null, "error");
        return;
      }
      if(semester < 1 || semester > 9){
        support.firePropertyChange("semester number error", null, semester);
        return;
      }

      model.createCourse(code, semester, title, description, additionalTeacherInitials, studentArrayList);
    }
    catch(NumberFormatException e){
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
   * Bidirectionally binds a property to the code StringProperty for two-way accessing and managing of the code field.
   *
   * @param property the StringProperty value to which code will be bound to
   */
  public void bindCode(StringProperty property){
    property.bindBidirectional(code);
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
   * Bidirectionally binds a property to the studentsList ObjectProperty for two-way accessing and managing of the ListView of students.
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
    support.firePropertyChange(evt);
  }
}
