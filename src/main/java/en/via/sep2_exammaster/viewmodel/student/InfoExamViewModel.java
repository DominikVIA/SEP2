package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The InfoExamViewModel class represents the view model for displaying more information about a given exam and its results (from the perspective of a student).
 * It provides a property for each value of an exam and its result (such as title, room, content, gradeLabel, feedback, etc.).
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class InfoExamViewModel implements PropertyChangeListener {
  private Result result;
  private final Model model;
  private final StringProperty title, room, content, time, date, type, examiner, gradeLabel, feedback;
  private final ObjectProperty<ObservableList<Announcement>> announcements;

  /**
   * Constructs a InfoExamViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
  public InfoExamViewModel(Model model){
    this.model = model;
    this.model.addListener(this);
    title = new SimpleStringProperty("");
    room = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    time = new SimpleStringProperty("");
    date = new SimpleStringProperty("");
    type = new SimpleStringProperty("");
    examiner = new SimpleStringProperty("");
    gradeLabel = new SimpleStringProperty("");
    feedback = new SimpleStringProperty("");
    announcements = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  /**
   * Opens a view for viewing a selected announcement's information.
   *
   * @param announcement announcement to be displayed in the view
   */
  public void viewAnnouncementInfo(Announcement announcement){
    model.viewAnnouncementInfo(announcement, result.getExam().getTitle());
  }

  /**
   * Resets the ViewModel and its ViewController by setting each property to the corresponding value of the exam or its result.
   */
  public void reset(){
    if(result != null) {
      title.set(result.getExam().getTitle());
      room.set(result.getExam().getRoom());
      content.set(result.getExam().getContent());
      time.set(result.getExam().getTime().toString());
      date.set(result.getExam().getDate().toString());
      type.set((result.getExam().isWritten() ? "Written" : "Oral"));
      examiner.set(result.getExam().getExaminers().name());
      gradeLabel.set((result.getGrade().getGrade() != -2 ? result.getGrade().toString() : "Waiting for grade"));
      feedback.set(result.getFeedback());
      announcements.getValue().setAll(result.getExam().getAnnouncements());
    }
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
   * Binds a property to the gradeLabel StringProperty for one-way accessing and managing of the examLabel Label in the view.
   *
   * @param property the StringProperty to bind to the gradeLabel property
   */
  public void bindGradeLabel(StringProperty property){
    property.bind(gradeLabel);
  }

  public void bindFeedback(StringProperty property){
    property.bind(feedback);
  }

  /**
   * Binds the provided ObjectProperty to the announcementsList property,
   * enabling one-way accessing and changing of the announcementsList ListView in the FXML view.
   *
   * @param property the ObjectProperty to bind to the announcementsList property
   */
  public void bindAnnouncements(ObjectProperty<ObservableList<Announcement>> property){
    property.bind(announcements);
  }

  /**
   * Handles property change events fired by its subjects (ModelManager).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("view result")) {
      result = (Result) evt.getNewValue();
    }
  }
}
