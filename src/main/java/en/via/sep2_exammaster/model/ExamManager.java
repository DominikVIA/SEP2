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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The ExamManager class implements the ExamManagerInterface and RemotePropertyChangeListener interfaces
 * to manage exams and announcements, and to listen for remote property changes sent by the server.
 * It also extends the UnicastRemoteObject class in order to facilitate RMI communication with the server.
 */
public class ExamManager extends UnicastRemoteObject implements ExamManagerInterface, RemotePropertyChangeListener<Serializable> {
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
   * Constructs a ExamManager object with the specified ServerConnector for use to communicate with the server.
   *
   * @param server the ServerConnector to use for communication with the server
   * @throws IOException if an I/O error occurs
   */
  public ExamManager(ServerConnector server) throws IOException {
    this.server = server;
    this.server.addListener(this);
    this.support = new PropertyChangeSupport(this);
  }

  /**
   * Sets the currently logged-in user.
   *
   * @param user the logged-in user.
   */
  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  /**
   * Creates a new exam with the given details.
   *
   * @param title       the title of the exam
   * @param content     the content of the exam
   * @param room        the room for the exam
   * @param course      the course for which the exam is created
   * @param date        the date of the exam
   * @param time        the time of the exam
   * @param written     indicates if the exam is written or oral
   * @param examiners   the type of examiners grading the exam
   * @param students    the list of students enrolled for the exam
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void createExam(String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException {
    server.createExam(loggedIn, title, content, room, course, date, time, written, examiners, students);
  }

  /**
   * Edits an existing exam with the given details.
   *
   * @param id          the id of the exam to edit
   * @param title       the new title of the exam
   * @param content     the new content of the exam
   * @param room        the new room for the exam
   * @param course      the new course for the exam
   * @param date        the new date of the exam
   * @param time        the new time of the exam
   * @param written     indicates if the exam is written or oral
   * @param examiners   the new type examiners grading the exam
   * @param students    the new list of students enrolled for the exam
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void editExam(int id, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException {
    server.editExam(loggedIn, id, title, content, room, course, date, time, written, examiners, students);
  }

  /**
   * Deletes the exam with the given id.
   *
   * @param id          the id of the exam to delete
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void deleteExam(int id) throws IOException{
    server.deleteExam(loggedIn, id);
  }

  /**
   * Marks the given exam as completed.
   *
   * @param exam the exam to mark as completed
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void markExamCompleted(Exam exam) throws IOException {
    server.markExamCompleted(loggedIn, exam);
  }

  /**
   * Creates an announcement for the given exam.
   *
   * @param title       the title of the announcement
   * @param content     the content of the announcement
   * @param exam        the exam for which the announcement is created
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void createAnnouncement(String title, String content, Exam exam) throws IOException{
    server.createAnnouncement(loggedIn, title, content, exam);
  }

  /**
   * Displays information about the given announcement and the associated exam.
   * Sends an event with the announcement to be viewed using property change events to the other layers of the app.
   *
   * @param announcement the announcement to view
   * @param examTitle the title of the exam associated with the announcement
   */
  @Override
  public void viewAnnouncementInfo(Announcement announcement, String examTitle){
    support.firePropertyChange("view announcement-" + examTitle, null, announcement);
  }

  /**
   * Displays the edit view for the given exam.
   * Sends an event with the exam to be edited using property change events to the other layers of the app.
   *
   * @param exam the exam to edit
   */
  @Override
  public void viewEditExam(Exam exam){
    support.firePropertyChange("edit exam", null, exam);
  }

  /**
   * Displays the create announcement view for the given exam.
   * Sends an event with the exam for which a new announcement will be created
   * using property change events to the other layers of the app.
   *
   * @param exam the exam for which to create the announcement.
   */
  @Override
  public void viewCreateAnnouncement(Exam exam){
    support.firePropertyChange("create announcement", null, exam);
  }

  /**
   * Displays information about the given exam.
   * Sends an event with the exam to be viewed using property change events to the other layers of the app.
   *
   * @param exam the exam to view.
   */
  @Override
  public void viewExamInfo(Exam exam){
    support.firePropertyChange("view exam", null, exam);
  }

  /**
   * Displays the add results view for the given exam.
   * Sends an event with the exam for which results are to be added
   * using property change events to the other layers of the app.
   *
   * @param exam the exam for which to add results.
   */
  @Override
  public void viewAddResults(Exam exam){
    support.firePropertyChange("add results", null, exam);
  }

  /**
   * Displays the analytics view for the given exam.
   * Sends an event with the exam the analytics of which are to be viewed
   * using property change events to the other layers of the app.
   *
   * @param exam the exam for which to view analytics.
   */
  @Override
  public void viewAnalytics(Exam exam){
    support.firePropertyChange("view analytics", null, exam);
  }

  /**
   * Adds a property change listener.
   *
   * @param listener the listener to add.
   */
  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a property change listener.
   *
   * @param listener the listener to remove.
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
        (evt.getPropertyName().equals("exam create success") ||
        evt.getPropertyName().equals("exam edit success") ||
        evt.getPropertyName().equals("announcement create success"))
    ) support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }
}
