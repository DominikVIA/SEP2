package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ModelManager extends UnicastRemoteObject implements Model, RemotePropertyChangeListener<Serializable> {

  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public ModelManager(ServerConnector server) throws IOException {
    this.server = server;
    this.support = new PropertyChangeSupport(this);
    this.server.addListener(this);
  }

  @Override
  public void login(String username, String password) throws IOException {
    try{
      User temp = server.login(username, password);
      if(temp != null){
        loggedIn = temp;
        support.firePropertyChange("login success", null, loggedIn);
      }
    }
    catch (IllegalArgumentException e){
      support.firePropertyChange(e.getMessage(), null, false);
    }
  }

  @Override public void logout() throws IOException {
    server.removeListener(this);
    server.logout(loggedIn);
  }

  @Override
  public void createCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException{
    server.createCourse(code, semester, title, description, (Teacher) loggedIn, additionalTeacherInitials, students);
  }

  @Override
  public void editCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException{
    server.editCourse(code, semester, title, description, (Teacher) loggedIn, additionalTeacherInitials, students);
  }

  @Override
  public List<Course> getCourses() throws IOException {
    return server.getCourses((Teacher)loggedIn);
  }

  @Override
  public Student getStudent(int studentID) throws IOException{
    try
    {
      return server.getStudent(loggedIn, studentID);
    }
    catch (IllegalArgumentException e){
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Teacher getTeacher(String initials) throws IOException{
    return server.getTeacher(loggedIn, initials);
  }

  @Override
  public void viewCourse(Course course){
    support.firePropertyChange("view course", null, course);
  }

  @Override
  public void viewEditCourse(Course course){
    support.firePropertyChange("edit course", null, course);
  }

  @Override
  public void viewCreateExam(Course course){
    support.firePropertyChange("create exam", null, course);
  }

  @Override
  public void viewCreateAnnouncement(Exam exam){
    support.firePropertyChange("create announcement", null, exam);
  }

  @Override
  public void viewAnnouncement(Announcement announcement, String examTitle){
    support.firePropertyChange("view announcement-" + examTitle, null, announcement);
  }

  @Override
  public void viewEditExam(Exam exam){
    support.firePropertyChange("edit exam", null, exam);
  }

  @Override
  public void deleteCourse(String code) throws IOException{
    server.deleteCourse(code);
  }

  @Override
  public void markExamCompleted(Exam exam) throws IOException{
    server.markExamCompleted(exam);
  }

  @Override
  public List<Exam> getExams() throws IOException {
    return server.getExamsByStudentId(((Student)loggedIn).getStudentNo());
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
  public void createAnnouncement(String title, String content, Exam exam) throws IOException{
    server.createAnnouncement(loggedIn, title, content, exam);
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
  public Result getStudentExamResult(Exam exam, Student student){
    try{
      return server.getStudentExamResult(exam, student);
    }
    catch (IOException e){
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException{
    server.editResult(loggedIn, student, exam, grade, feedback);
  }

  @Override public User getLoggedIn() {
    return loggedIn;
  }

  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(RemotePropertyChangeEvent<Serializable> evt) {
    if(evt.getOldValue().equals(loggedIn))
      support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }

  @Override public void close() throws IOException {
    logout();
    UnicastRemoteObject.unexportObject(this, true);
  }

}
