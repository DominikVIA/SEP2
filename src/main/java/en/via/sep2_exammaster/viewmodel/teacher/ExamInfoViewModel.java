package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Announcement;
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
 * The ExamInfoViewModel class represents the view model for displaying information about a given exam.
 * It interacts with the Model in order to send information to be used in other views
 * and provides properties for the grade chart and a title within the view containing the name of the exam.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class ExamInfoViewModel implements PropertyChangeListener {
  /**
   * The exam to be used as a source of exam information.
   */
  private Exam exam;
  /**
   * Instance of the Model class to allow communication with the server.
   */
  private final Model model;
  /**
   * StringProperties responsible for handling different pieces of an exam's information, such as:
   * title, room, content, time, date, type (written or oral), examiner (internal, external, both).
   */
  private final StringProperty title, room, content, time, type, examiner, date;
  /**
   * ObjectProperty used to display a list announcements related to a given exam.
   */
  private final ObjectProperty<ObservableList<Announcement>> announcementsList;
  /**
   * ObjectProperty used to display a list of students enrolled in a given exam.
   */
  private final ObjectProperty<ObservableList<Student>> studentsList;
  /**
   * PropertyChangeSupport for firing events to communicate with this object's listeners.
   */
  private final PropertyChangeSupport support;

  /**
   * Constructs a ExamInfoViewModel with the given model.
   *
   * @param model the model for getting all of a teacher's courses
   */
  public ExamInfoViewModel(Model model){
    this.model = model;
    this.model.addListener(this);
    title = new SimpleStringProperty("");
    room = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    time = new SimpleStringProperty("");
    type = new SimpleStringProperty("");
    examiner = new SimpleStringProperty("");
    date = new SimpleStringProperty("");
    announcementsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    support = new PropertyChangeSupport(this);
  }

  /**
   * Resets the viewModel by setting all of this view model's properties
   * to contain information read from a given exam.
   */
  public void reset(){
    if(exam != null){
      title.set(exam.getTitle());
      room.set(exam.getRoom());
      content.set(exam.getContent());
      time.set(exam.getTime().toString());
      date.set(exam.getDate().toString());
      type.set((exam.isWritten() ? "Written" : "Oral"));
      examiner.set(exam.getExaminers().name());
      announcementsList.getValue().setAll(exam.getAnnouncements());
      studentsList.getValue().setAll(exam.getStudents());
    }
  }

  /**
   * Marks the given exam as completed.
   *
   * @throws IOException if an I/O exception occurs while marking an exam as completed
   */
  public void markExamCompleted() throws IOException {
    model.markExamCompleted(exam);
    exam.setCompleted(true);
    reset();
  }

  /**
   * Deleted the given exam.
   *
   * @throws IOException if an I/O exception occurs while deleting the exam
   */
  public void onDelete() throws IOException {
    viewCourseInfo(true);
    model.deleteExam(exam.getId());
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "add results" view.
   */
  public void viewAddResults(){
    try
    {
      model.viewExamRelated(exam, ModelManager.ADD_RESULTS);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Uses this view model's exam to get a course object in order to display it in a different view.
   * The appropriate boolean value is used to decide whether this method is used after an exam's deletion,
   * so that if it is not displayed again in the course info view if needed.
   *
   * @param deletion if this method is used after deleting an exam this should be true; false otherwise
   */
  public void viewCourseInfo(boolean deletion){
    exam.getCourse().deleteExam(exam);
    if(!deletion) exam.getCourse().addExam(exam);
    try
    {
      model.viewCourseRelated(exam.getCourse(), ModelManager.VIEW_COURSE);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "create announcement" view.
   */
  public void viewCreateAnnouncement(){
    try
    {
      model.viewExamRelated(exam, ModelManager.CREATE_ANNOUNCEMENT);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "announcement info" view.
   */
  public void viewAnnouncementInfo(Announcement announcement){
    model.viewAnnouncementInfo(announcement, exam.getTitle());
  }

  /**
   * Sends the view model's exam to the model, so that it can be used in the "edit exam" view.
   */
  public void onEdit(){
    try
    {
      model.viewExamRelated(exam, ModelManager.EDIT_EXAM);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns this view model's saved Exam object.
   *
   * @return this view model's saved Exam object
   */
  public Exam getExam(){
    return exam;
  }

  /**
   * Binds the provided StringProperty to the title property,
   * enabling one-way accessing and changing of the title field in the FXML view.
   *
   * @param property the StringProperty to bind to the title property
   */
  public void bindTitle(StringProperty property){
    property.bind(title);
  }

  /**
   * Binds the provided StringProperty to the room property,
   * enabling one-way accessing and changing of the room field in the FXML view.
   *
   * @param property the StringProperty to bind to the room property
   */
  public void bindRoom(StringProperty property){
    property.bind(room);
  }

  /**
   * Binds the provided StringProperty to the content property,
   * enabling one-way accessing and changing of the content TextArea in the FXML view.
   *
   * @param property the StringProperty to bind to the content property
   */
  public void bindContent(StringProperty property){
    property.bind(content);
  }

  /**
   * Binds the provided StringProperty to the time property,
   * enabling one-way accessing and changing of the time field in the FXML view.
   *
   * @param property the StringProperty to bind to the time property
   */
  public void bindTime(StringProperty property){
    property.bind(time);
  }

  /**
   * Binds the provided StringProperty to the examiner property,
   * enabling one-way accessing and changing of the examiner field in the FXML view.
   *
   * @param property the StringProperty to bind to the examiner property
   */
  public void bindExaminer(StringProperty property){
    property.bind(examiner);
  }

  /**
   * Binds the provided StringProperty to the type property,
   * enabling one-way accessing and changing of the type field in the FXML view.
   *
   * @param property the StringProperty to bind to the type property
   */
  public void bindType(StringProperty property){
    property.bind(type);
  }

  /**
   * Binds the provided StringProperty to the date property,
   * enabling one-way accessing and changing of the date field in the FXML view.
   *
   * @param property the StringProperty to bind to the date property
   */
  public void bindDate(StringProperty property){
    property.bind(date);
  }

  /**
   * Binds the provided ObjectProperty to the studentsList property,
   * enabling one-way accessing and changing of the studentsList ListView in the FXML view.
   *
   * @param property the ObjectProperty to bind to the studentsList property
   */
  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bind(studentsList);
  }

  /**
   * Binds the provided ObjectProperty to the announcementsList property,
   * enabling one-way accessing and changing of the announcementsList ListView in the FXML view.
   *
   * @param property the ObjectProperty to bind to the announcementsList property
   */
  public void bindAnnouncements(ObjectProperty<ObservableList<Announcement>> property){
    property.bind(announcementsList);
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
   * Handles property change events, specifically ones relating to exams such as:
   * view exam, edit exam, add results, view announcement.
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "view exam" -> {
        exam = (Exam) evt.getNewValue();
        reset();
        support.firePropertyChange(evt);
      }
      case "edit exam", "add results" -> support.firePropertyChange(evt);
      default -> {
        if (evt.getPropertyName().contains("view announcement")){
          support.firePropertyChange(evt);
        }
      }
    }
  }
}
