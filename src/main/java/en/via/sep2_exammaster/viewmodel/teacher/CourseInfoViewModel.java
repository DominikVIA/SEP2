package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
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

/**
 * The CourseInfoViewModel class represents the view model for displaying information about a course.
 * It interacts with the Model in order to send information to other views
 * and provides a property for each value of a course (such as code, title, semester, etc.).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class CourseInfoViewModel implements PropertyChangeListener {
  private Course course;
  private final Model model;
  private final StringProperty code, semester, title, description;
  private final ObjectProperty<ObservableList<Exam>> examsList;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

  /**
   * Constructs a CourseInfoViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
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

  /**
   * Resets this ViewModel and its ViewController by setting each property to the information of the viewed course.
   */
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

  /**
   * Opens a view for creating courses.
   *
   * @throws IOException if an I/O exception occurs while opening the view
   */
  public void onCreateExam() throws IOException {
    model.viewCourseRelated(course, ModelManager.CREATE_EXAM);
  }

  /**
   * Deletes the course that's being displayed.
   *
   * @throws IOException if an I/O exception occurs while deleting the course
   */
  public void onDelete() throws IOException {
    model.viewCourseRelated(course, ModelManager.DELETE_COURSE);
  }

  /**
   * Opens a view for editing a selected course.
   *
   * @throws IOException if an I/O exception occurs while opening the view
   */
  public void onEdit() throws IOException {
    model.viewCourseRelated(course, ModelManager.EDIT_COURSE);
  }

  /**
   * Opens a view for viewing a selected exam's information.
   *
   * @throws IOException if an I/O exception occurs while opening the view
   */
  public void onViewExam(Exam exam) throws IOException
  {
    model.viewExamRelated(exam, ModelManager.VIEW_EXAM);
  }

  /**
   * Opens a view for viewing exam analytics.
   *
   * @throws IOException if an I/O exception occurs while opening the view
   */
  public void onViewAnalytics(Exam exam) throws IOException
  {
    model.viewExamRelated(exam, ModelManager.VIEW_ANALYTICS);
  }

  /**
   * Binds a property to the code StringProperty for one-way accessing and managing of the code field
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which code will be bound to
   */
  public void bindCode(StringProperty property){
    property.bind(code);
  }

  /**
   * Binds a property to the semester StringProperty for one-way accessing and managing of the semester field
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which semester will be bound to
   */
  public void bindSemester(StringProperty property){
    property.bind(semester);
  }

  /**
   * Binds a property to the title StringProperty for one-way accessing and managing of the title field
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which title will be bound to
   */
  public void bindTitle(StringProperty property){
    property.bind(title);
  }

  /**
   * Binds a property to the description StringProperty for one-way accessing and managing of the description TextArea
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which description will be bound to
   */
  public void bindDescription(StringProperty property){
    property.bind(description);
  }

  /**
   * Binds a property to the studentsList ObjectProperty for one-way accessing and managing of the ListView of enrolled students
   * (one-way since it cannot be edited by the user).
   *
   * @param property the ObjectProperty value to which studentsList will be bound to
   */
  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bind(studentsList);
  }

  /**
   * Binds a property to the examsList ObjectProperty for one-way accessing and managing of the ListView of a course's exams
   * (one-way since it cannot be edited by the user).
   *
   * @param property the ObjectProperty value to which examsList will be bound to
   */
  public void bindExams(ObjectProperty<ObservableList<Exam>> property){
    property.bind(examsList);
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
    switch (evt.getPropertyName()){
      case "view course" -> {
        course = (Course) evt.getNewValue();
        reset();
      }
      case "view exam", "create exam", "view analytics" -> support.firePropertyChange(evt);
    }
  }
}
