package en.via.sep2_exammaster.shared;

import dk.via.remote.observer.RemotePropertyChangeListener;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The ServerConnector interface defines methods for communication with the server.
 * This interface specifies the methods that can be performed remotely by clients on the server.
 */
public interface ServerConnector extends Remote {

  /**
   * Logs in a user with the provided username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return the logged-in user
   * @throws RemoteException if a remote communication-related exception occurs
   */
  User login(String username, String password) throws RemoteException;

  /**
   * Logs out the specified user.
   *
   * @param user the user to logout
   * @throws RemoteException if a remote communication-related exception occurs
   */
  void logout(User user) throws RemoteException;

  /**
   * Creates a new course with the specified details.
   *
   * @param code             the code of the course
   * @param semester         the semester of the course
   * @param title            the title of the course
   * @param description      the description of the course
   * @param primaryTeacher   the primary teacher of the course
   * @param additionalTeacher the additional teacher of the course
   * @param students         the list of students enrolled in the course
   * @throws RemoteException if a remote communication-related exception occurs
   */
  void createCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws RemoteException;
  /**
   * Edits an existing course with the specified details.
   *
   * @param code             the code with which to find the course to edit
   * @param semester         the new semester of the course
   * @param title            the new title of the course
   * @param description      the new description of the course
   * @param primaryTeacher   the new primary teacher of the course
   * @param additionalTeacher the new additional teacher of the course
   * @param students         the new list of students enrolled in the course
   * @throws RemoteException if a remote communication-related exception occurs
   */
  void editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws RemoteException;
  /**
   * Deletes the specified course from the system and database.
   *
   * @param code code of the course to be deleted
   * @throws RemoteException if a remote communication-related exception occurs
   */
  void deleteCourse(String code) throws RemoteException;
  /**
   * Gets a list of courses with a given Teacher object as the primary or additional teacher of the course.
   *
   * @param teacher Teacher object by which to find a list of courses
   * @return list of courses with the provided teacher as the primary or additional teacher
   * @throws RemoteException if a remote communication-related exception occurs
   */
  List<Course> getCourses(Teacher teacher) throws RemoteException;
  /**
   * Marks a given exam as completed.
   *
   * @param exam Exam object to be marked as completed
   * @throws RemoteException if a remote communication-related exception occurs
   */
  void markExamCompleted(Exam exam) throws RemoteException;
  /**
   * Gets a list of results belonging to a student with a given student ID.
   * @param studentId ID of the student, whose results are to be returned
   * @return list of results belonging to the given student
   * @throws RemoteException if a remote communication-related exception occurs
   */
  List<Result> getResultsByStudentId(int studentId) throws RemoteException;
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
  void createExam(User loggedIn, String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws RemoteException;
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
  void editExam(
      User loggedIn, int id, String title,
      String content, String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students
  ) throws RemoteException;
  /**
   * Deletes the specified exam from the system and database.
   *
   * @param id                  ID of the exam to be deleted
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  void deleteExam(int id) throws RemoteException;
  /**
   * Creates a new announcement with the specified details.
   *
   * @param loggedIn          the user creating the exam
   * @param title             the title of the announcement
   * @param content           the content of the announcement
   * @param exam              the exam associated with the announcement
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  void createAnnouncement(User loggedIn, String title, String content, Exam exam) throws RemoteException;
  /**
   * Gets the results of a given student for a given exam.
   *
   * @param exam              exam for which the result is to be read
   * @param student           student whose exam result is to be read
   * @return                  result read form the database
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  Result getStudentExamResult(Exam exam, Student student) throws
      RemoteException;
  /**
   * Gets a list of results for a given exam
   *
   * @param exam                exam for which to find all the results
   * @return                    list o
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  List<Result> getResultsByExam(Exam exam) throws RemoteException;
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
  void editResult(User loggedIn, Student student, Exam exam, Grade grade, String feedback) throws RemoteException;
  /**
   * Retrieves a Student object from the database with a given unique ID number.
   *
   * @param loggedIn            user getting the Student object
   * @param studentID           student ID of the student to be retrieved
   * @return                    Student object with the given ID
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  Student getStudent(User loggedIn, int studentID) throws RemoteException;
  /**
   * Adds a listener for remote property change events.
   *
   * @param listener          the listener to be added
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  void addListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;

  /**
   * Removes a listener for remote property change events.
   *
   * @param listener          the listener to be removed
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  void removeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;


}
