package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Announcement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AnnouncementInfoViewModelStudent implements PropertyChangeListener {
  private Announcement announcement;
  private String examTitle;
  private final Model model;
  private final StringProperty titleLabel, content, examLabel, dayAndTimeLabel;

  public AnnouncementInfoViewModelStudent(Model model){
    this.model = model;
    this.model.addListener(this);

    titleLabel = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    examLabel = new SimpleStringProperty("");
    dayAndTimeLabel = new SimpleStringProperty("");

  }

  public void reset(){
    if(announcement != null && examTitle != null){
      titleLabel.set(announcement.getTitle());
      examLabel.set(examTitle);
      content.set(announcement.getContent());
      dayAndTimeLabel.set(announcement.getDate() + " | " + announcement.getTime());
    }
  }

  public void bindTitleLabel(StringProperty property){
    property.bind(titleLabel);
  }

  public void bindContent(StringProperty property){
    property.bind(content);
  }

  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
  }

  public void bindDayAndTimeLabel(StringProperty property){
    property.bind(dayAndTimeLabel);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().contains("view announcement")){
      examTitle = evt.getPropertyName().split("-")[1];
      announcement = (Announcement) evt.getNewValue();
      reset();
    }

  }
}
