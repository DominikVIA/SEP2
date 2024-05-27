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
 * The ResultManager class implements the ResultManagerInterface and RemotePropertyChangeListener interfaces
 * to manage exam results and listen for remote property changes sent by the server. It also extends the UnicastRemoteObject class
 * in order to facilitate RMI communication with the server.
 */
public class ResultManager extends UnicastRemoteObject implements ResultManagerInterface, RemotePropertyChangeListener<Serializable> {

  /** The currently logged-in user. */
  private User loggedIn;

  /** The server connector to communicate with the server. */
  private final ServerConnector server;

  // constructor, unrelated instances, unrelated methods

  /**
   * The PropertyChangeSupport instance for managing property change listeners
   * and sending events to the other layers of the application.
   */
  private final PropertyChangeSupport support;

  /**
   * Constructs a ResultManager object with the specified ServerConnector for use to communicate with the server.
   *
   * @param server the ServerConnector to use for communication with the server
   * @throws IOException if an I/O error occurs
   */
  public ResultManager(ServerConnector server) throws IOException {
    this.server = server;
    this.support = new PropertyChangeSupport(this);
  }

  /**
   * Sets the logged-in user.
   *
   * @param user the user to set as logged in
   */
  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  /**
   * Retrieves all results for the logged-in user.
   *
   * @return a list of a logged-in student's results
   * @throws IOException if an I/O error occurs
   */
  @Override
  public List<Result> getResults() throws IOException
  {
    return server.getResultsByStudentId(((Student)loggedIn).getStudentNo());
  }

  /**
   * Retrieves the result of a specific student for a specific exam.
   *
   * @param exam      the exam
   * @param student   the student
   * @return the result of the student for the exam
   * @throws IOException if an I/O error occurs
   */
  @Override
  public Result getStudentExamResult(Exam exam, Student student)
      throws IOException
  {
    return server.getStudentExamResult(exam, student);
  }

  /**
   * Retrieves all results for a specific exam.
   *
   * @param exam the exam
   * @return a list of results for the exam
   * @throws IOException if an I/O error occurs
   */
  @Override
  public List<Result> getResultsByExam(Exam exam)
      throws IOException
  {
    return server.getResultsByExam(exam);
  }

  /**
   * View a specific result. Sends the provided result through the different layers of the app, doesn't actually communicate with the server.
   *
   * @param result the result to view
   */
  @Override
  public void viewResult(Result result) {
    support.firePropertyChange("view result", null, result);
  }

  /**
   * Edits the result of a specific student for a specific exam.
   *
   * @param student the student
   * @param exam the exam
   * @param grade the new grade
   * @param feedback the new feedback
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException{
    server.editResult(loggedIn, student, exam, grade, feedback);
  }

  /**
   * Adds a listener for property changes.
   *
   * @param listener the listener to add
   */
  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a listener for property changes.
   *
   * @param listener the listener to remove
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
    if(evt.getOldValue().equals(loggedIn) && evt.getPropertyName().equals("result edit success"))
      support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }
}
