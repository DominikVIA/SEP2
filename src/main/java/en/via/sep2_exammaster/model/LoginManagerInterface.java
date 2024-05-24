package en.via.sep2_exammaster.model;

import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * The interface LoginManagerInterface provides methods for managing users logging in and logging out.
 */
public interface LoginManagerInterface {

  /**
   * Logs in the user with the given username and password.
   *
   * @param username the username of the user to login
   * @param password the password of the user
   * @throws IOException if an I/O error occurs
   */
  void login(String username, String password) throws IOException;

  /**
   * Logs out the currently logged-in user.
   *
   * @throws IOException if an I/O error occurs
   */
  void logout() throws IOException;

  /**
   * Adds a property change listener to be notified of changes.
   *
   * @param listener the property change listener to add
   */
  void addListener(PropertyChangeListener listener);

  /**
   * Removes a property change listener.
   *
   * @param listener the property change listener to remove
   */
  void removeListener(PropertyChangeListener listener);
}

