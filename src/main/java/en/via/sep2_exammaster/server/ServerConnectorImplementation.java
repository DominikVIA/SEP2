package en.via.sep2_exammaster.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import en.via.sep2_exammaster.server.database.CourseDAOImpl;
import en.via.sep2_exammaster.server.database.Database;
import en.via.sep2_exammaster.server.database.DatabaseManager;
import en.via.sep2_exammaster.shared.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * The ServerConnectorImplementation class implements the ServerConnector interface,
 * providing functionalities to all of ServerConnector's methods,
 * necessary for proper communication with the server and its services.
 * It also contains a reference to the database, a RemotePropertyChangeSupport,
 * and an ArrayList of User objects in order to keep track of currently online users.
 */
public class ServerConnectorImplementation implements ServerConnector {
  private final Database database;
  private final RemotePropertyChangeSupport<Serializable> support;
  private final ArrayList<User> onlineUsers;

  /**
   * Constructs a new ServerConnectorImplementation object.
   * <p>
   * This constructor initializes the ServerConnectorImplementation with a reference to the database,
   * initializes a RemotePropertyChangeSupport object, and initializes an empty list of online users.
   *
   * @throws RemoteException if a remote communication-related exception occurs
   * @throws SQLException    if a database access error occurs
   */
  public ServerConnectorImplementation() throws RemoteException, SQLException {
    database = DatabaseManager.getInstance();
    support = new RemotePropertyChangeSupport<>();
    onlineUsers = new ArrayList<>();
  }

  /**
   * Logs in a user with the provided username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return the logged-in user
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public User login(String username, String password) throws RemoteException {
    List<User> allUsers = database.readAllUsers();
    User user;

    if (!Pattern.matches("[a-zA-Z]+", username))
      user = new Student(Integer.parseInt(username), null, password);
    else
      user = new Teacher(username, null, password);

    if (allUsers.contains(user)) {
      if(!onlineUsers.contains(user))
      {
        int index = allUsers.indexOf(user);
        user = allUsers.get(index);
        onlineUsers.add(user);
      }
      else throw new IllegalArgumentException("login fail online");
    }
    else throw new IllegalArgumentException("login fail credentials");

    return user;
  }

  /**
   * Logs out the specified user.
   *
   * @param user the user to logout
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public void logout(User user) throws RemoteException{
    onlineUsers.remove(user);
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
      List<Student> students) throws RemoteException {
    try {
      Course temp = database.createCourse(code, semester, title, description, (Teacher) loggedIn, additionalTeacher, students);
      support.firePropertyChange("course create success", loggedIn, temp);
    }
    catch (IllegalArgumentException e){
      switch (e.getMessage()){
        case "code exists" -> support.firePropertyChange("course create fail", loggedIn, null);
        case "teacher initials incorrect" -> support.firePropertyChange("teacher not found", loggedIn, null);
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
  @Override
  public void editCourse(User loggedIn, String code, int semester, String title,
      String description, String additionalTeacher,
      List<Student> students) throws RemoteException {
    try {
      Course temp = database.editCourse(code, semester, title, description, (Teacher) loggedIn, additionalTeacher, students);
      support.firePropertyChange("course edit success", loggedIn, temp);
    }
    catch (IllegalArgumentException e){
      support.firePropertyChange("teacher not found", loggedIn, null);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets a list of courses with a given Teacher object as the primary or additional teacher of the course.
   *
   * @param teacher teacher whose initials will be used to find a list of courses
   * @return list of courses with the provided teacher as the primary or additional teacher
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override public List<Course> getCourses(User teacher) throws RemoteException {
    try {
      List<Course> courses = CourseDAOImpl.getInstance().getCourses();
      List<Course> answer = new ArrayList<>();
      for(Course temp : courses){
        if(temp.getTeacher(0).equals(teacher) ||
            (temp.getTeacher(1) != null && temp.getTeacher(1).equals(teacher)))
          answer.add(temp);
      }
      return answer;
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the specified course from the system and database.
   *
   * @param user making sure only a teacher can delete a course
   * @param code code of the course to be deleted
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override
  public void deleteCourse(User user, String code) throws RemoteException{
    database.deleteCourse(code);
  }

  /**
   * Marks a given exam as completed.
   *
   * @param loggedIn making sure only a teacher can mark an exam as completed
   * @param exam Exam object to be marked as completed
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override
  public void markExamCompleted(User loggedIn, Exam exam) throws RemoteException{
    database.markExamCompleted(exam);
  }

  /**
   * Gets a list of results belonging to a student with a given student ID.
   *
   * @param loggedIn for making sure only a student can get results using their id
   * @param studentId ID of the student, whose results are to be returned
   * @return list of results belonging to the given student
   * @throws RemoteException if a remote communication-related exception occurs
   */
  @Override
  public List<Result> getResultsByStudentId(User loggedIn, int studentId) throws RemoteException {
    return database.getResultsByStudentId(studentId);
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
  @Override
  public void createExam(User loggedIn, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws RemoteException {
    try{
      Exam temp = database.createExam(title, content, room, course, date, time, written, examiners, students);
      support.firePropertyChange("exam create success", loggedIn, temp);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
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
  @Override
  public void editExam(
      User loggedIn, int id, String title,
      String content, String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students
  ) throws RemoteException {
    try{
      Exam temp = database.editExam(id, title, content, room, course, date, time, written, examiners, students);
      support.firePropertyChange("exam edit success", loggedIn, temp);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the specified exam from the system and database.
   *
   * @param loggedIn            for making sure only a teacher can delete an exam
   * @param id                  ID of the exam to be deleted
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override
  public void deleteExam(User loggedIn, int id) throws RemoteException{
    database.deleteExam(id);
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
  @Override
  public void createAnnouncement(User loggedIn, String title, String content, Exam exam) throws RemoteException{
    Announcement temp = database.createAnnouncement(title, content, exam);
    support.firePropertyChange("announcement create success", loggedIn, temp);
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
  @Override
  public Result getStudentExamResult(User loggedIn, Exam exam, Student student) throws
      RemoteException
  {
    return database.getStudentResultByExam(exam, student);
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
    return database.getResultsByExam(exam);
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
  @Override
  public void editResult(User loggedIn, Student student, Exam exam, Grade grade, String feedback) throws RemoteException{
    Result temp = database.editResult(student, exam, grade, feedback);
    support.firePropertyChange("result edit success", loggedIn, temp);
  }

  /**
   * Retrieves a Student object from the database with a given unique ID number.
   *
   * @param loggedIn            user getting the Student object
   * @param studentID           student ID of the student to be retrieved
   * @return                    Student object with the given ID
   * @throws RemoteException    if a remote communication-related exception occurs
   */
  @Override public Student getStudent(User loggedIn, int studentID) throws RemoteException {
    Student temp = database.readStudent(studentID);
    if(temp != null) return temp;
    support.firePropertyChange("student not found", loggedIn, null);
    return null;
  }

  /**
   * Adds a listener for remote property change events.
   *
   * @param listener          the listener to be added
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  @Override public void addListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a listener for remote property change events.
   *
   * @param listener          the listener to be removed
   * @throws RemoteException  if a remote communication-related exception occurs
   */
  @Override public void removeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
    support.removePropertyChangeListener(listener);
  }
}
