package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
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

/**
 * The EditExamViewModel class represents the view model for displaying and editing information about a given exam.
 * It interacts with the Model in order to save the edited information
 * and provides properties for an exam's each accessible value (such as title, room, content, etc.).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class EditExamViewModel implements PropertyChangeListener {
  private Exam exam;
  private ArrayList<Student> studentArrayList;
  private final Model model;
  private final StringProperty room, title, content, time, student;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final ObjectProperty<LocalDate> date;
  private final ObjectProperty<String> examiner, type;
  private final PropertyChangeSupport support;

  /**
   * Constructs a EditExamViewModel with the given model.
   * 
   * @param model the model for communication with the server
   */
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

  /**
   * Resets the ViewModel and the ViewController by setting each property to the information of the editable exam.
   */
  public void reset(){
    if(exam != null){
      title.set(exam.getTitle());
      room.set(exam.getRoom());
      content.set(exam.getContent());
      time.set(exam.getTime().toString());
      examiner.set(exam.getExaminers().name());
      type.set((exam.isWritten() ? "Written" : "Oral"));
      student.set("");
      studentsList.getValue().setAll(exam.getStudents());
      studentArrayList = exam.getStudents();
      date.set(exam.getDate());
    }
  }

  /**
   * Saves the newly input information about an exam and displays appropriate errors if necessary.
   * 
   * @throws IOException if an I/O exception occurs while saving the information
   */
  public void onSave() throws IOException {
    try
    {
      String title = this.title.getValue();
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

  /**
   * Removes a student from an exam's participation list.
   * 
   * @param student student to be removed from exam
   */
  public void remove(Student student){
    studentArrayList.remove(student);
    studentsList.getValue().remove(student);
  }

  /**
   * Views information about the Exam instance of this object.
   */
  public void viewExamInfo(){
    try
    {
      model.viewExamRelated(exam, ModelManager.VIEW_EXAM);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
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
    if(evt.getPropertyName().equals("edit exam") || evt.getPropertyName().equals("exam edit success") ) {
        this.exam = (Exam) evt.getNewValue();
        reset();
      }
    support.firePropertyChange(evt);
  }

}
