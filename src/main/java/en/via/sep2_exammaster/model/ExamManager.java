package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ExamManager extends UnicastRemoteObject implements ExamManagerInterface, RemotePropertyChangeListener<Serializable> {
  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public ExamManager(ServerConnector server) throws IOException {
    this.server = server;
    this.support = new PropertyChangeSupport(this);
  }

  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  @Override
  public void createExam(String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException {
    server.createExam(loggedIn, title, content, room, course, date, time, written, examiners, students);
  }

  @Override
  public void editExam(int id, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException {
    server.editExam(loggedIn, id, title, content, room, course, date, time, written, examiners, students);
  }

  @Override
  public void deleteExam(int id) throws IOException{
    server.deleteExam(id);
  }

  @Override
  public void markExamCompleted(Exam exam) throws IOException {
    server.markExamCompleted(exam);
  }

  @Override
  public void createAnnouncement(String title, String content, Exam exam) throws IOException{
    server.createAnnouncement(loggedIn, title, content, exam);
  }

  @Override
  public void viewAnnouncementInfo(Announcement announcement, String examTitle){
    support.firePropertyChange("view announcement-" + examTitle, null, announcement);
  }

  @Override
  public void viewEditExam(Exam exam){
    support.firePropertyChange("edit exam", null, exam);
  }

  @Override
  public void viewCreateAnnouncement(Exam exam){
    support.firePropertyChange("create announcement", null, exam);
  }

  @Override
  public void viewExamInfo(Exam exam){
    support.firePropertyChange("view exam", null, exam);
  }

  @Override
  public void viewAddResults(Exam exam){
    support.firePropertyChange("add results", null, exam);
  }

  @Override
  public void viewAnalytics(Exam exam){
    support.firePropertyChange("view analytics", null, exam);
  }

  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(
      RemotePropertyChangeEvent<Serializable> evt)
      throws RemoteException {
    if(evt.getOldValue().equals(loggedIn)) support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }
}
