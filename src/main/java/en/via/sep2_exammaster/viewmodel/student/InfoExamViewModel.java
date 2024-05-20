package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InfoExamViewModel implements PropertyChangeListener {
  private Result result;
  private final Model model;
  private final StringProperty title, room, content, time, date, type, examiner, gradeLabel, feedback;
  private final ObjectProperty<ObservableList<Announcement>> announcements;

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

  public void viewAnnouncement(Announcement announcement){
    model.viewAnnouncement(announcement, result.getExam().getTitle());
  }

  public void reset(){
    if(result != null) {
      title.set(result.getExam().getTitle());
      room.set(result.getExam().getRoom());
      content.set(result.getExam().getContent());
      time.set(result.getExam().getTime().toString());
      date.set(result.getExam().getDate().toString());
      type.set((result.getExam().isWritten() ? "Written" : "Oral"));
      examiner.set(result.getExam().getExaminers().name());
      gradeLabel.set((result.getGrade() != null ? result.getGrade().toString() : "Not graded"));
      feedback.set(result.getFeedback());
      announcements.getValue().setAll(result.getExam().getAnnouncements());
    }
  }

  public void bindTitle(StringProperty property){
    property.bind(title);
  }

  public void bindRoom(StringProperty property){
    property.bind(room);
  }

  public void bindContent(StringProperty property){
    property.bind(content);
  }

  public void bindTime(StringProperty property){
    property.bind(time);
  }

  public void bindDate(StringProperty property){
    property.bind(date);
  }

  public void bindType(StringProperty property){
    property.bind(type);
  }

  public void bindExaminer(StringProperty property){
    property.bind(examiner);
  }

  public void bindGradeLabel(StringProperty property){
    property.bind(gradeLabel);
  }

  public void bindFeedback(StringProperty property){
    property.bind(feedback);
  }

  public void bindAnnouncements(ObjectProperty<ObservableList<Announcement>> property){
    property.bind(announcements);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("view result")) {
      result = (Result) evt.getNewValue();
    }
  }
}
