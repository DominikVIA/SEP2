package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * An interface defining the methods necessary for the functioning of the logic layer of the application.
 * It defines methods for user authentication and the management of
 * each part of the application such as courses or exams among others.
 * Extends the Closeable interface.
 */
public interface Model extends Closeable {

  /**
   * Attempts to log in a user with a given username and password.
   *
   * @param username the username of the user attempting to log in
   * @param password the password of the user attempting to log in
   * @throws IOException if an I/O error occurs while attempting to log in
   */
  void login(String username, String password) throws IOException;

  /**
   * Logs out the currently logged-in user.
   *
   * @throws IOException if an I/O error occurs while attempting to log out
   */
  void logout() throws IOException;

  /**
   * Creates a new course with the provided details. Since this method is only accessible to a Teacher,
   * the logged-in user is used as the primary teacher.
   *
   * @param code                      the code of the new course (cannot exceed yada-yada)
   * @param semester                  the semester of the new course (must be between 1 and 9 inclusive)
   * @param title                     the title of the new course
   * @param description               the description of the new course
   * @param additionalTeacherInitials the initials of any additional teacher associated with the course
   * @param students                  the list of students enrolled in the new course
   * @throws IOException if an I/O error occurs while attempting to create the course
   */
  void createCourse(String code, int semester, String title, String description,
      String additionalTeacherInitials, List<Student> students) throws IOException;

  /**
   * Edits an existing course with the provided details.
   *
   * @param code                      the code of the course to be edited
   * @param semester                  the new semester of the course
   * @param title                     the new title of the course
   * @param description               the new description of the course
   * @param additionalTeacherInitials the new initials of any additional teacher associated with the course
   * @param students                  the new list of students enrolled in the course
   * @throws IOException if an I/O error occurs while attempting to edit the course
   */
  void editCourse(String code, int semester, String title, String description,
      String additionalTeacherInitials, List<Student> students) throws IOException;

  /**
   * Retrieves the list of all courses.
   *
   * @return the list of all courses
   * @throws IOException if an I/O error occurs while attempting to retrieve the courses
   */
  List<Course> getCourses() throws IOException;

  /**
   * A collection of methods that take a course as a parameter.
   * The string is used to decide which exact method the application should use.
   *
   * @param course the course for which information is to be displayed
   * @param window a String representing a specific method that is contained within this method
   * @throws IOException if an I/O error occurs while attempting to display the course-related information
   */
  void viewCourseRelated(Course course, String window) throws IOException;

  /**
   * Creates a new exam with the provided details.
   *
   * @param title      the title of the new exam
   * @param content    the content or description of the new exam
   * @param room       the room or location of the new exam
   * @param course     the course to which the new exam belongs
   * @param date       the date of the new exam
   * @param time       the time of the new exam
   * @param written    indicates whether the new exam is written or not
   * @param examiners  the examiners responsible for the new exam
   * @param students   the list of students enrolled for the new exam
   * @throws IOException if an I/O error occurs while attempting to create the exam
   */
  void createExam(String title, String content, String room, Course course,
      LocalDate date, LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException;

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
  void editExam(int id, String title, String content, String room, Course course,
      LocalDate date, LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException;

  /**
   * Deletes the exam with the given id.
   *
   * @param id          the id of the exam to delete
   * @throws IOException if an I/O error occurs
   */
  void deleteExam(int id) throws IOException;

  /**
   * Marks the given exam as completed.
   *
   * @param exam the exam to mark as completed
   * @throws IOException if an I/O error occurs
   */
  void markExamCompleted(Exam exam) throws IOException;

  /**
   * Passes the given information to the ExamManager method creating an announcement.
   *
   * @param title       the title of the announcement
   * @param content     the content of the announcement
   * @param exam        the exam for which the announcement is created
   * @throws IOException if an I/O error occurs
   */
  void createAnnouncement(String title, String content, Exam exam) throws IOException;

  /**
   * Passes the given information to the ExamManager method displaying an announcement.
   *
   * @param announcement the announcement to view
   * @param examTitle the title of the exam associated with the announcement
   */
  void viewAnnouncementInfo(Announcement announcement, String examTitle);

  /**
   * A collection of methods that take an exam as a parameter.
   * The string is used to decide which exact method the application should use.
   *
   * @param exam   the exam to be used by a method
   * @param window a String representing a specific method that is contained within this method
   * @throws IOException If an I/O error occurs while attempting to view the exam-related information.
   */
  void viewExamRelated(Exam exam, String window) throws IOException;

  /**
   * Retrieves a list of results for the logged-in Student (this is a method only accessible to Students,
   * so it uses the logged-in user)
   *
   * @return a list of the logged-in student's results
   */
  List<Result> getResults();

  /**
   * Retrieves the result of the specified student for the specified exam.
   *
   * @param exam    the exam for which to retrieve the student's result
   * @param student the student for whom to retrieve the result
   * @return the result of the specified student for the specified exam
   * @throws IOException if an I/O error occurs while attempting to retrieve the student's exam result
   */
  Result getStudentExamResult(Exam exam, Student student) throws IOException;

  /**
   * Retrieves the list of results for the specified exam.
   *
   * @param exam the exam for which to retrieve the results
   * @return the list of results for the specified exam
   * @throws IOException if an I/O error occurs while attempting to retrieve the results
   */
  List<Result> getResultsByExam(Exam exam) throws IOException;

  /**
   * Displays information about the specified result.
   *
   * @param result the result to view
   */
  void viewResult(Result result);

  /**
   * Edits the result of the specified student for the specified exam.
   *
   * @param student  the student whose result is to be edited
   * @param exam     the exam for which the result is to be edited
   * @param grade    the new grade for the result
   * @param feedback the new feedback for the result
   * @throws IOException if an I/O error occurs while attempting to edit the result
   */
  void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException;

  /**
   * Retrieves information about the student with the specified ID.
   *
   * @param studentID the ID of the student to retrieve information about
   * @return the student with the specified ID
   * @throws IOException if an I/O error occurs while attempting to retrieve information about the student
   */
  Student getStudent(int studentID) throws IOException;

  /**
   * Adds a property change listener to this model.
   *
   * @param listener the property change listener to add
   */
  void addListener(PropertyChangeListener listener);

  /**
   * Removes a property change listener from this model.
   *
   * @param listener the property change listener to remove
   */
  void removeListener(PropertyChangeListener listener);

}
