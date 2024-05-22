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
import java.time.LocalDate;

public class ExamInfoViewModel implements PropertyChangeListener {
  private Exam exam;
  private final Model model;
  private final StringProperty title, room, content, time, type, examiner, date;
  private final ObjectProperty<ObservableList<Announcement>> announcementsList;
  private final ObjectProperty<ObservableList<Student>> studentsList;
  private final PropertyChangeSupport support;

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

  public void markExamCompleted() throws IOException {
    model.markExamCompleted(exam);
    exam.complete();
    reset();
  }

  public void onDelete() throws IOException {
    viewCourseInfo(true);
    model.deleteExam(exam.getId());
  }

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

  public void viewAnnouncementInfo(Announcement announcement){
    model.viewAnnouncementInfo(announcement, exam.getTitle());
  }

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

  public Exam getExam(){
    return exam;
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

  public void bindExaminer(StringProperty property){
    property.bind(examiner);
  }

  public void bindType(StringProperty property){
    property.bind(type);
  }

  public void bindDate(StringProperty property){
    property.bind(date);
  }

  public void bindStudents(ObjectProperty<ObservableList<Student>> property){
    property.bind(studentsList);
  }

  public void bindAnnouncements(ObjectProperty<ObservableList<Announcement>> property){
    property.bind(announcementsList);
  }

  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "view exam" -> {
        exam = (Exam) evt.getNewValue();
        reset();
        support.firePropertyChange(evt);
      }
      case "edit exam", "add results" -> {
        support.firePropertyChange(evt);
      }
      default -> {
        if (evt.getPropertyName().contains("view announcement")){
          support.firePropertyChange(evt);
        }
      }
    }
  }
}
