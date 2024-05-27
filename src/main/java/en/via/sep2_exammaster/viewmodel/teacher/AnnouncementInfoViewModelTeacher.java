package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Announcement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The AnnouncementInfoViewModelTeacher class represents the view model for displaying information about an announcement as a teacher.
 * It provides a property for each value of an announcement: the titleLabel for the announcement title,
 * the content of the announcement, the examLabel for the title of the exam the announcements belongs to
 * and the dayAndTimeLabel of when the announcement was created.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class AnnouncementInfoViewModelTeacher implements PropertyChangeListener {
  private Announcement announcement;
  private String examTitle;
  private final Model model;
  private final StringProperty titleLabel, content, examLabel, dayAndTimeLabel;

  /**
   * Constructs a AnnouncementInfoViewModelTeacher with the given model.
   *
   * @param model the model for communication with the server
   */
  public AnnouncementInfoViewModelTeacher(Model model){
    this.model = model;
    this.model.addListener(this);

    titleLabel = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    examLabel = new SimpleStringProperty("");
    dayAndTimeLabel = new SimpleStringProperty("");

  }

  /**
   * Resets the ViewModel and its ViewController by setting all the properties to
   * the information of the viewed announcement and the title of the associated exam.
   */
  public void reset(){
    if(announcement != null && examTitle != null){
      titleLabel.set(announcement.getTitle());
      examLabel.set(examTitle);
      content.set(announcement.getContent());
      dayAndTimeLabel.set(announcement.getDate() + " | " + announcement.getTime());
    }
  }

  /**
   * Binds a property to the titleLabel StringProperty for one-way accessing and managing of the titleLabel label
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which titleLabel will be bound to
   */
  public void bindTitleLabel(StringProperty property){
    property.bind(titleLabel);
  }

  /**
   * Binds a property to the content StringProperty for one-way accessing and managing of the content field
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which content will be bound to
   */
  public void bindContent(StringProperty property){
    property.bind(content);
  }

  /**
   * Binds a property to the examLabel StringProperty for one-way accessing and managing of the examLabel label
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which examLabel will be bound to
   */
  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
  }

  /**
   * Binds a property to the dayAndTimeLabel StringProperty for one-way accessing and managing of the dayAndTimeLabel label
   * (one-way since it cannot be edited by the user).
   *
   * @param property the StringProperty value to which dayAndTimeLabel will be bound to
   */
  public void bindDayAndTimeLabel(StringProperty property){
    property.bind(dayAndTimeLabel);
  }

  /**
   * Handles property change events fired by its subjects (ModelManager).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().contains("view announcement")){
      examTitle = evt.getPropertyName().split("-")[1];
      announcement = (Announcement) evt.getNewValue();
      reset();
    }

  }
}
