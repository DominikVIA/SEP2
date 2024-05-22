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
import java.util.List;

public class CourseManager extends UnicastRemoteObject implements CourseManagerInterface, RemotePropertyChangeListener<Serializable> {
  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public CourseManager(ServerConnector server) throws IOException {
    this.server = server;
    this.server.addListener(this);
    this.support = new PropertyChangeSupport(this);
  }

  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  @Override
  public void createCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException
  {
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
  public void viewCourse(Course course){
    support.firePropertyChange("view course", null, course);
  }

  @Override
  public void viewEditCourse(Course course){
    support.firePropertyChange("edit course", null, course);
  }

  @Override
  public void deleteCourse(String code) throws IOException{
    server.deleteCourse(code);
  }

  @Override
  public void viewCreateExam(Course course){
    support.firePropertyChange("create exam", null, course);
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
