package en.via.sep2_exammaster.server;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import en.via.sep2_exammaster.shared.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * This class extends UnicastRemoteObject and implements the ServerConnector and RemotePropertyChangeListener interfaces.
 * Extending the UnicastRemoteObject is necessary for RMI communication with the client.
 * It implements the ServerConnector as this class follows the Protection Proxy pattern (duh).
 * This also means that each of the methods of this class are essentially checking if the
 * logged-in user is a student or a teacher and then simply calls the actual server's methods for functionality.
 * Lastly, it implements the listener as it also follows the Observer pattern, and it is both a listener and a subject.
 */
public class ServerProtectionProxy extends UnicastRemoteObject implements ServerConnector,
    RemotePropertyChangeListener<Serializable>
{
  private final ServerConnectorImplementation protecting;
  private final RemotePropertyChangeSupport<Serializable> support;

  /**
   * Constructs a new ServerProtectionProxy object.
   * <p>
   * Initializes the ServerConnectorImplementation object and a RemotePropertyChangeSupport object.
   *
   * @throws Exception if any exception occurs
   */
  public ServerProtectionProxy() throws Exception
  {
    this.protecting = new ServerConnectorImplementation();
    this.protecting.addListener(this);
    this.support = new RemotePropertyChangeSupport<>();
  }

  /**
   * Logs in a user with the provided username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return the logged-in user
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public User login(String username, String password)
      throws RemoteException
  {
    return protecting.login(username, password);
  }

  /**
   * Logs out the specified user.
   *
   * @param user the user to logout
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void logout(User user) throws RemoteException
  {
    protecting.logout(user);
  }

  /**
   * Creates a new course with the specified details.
   *
   * @param loggedIn         the loggedIn teacher creating the course
   * @param code             the code of the course
   * @param semester         the semester of the course
   * @param title            the title of the course
   * @param description      the description of the course
   * @param additionalTeacher the additional teacher of the course
   * @param students         the list of students enrolled in the course
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void createCourse(User loggedIn, String code, int semester, String title,
      String description, String additionalTeacher,
      List<Student> students) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.createCourse(loggedIn, code, semester, title, description, additionalTeacher, students);
  }

  /**
   * Edits an existing course with the specified details.
   *
   * @param loggedIn          the loggedIn teacher editing the course
   * @param code             the code with which to find the course to edit
   * @param semester         the new semester of the course
   * @param title            the new title of the course
   * @param description      the new description of the course
   * @param additionalTeacher the new additional teacher of the course
   * @param students         the new list of students enrolled in the course
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void editCourse(User loggedIn, String code, int semester, String title,
      String description, String additionalTeacher,
      List<Student> students) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.editCourse(loggedIn, code, semester, title, description, additionalTeacher, students);
  }

  /**
   * Deletes the specified course from the system and database.
   *
   * @param loggedIn making sure only a teacher can delete a course
   * @param code code of the course to be deleted
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void deleteCourse(User loggedIn, String code) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.deleteCourse(loggedIn, code);
  }

  /**
   * Gets a list of courses with a given Teacher object as the primary or additional teacher of the course.
   *
   * @param teacher teacher whose initials will be used to find a list of courses
   * @return list of courses with the provided teacher as the primary or additional teacher
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public List<Course> getCourses(User teacher)
      throws RemoteException
  {
    if(teacher instanceof Teacher) return protecting.getCourses(teacher);
    else return null;
  }

  /**
   * Marks a given exam as completed.
   *
   * @param loggedIn making sure only a teacher can mark an exam as completed
   * @param exam Exam object to be marked as completed
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void markExamCompleted(User loggedIn, Exam exam) throws RemoteException {
    if(loggedIn instanceof Teacher) protecting.markExamCompleted(loggedIn, exam);
  }

  /**
   * Gets a list of results belonging to a student with a given student ID.
   *
   * @param loggedIn for making sure only a student can get results using their id
   * @param studentId ID of the student, whose results are to be returned
   * @return list of results belonging to the given student
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public List<Result> getResultsByStudentId(User loggedIn, int studentId)
      throws RemoteException
  {
    if(loggedIn instanceof Student) return protecting.getResultsByStudentId(loggedIn, studentId);
    return null;
  }

  /**
   * Creates a new exam with the specified details.
   *
   * @param loggedIn  the user creating the exam
   * @param title     the title of the exam
   * @param content   the content or description of the exam
   * @param room      the room where the exam will take place
   * @param course    the course associated with the exam
   * @param date      the date of the exam
   * @param time      the time of the exam
   * @param written   indicates whether the exam is written or oral
   * @param examiners the type of examiners grading the exam
   * @param students  the list of students participating in the exam
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void createExam(User loggedIn, String title, String content,
      String room, Course course, LocalDate date, LocalTime time,
      boolean written, Examiners examiners, List<Student> students)
      throws RemoteException {
    if(loggedIn instanceof Teacher) protecting.createExam(loggedIn, title, content, room, course, date, time, written, examiners, students);
  }

  /**
   * Edits an existing exam with the specified details.
   *
   * @param loggedIn  the user editing the exam
   * @param id        the new id of exam to be edited
   * @param title     the new title of the exam
   * @param content   the new content of the exam
   * @param room      the new room where the exam will take place
   * @param course    the course associated with the exam (not new since it cannot be changed)
   * @param date      the new date of the exam
   * @param time      the new time of the exam
   * @param written   the new boolean value indicating whether the exam is written or oral
   * @param examiners the new type of examiners grading the exam
   * @param students  the new list of students participating in the exam
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void editExam(User loggedIn, int id, String title,
      String content, String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.editExam(loggedIn, id, title, content, room, course, date, time, written, examiners, students);

  }

  /**
   * Deletes the specified exam from the system and database.
   *
   * @param loggedIn            for making sure only a teacher can delete an exam
   * @param id                  ID of the exam to be deleted
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override public void deleteExam(User loggedIn, int id) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.deleteExam(loggedIn, id);
  }

  /**
   * Creates a new announcement with the specified details.
   *
   * @param loggedIn          the user creating the exam
   * @param title             the title of the announcement
   * @param content           the content of the announcement
   * @param exam              the exam associated with the announcement
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  @Override public void createAnnouncement(User loggedIn, String title,
      String content, Exam exam) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.createAnnouncement(loggedIn, title, content, exam);
  }

  /**
   * Gets the results of a given student for a given exam.
   *
   * @param loggedIn          for making sure only a teacher can get a given student's exam's result
   * @param exam              exam for which the result is to be read
   * @param student           student whose exam result is to be read
   * @return                  result read form the database
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  @Override public Result getStudentExamResult(User loggedIn, Exam exam, Student student)
      throws RemoteException
  {
    if(loggedIn instanceof Teacher) return protecting.getStudentExamResult(loggedIn, exam, student);
    return null;
  }

  /**
   * Gets a list of results for a given exam
   *
   * @param loggedIn            for making sure only a teacher can get all results of an exam
   * @param exam                exam for which to find all the results
   * @return                    list o
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override public List<Result> getResultsByExam(User loggedIn, Exam exam)
      throws RemoteException
  {
    if(loggedIn instanceof Teacher) return protecting.getResultsByExam(loggedIn, exam);
    return null;
  }

  /**
   * Edits an existing result in order to change existing information or add new information (initially
   * results are created with no grade and no feedback).
   *
   * @param loggedIn            the user editing the result
   * @param student             the student to whom the result belongs to
   * @param exam                the exam associated with the result
   * @param grade               the new grade of the result
   * @param feedback            the new feedback of the result
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override public void editResult(User loggedIn, Student student, Exam exam,
      Grade grade, String feedback) throws RemoteException
  {
    if(loggedIn instanceof Teacher) protecting.editResult(loggedIn, student, exam, grade, feedback);
  }

  /**
   * Retrieves a Student object from the database with a given unique ID number.
   *
   * @param loggedIn            user getting the Student object
   * @param studentID           student ID of the student to be retrieved
   * @return                    Student object with the given ID
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override public Student getStudent(User loggedIn, int studentID)
      throws RemoteException
  {
    if(loggedIn instanceof Teacher) return protecting.getStudent(loggedIn, studentID);
    return null;
  }

  /**
   * Adds a listener for remote property change events.
   *
   * @param listener          the listener to be added
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  @Override public void addListener(
      RemotePropertyChangeListener<Serializable> listener)
      throws RemoteException
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a listener for remote property change events.
   *
   * @param listener          the listener to be removed
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  @Override public void removeListener(
      RemotePropertyChangeListener<Serializable> listener)
      throws RemoteException
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * For handling events fired by this class's subjects.
   *
   * @param remotePropertyChangeEvent event fired by a subject
   * @throws RemoteException          if a communication-related exception occurs while handling events
   */
  @Override public void propertyChange(
      RemotePropertyChangeEvent<Serializable> remotePropertyChangeEvent)
      throws RemoteException
  {
    support.firePropertyChange(remotePropertyChangeEvent);
  }
}
