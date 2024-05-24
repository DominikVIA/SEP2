package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The Database interface defines methods for interacting with the underlying database
 * to perform operations related to courses, exams, announcements, results, users, and students.
 * <p>
 * Implementations of this interface provide functionality to access and manipulate
 * data stored in the database. This data includes information about: students, teachers,
 * courses, exams, announcements and exam results. For each of these pools of information
 * there are appropriate methods to allow the correct users to access or edit the data.
 */
public interface Database
{
  /**
   * Creates a new course in the database.
   *
   * @param code              the course code (cannot exceed 12 characters of length)
   * @param semester          the semester of the course (must be a number between 1 and 9 inclusive)
   * @param title             the title of the course
   * @param description       the description of the course
   * @param primaryTeacher    the primary teacher of the course
   * @param additionalTeacher the initials of the additional teacher
   * @param students          the list of students enrolled in the course
   * @return the created course
   * @throws SQLException if a database access error occurs
   */
  Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws SQLException;

  /**
   * Edits an existing course in the database.
   *
   * @param code                      the course code
   * @param semester                  the semester of the course
   * @param title                     the title of the course
   * @param description               the description of the course
   * @param primaryTeacher            the primary teacher of the course
   * @param additionalTeacherInitials the initials of the additional teacher
   * @param students                  the list of students enrolled in the course
   * @return the edited course
   * @throws SQLException if a database access error occurs
   */
  Course editCourse(String code, int semester, String title, String description,
      Teacher primaryTeacher, String additionalTeacherInitials, List<Student> students) throws SQLException;

  /**
   * Deletes a course from the database.
   *
   * @param code the code of the course to be deleted
   */
  void deleteCourse(String code);

  /**
   * Marks an exam as completed in the database.
   *
   * @param exam the exam to be marked as completed
   */
  void markExamCompleted(Exam exam);

  /**
   * Retrieves results associated with a specific student from the database.
   *
   * @param studentId the ID of the student
   * @return a list of the student's exam results
   */
  List<Result> getResultsByStudentId(int studentId);

  /**
   * Creates a new exam in the database.
   *
   * @param title     the title of the exam
   * @param content   the content of the exam
   * @param room      the room where the exam will be held
   * @param course    the course associated with the exam
   * @param date      the date of the exam
   * @param time      the time of the exam
   * @param written   indicates if the exam is written or not (written or oral)
   * @param examiners the type of examiners grading the exam
   * @param students  the list of students enrolled in the exam
   * @return the created exam
   * @throws SQLException if a database access error occurs
   */
  Exam createExam(String title, String content, String room, Course course,
      LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students) throws SQLException;
  /**
   * Edits an existing exam in the database.
   *
   * @param id        the ID of the exam to be edited (cannot be changed)
   * @param title     the new title of the exam
   * @param content   the new content of the exam
   * @param room      the new room where the exam will be held
   * @param course    the course associated with the exam (cannot be changed)
   * @param date      the date of the exam
   * @param time      the time of the exam
   * @param written   indicates if the exam is written or not
   * @param examiners the type of examiners grading the exam
   * @param students  the list of students enrolled in the exam
   * @return the edited exam
   * @throws SQLException if a database access error occurs
   */
  Exam editExam(int id, String title, String content, String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students) throws SQLException;

  /**
   * Deletes an exam from the database.
   *
   * @param id the ID of the exam to be deleted
   */
  void deleteExam(int id);

  /**
   * Creates a new announcement associated with an exam in the database.
   *
   * @param title   the title of the announcement
   * @param content the content of the announcement
   * @param exam    the exam associated with the announcement
   * @return        the created announcement
   */
  Announcement createAnnouncement(String title, String content, Exam exam);

  /**
   * Retrieves the result of a specific student for a given exam from the database.
   *
   * @param exam    the exam
   * @param student the student
   * @return the result of the student for the exam
   */
  Result getStudentResultByExam(Exam exam, Student student);

  /**
   * Retrieves a list of results associated with a specific exam from the database.
   *
   * @param exam the exam
   * @return a list of results associated with the exam
   */
  List<Result> getResultsByExam(Exam exam);

  /**
   * Edits an existing result of a specific student for a given exam in the database.
   *
   * @param student  the student whose result is being edited
   * @param exam     the exam associated with the result
   * @param grade    the grade to be updated
   * @param feedback the feedback to be updated
   * @return         the edited result
   */
  Result editResult(Student student, Exam exam, Grade grade, String feedback);

  /**
   * Retrieves all users from the database.
   *
   * @return a list of all users
   */
  List<User> readAllUsers();

  /**
   * Retrieves a student from the database based on the student ID.
   *
   * @param studentID the ID of the student to be found and retrieved
   * @return the student with the specified ID
   */
  Student readStudent(int studentID);
}
