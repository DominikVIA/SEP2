package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Announcement;
import en.via.sep2_exammaster.shared.Exam;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class CreateAnnouncementViewModel implements PropertyChangeListener {
  private Exam exam;
  private final Model model;
  private final StringProperty title, content, examLabel;
  private final PropertyChangeSupport support;

  public CreateAnnouncementViewModel(Model model){
    this.model = model;
    model.addListener(this);

    title = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    examLabel = new SimpleStringProperty("");

    support = new PropertyChangeSupport(this);
  }

  public void onSave() throws IOException {
    String title = this.title.get();
    String content = this.content.get();

    if(title.isBlank() || content.isBlank()){
      support.firePropertyChange("creating error", null, "error");
      return;
    }
    model.createAnnouncement(title, content, exam);
  }

  public void viewExamInfo(){
    model.viewExamInfo(exam);
  }

  public void reset(){
    if(exam != null) {
      title.set("");
      content.set("");
      examLabel.set(exam.getTitle());
    }
  }

  public void bindTitle(StringProperty property){
    property.bindBidirectional(title);
  }

  public void bindContent(StringProperty property){
    property.bindBidirectional(content);
  }

  public void bindExamLabel(StringProperty property){
    property.bind(examLabel);
  }

  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

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
