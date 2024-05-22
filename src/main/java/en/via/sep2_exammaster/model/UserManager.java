package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserManager extends UnicastRemoteObject implements
    UserManagerInterface, RemotePropertyChangeListener<Serializable>
{
  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public UserManager(ServerConnector server) throws IOException
  {
    this.server = server;
    this.server.addListener(this);
    this.support = new PropertyChangeSupport(this);
  }

  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
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
