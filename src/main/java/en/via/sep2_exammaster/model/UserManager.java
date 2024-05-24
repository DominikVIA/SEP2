package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

/**
 * The UserManager class implements the UserManagerInterface and RemotePropertyChangeListener interfaces
 * to manage users and listen for remote property changes sent by the server. It also extends the UnicastRemoteObject class
 * in order to facilitate RMI communication with the server.
 */
public class UserManager extends UnicastRemoteObject implements UserManagerInterface, RemotePropertyChangeListener<Serializable> {
  /**
   * The currently logged-in user.
   */
  private User loggedIn;

  /**
   * The ServerConnector used for communication with the server.
   */
  private final ServerConnector server;

  /**
   * The PropertyChangeSupport instance for managing property change listeners
   * and sending events to the other layers of the application.
   */
  private final PropertyChangeSupport support;

  /**
   * Constructs a UserManager object with the specified ServerConnector for use to communicate with the server.
   *
   * @param server the ServerConnector to use for communication with the server
   * @throws IOException if an I/O error occurs
   */
  public UserManager(ServerConnector server) throws IOException {
    this.server = Objects.requireNonNull(server, "ServerConnector cannot be null");
    this.server.addListener(this);
    this.support = new PropertyChangeSupport(this);
  }

  /**
   * Sets the currently logged-in user to be used to for server communication.
   *
   * @param user the user to set as logged in
   */
  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  /**
   * Retrieves information about a student by their ID.
   *
   * @param studentID the ID of the student to retrieve
   * @return the Student object corresponding to the provided ID
   * @throws IOException if an I/O error occurs while retrieving the student information
   */
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

  /**
   * Adds a property change listener to the user manager.
   *
   * @param listener the property change listener to add
   */
  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a property change listener from the user manager.
   *
   * @param listener the property change listener to remove
   */
  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  /**
   * Method provided by implementing RemotePropertyChangeListener. It executes a piece of code whenever it detects
   * a fired event from the source it is listening to, in this case that would be the server.
   *
   * @param evt   event fired by the source that this class is listening to
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override public void propertyChange(
      RemotePropertyChangeEvent<Serializable> evt)
      throws RemoteException {
    if(evt.getOldValue().equals(loggedIn) && evt.getPropertyName().equals("student not found")) support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }
}
