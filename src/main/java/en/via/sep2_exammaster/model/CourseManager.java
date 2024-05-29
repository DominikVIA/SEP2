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

/**
 * The CourseManager class implements the CourseManagerInterface and RemotePropertyChangeListener interfaces
 * to manage courses and listen for remote property changes sent by the server. It also extends the UnicastRemoteObject class
 * in order to facilitate RMI communication with the server.
 */
public class CourseManager extends UnicastRemoteObject implements CourseManagerInterface, RemotePropertyChangeListener<Serializable> {
  /** The currently logged-in user. */
  private User loggedIn;

  /** The server connector to communicate with the server. */
  private final ServerConnector server;

  /**
   * The PropertyChangeSupport instance for managing property change listeners
   * and sending events to the other layers of the application.
   */
  private final PropertyChangeSupport support;

  /**
   * Constructs a CourseManager object with the specified ServerConnector for use to communicate with the server.
   *
   * @param server the ServerConnector to use for communication with the server
   * @throws IOException if an I/O error occurs
   */
  public CourseManager(ServerConnector server) throws IOException {
    this.server = server;
    this.server.addListener(this);
    this.support = new PropertyChangeSupport(this);
  }

  /**
   * Sets the currently logged-in user.
   *
   * @param user The logged-in user.
   */
  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  /**
   * Creates a new course with the given details.
   *
   * @param code                       the code of the course (cannot exceed 12 characters in length)
   * @param semester                   the semester of the course
   * @param title                      the title of the course
   * @param description                the description of the course
   * @param additionalTeacherInitials  the initials of additional teacher
   * @param students                   the list of students enrolled in the course
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void createCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException
  {
    server.createCourse(loggedIn, code, semester, title, description, additionalTeacherInitials, students);
  }

  /**
   * Edits an existing course with the given details.
   *
   * @param code                        the code of the course to edit (cannot be changed)
   * @param semester                    the new semester of the course
   * @param title                       the new title of the course
   * @param description                 the new description of the course
   * @param additionalTeacherInitials   the new initials of additional teacher
   * @param students                    the new list of students enrolled in the course
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void editCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException{
    server.editCourse(loggedIn, code, semester, title, description, additionalTeacherInitials, students);
  }

  /**
   * Retrieves the list of all courses.
   *
   * @return the list of all courses
   * @throws IOException if an I/O error occurs
   */
  @Override
  public List<Course> getCourses() throws IOException {
    return server.getCourses(loggedIn);
  }

  /**
   * Views the details of the given course. Sends an event with the course to
   * be viewed using property change events to the other layers of the app.
   *
   * @param course the course to view
   */
  @Override
  public void viewCourse(Course course){
    support.firePropertyChange("view course", null, course);
  }

  /**
   * Views the edit form for the given course. Sends an event with the course to
   * be edited using property change events to the other layers of the app.
   *
   * @param course the course to edit
   */
  @Override
  public void viewEditCourse(Course course){
    support.firePropertyChange("edit course", null, course);
  }

  /**
   * Deletes the course with the given code.
   *
   * @param code the code of the course to delete
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void deleteCourse(String code) throws IOException{
    server.deleteCourse(loggedIn, code);
  }

  /**
   * Views the form for creating an exam for the given course. Sends an event with the course to
   * have a new exam added using property change events to the other layers of the app.
   *
   * @param course the course for which to create an exam
   */
  @Override
  public void viewCreateExam(Course course){
    support.firePropertyChange("create exam", null, course);
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
    if(evt.getOldValue().equals(loggedIn) &&
        (evt.getPropertyName().equals("course create success") ||
        evt.getPropertyName().equals("course create fail") ||
        evt.getPropertyName().equals("teacher not found") ||
        evt.getPropertyName().equals("course edit success"))
    ) support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }
}
