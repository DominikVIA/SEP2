package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
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
    server.login(username, password);
  }

  @Override
  public List<Course> getCourses() throws IOException {
    return server.getCourses((Teacher)loggedIn);
  }

  @Override
  public void viewCourse(Course course){
    support.firePropertyChange("view course", null, course);
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
    if(evt.getPropertyName().equals("login success")) {
      loggedIn = (User) evt.getNewValue();
    }
    support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }

  @Override public void close() throws NoSuchObjectException {
    UnicastRemoteObject.unexportObject(this, true);
  }

}
