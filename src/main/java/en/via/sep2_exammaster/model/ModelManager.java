package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
  public void editCourse(Course course){
    support.firePropertyChange("edit course", null, course);
  }

  @Override
  public void deleteCourse(String code) throws IOException{
    server.deleteCourse(code);
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
    if(evt.getNewValue().equals(loggedIn)
        || (evt.getNewValue() instanceof Course
        && (((Course) evt.getNewValue()).getTeacher(0).equals(loggedIn)
        || ((Course) evt.getNewValue()).getTeacher(1).equals(loggedIn))
      )
    ) {
      support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
      System.out.println("sending event");
    }
  }

  @Override public void close() throws IOException {
    logout();
    UnicastRemoteObject.unexportObject(this, true);
  }

}
