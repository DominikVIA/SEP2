package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * The interface UserManagerInterface provides methods to manage users and listeners.
 */
public interface UserManagerInterface {
  /**
   * Sets the currently logged-in user to be used to for server communication.
   *
   * @param user the user to set as logged in
   */
  void setLoggedIn(User user);

  /**
   * Retrieves information about a student by their ID.
   *
   * @param studentID the ID of the student to retrieve
   * @return the Student object corresponding to the provided ID
   * @throws IOException if an I/O error occurs while retrieving the student information
   */
  Student getStudent(int studentID) throws IOException;

  /**
   * Adds a property change listener to the user manager.
   *
   * @param listener the property change listener to add
   */
  void addListener(PropertyChangeListener listener);

  /**
   * Removes a property change listener from the user manager.
   *
   * @param listener the property change listener to remove
   */
  void removeListener(PropertyChangeListener listener);
}