package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Result;
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
 * The AddResultsViewModel class represents the view model for displaying and editing information about students' exam results.
 * It interacts with the Model in order to save input information
 * and provides a property for each value of an exam's result (such as the grade, feedback, a list of students in the exam).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class AddResultsViewModel implements PropertyChangeListener {
  private Exam exam;
  private final Model model;
  private final StringProperty feedback;
  private final ObjectProperty<Grade> grade;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

  /**
   * Constructs a AnnouncementInfoViewModelTeacher with the given model.
   *
   * @param model the model for communication with the server
   */
  public AddResultsViewModel(Model model){
    this.model = model;
    model.addListener(this);
    grade = new SimpleObjectProperty<>();
    feedback = new SimpleStringProperty("");
    studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    support = new PropertyChangeSupport(this);
  }

  /**
   * Saves the input information about a selected student's exam result.
   *
   * @param student student whose exam result information is to be saved
   * @throws IOException if an I/O exception occurs while saving the information
   */
  public void saveInformation(Student student) throws IOException {
    Grade grade = this.grade.get();
    String feedback = this.feedback.get();
    model.editResult(student, exam, grade, feedback);
  }

  /**
   * Gets information about a selected student's result from the model.
   *
   * @param student student whose result information is to be received
   * @throws IOException if an I/O error occurs while getting the information
   */
  public void studentClicked(Student student) throws IOException
  {
    Result temp = model.getStudentExamResult(exam, student);
    if(temp.getGrade().getGrade() != -2) grade.set(temp.getGrade());
    else grade.set(Grade.Null);
    if(temp.getFeedback() != null) feedback.set(temp.getFeedback());
    else feedback.set("");
  }

  /**
   * Resets the ViewModel and its ViewController by filling the studentsList with all students enrolled in a given exam
   * and ensuring the first student of the list is selected.
   *
   * @throws IOException if an I/O exception occurs while resetting the ViewModel
   */
  public void reset() throws IOException {
    if(exam != null){
      studentsList.getValue().setAll(exam.getStudents());
      studentClicked(exam.getStudents().get(0));
    }
  }

  /**
   * Bidirectionally binds a property to the grade ObjectProperty for two-way accessing and managing of the grade ChoiceBox.
   *
   * @param property the ObjectProperty value to which grade will be bound to
   */
  public void bindGrade(ObjectProperty<Grade> property){
    property.bindBidirectional(grade);
  }

  /**
   * Bidirectionally binds a property to the feedback StringProperty for two-way accessing and managing of the feedback TextArea.
   *
   * @param property the StringProperty value to which feedback will be bound to
   */
  public void bindFeedback(StringProperty property){
    property.bindBidirectional(feedback);
  }

  /**
   * Bidirectionally binds a property to the feedback StringProperty for two-way accessing and managing of the feedback TextArea.
   *
   * @param property the StringProperty value to which feedback will be bound to
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
    if(evt.getPropertyName().equals("add results")){
      exam = (Exam) evt.getNewValue();
      try {
        reset();
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    else if(evt.getPropertyName().equals("result edit success")){
      grade.set(((Result) evt.getNewValue()).getGrade());
      feedback.set(((Result) evt.getNewValue()).getFeedback());
    }
  }
}
