package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The LoginManager class implements the LoginManagerInterface, making it responsible for
 * managing the logging in and logging out of app users. It also extends the UnicastRemoteObject class
 * in order to facilitate RMI communication with the server.
 */
public class LoginManager extends UnicastRemoteObject implements LoginManagerInterface {

  /** The currently logged-in user. */
  private User loggedIn;

  /** The server connector to communicate with the server. */
  private final ServerConnector server;

  /** Support for property change listeners and to send events to further layers of the app. */
  private final PropertyChangeSupport support;

  /**
   * Constructs a new LoginManager with the specified server connector for use to communicate with the server.
   *
   * @param server the server connector to use
   * @throws IOException if an I/O error occurs
   */
  public LoginManager(ServerConnector server) throws IOException {
    this.server = server;
    this.support = new PropertyChangeSupport(this);
  }

  /**
   * Logs in the user with the given username and password.
   *
   * @param username the username of the user to login
   * @param password the password of the user
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void login(String username, String password) throws IOException
  {
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

  /**
   * Logs out the currently logged-in user.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override public void logout() throws IOException {
    server.logout(loggedIn);
  }

  /**
   * Adds a property change listener to be notified of changes.
   *
   * @param listener the property change listener to add
   */
  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a property change listener.
   *
   * @param listener the property change listener to remove
   */
  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }
}
