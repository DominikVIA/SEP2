package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Exam;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * The CreateCourseViewModel class represents the view model for creating a new announcement.
 * It interacts with the Model in order to save the input information
 * and provides a property for each value of an announcement, such as title and content.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class CreateAnnouncementViewModel implements PropertyChangeListener {
  private Exam exam;
  private final Model model;
  private final StringProperty title, content, examLabel;
  private final PropertyChangeSupport support;

  /**
   * Constructs a CreateAnnouncementViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
  public CreateAnnouncementViewModel(Model model){
    this.model = model;
    model.addListener(this);

    title = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    examLabel = new SimpleStringProperty("");

    support = new PropertyChangeSupport(this);
  }

  /**
   * Creates a new announcement using the information provided by the user in the view. Displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O error occurs while creating a new announcement
   */
  public void onCreate() throws IOException {
    String title = this.title.get();
    String content = this.content.get();

    if(title.isBlank() || content.isBlank()){
      support.firePropertyChange("creating error", null, "error");
      return;
    }
    model.createAnnouncement(title, content, exam);
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
   * Resets the ViewModel and its ViewController by setting all the properties to empty Strings.
   */
  public void reset(){
    if(exam != null) {
      title.set("");
      content.set("");
      examLabel.set(exam.getTitle());
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
   * Bidirectionally binds a property to the content StringProperty for two-way accessing and managing of the content TextArea.
   *
   * @param property the StringProperty value to which content will be bound to
   */
  public void bindContent(StringProperty property){
    property.bindBidirectional(content);
  }

  /**
   * Bidirectionally binds a property to the examLabel StringProperty for two-way accessing and managing of the examTitle Label.
   *
   * @param property the StringProperty value to which examLabel will be bound to
   */
  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
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
    if(evt.getPropertyName().equals("create announcement")) {
      this.exam = (Exam) evt.getNewValue();
      reset();
    }
    else if(evt.getPropertyName().equals("announcement create success")){
      this.exam.addAnnouncements((Announcement) evt.getNewValue());
    }
    support.firePropertyChange(evt);
  }


}
