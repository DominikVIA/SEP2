package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The DatabaseManager class implements the Database interface, making it
 * responsible for managing interactions with the underlying database.
 * It provides access to various DAO (Data Access Object) implementations for performing
 * operations related to courses, exams, results, students, and teachers.
 * <p>
 * It also follows the Singleton creational design pattern.
 *
 */
public class DatabaseManager implements Database {
  /**
   * The singleton instance of DatabaseManager.
   */
  static private DatabaseManager instance;

  /**
   * Data Access Object for managing courses in the database.
   */
  private CourseDAO courses;

  /**
   * Data Access Object for managing exams in the database.
   */
  private ExamDAO exams;

  /**
   * Data Access Object for managing results in the database.
   */
  private ResultDAO results;

  /**
   * Data Access Object for managing students in the database.
   */
  private StudentDAO students;

  /**
   * Data Access Object for managing teachers in the database.
   */
  private TeacherDAO teachers;

  /**
   * Constructs a new DatabaseManager instance. Gets the DAO objects
   * using the static getInstance() methods.
   *
   * @throws SQLException if a database access error occurs
   */
  private DatabaseManager() throws SQLException {
    courses = CourseDAOImpl.getInstance();
    exams = ExamDAOImpl.getInstance();
    results = ResultDAOImpl.getInstance();
    students = StudentDAOImpl.getInstance();
    teachers = TeacherDAOImpl.getInstance();
  }

  /**
   * Returns the singleton instance of DatabaseManager.
   *
   * @return the DatabaseManager instance
   * @throws SQLException if a database access error occurs
   */
  public static synchronized DatabaseManager getInstance() throws SQLException {
    if (instance == null) instance = new DatabaseManager();
    return instance;
  }

  /**
   * Creates a new course in the database.
   *
   * @param code                        the course code (cannot exceed 12 characters of length)
   * @param semester                    the semester of the course (must be a number between 1 and 9 inclusive)
   * @param title                       the title of the course
   * @param description                 the description of the course
   * @param primaryTeacher              the primary teacher of the course
   * @param additionalTeacherInitials   the initials of the additional teacher
   * @param students                    the list of students enrolled in the course
   * @return the created course
   * @throws SQLException if a database access error occurs
   */
  @Override
  public Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, String additionalTeacherInitials, List<Student> students)
      throws SQLException
  {
    Teacher additionalTeacher = null;
    if(additionalTeacherInitials != null && !additionalTeacherInitials.isBlank()) {
      additionalTeacher = teachers.getTeacherByInitials(
          additionalTeacherInitials);
      if(additionalTeacher == null) throw new IllegalArgumentException("teacher initials incorrect");
    }
    return courses.createCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
  }

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
  @Override
  public Course editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher,
      String additionalTeacherInitials, List<Student> students) throws SQLException
  {
    Teacher additionalTeacher = null;
    if(additionalTeacherInitials != null && !additionalTeacherInitials.isBlank()) {
      additionalTeacher = teachers.getTeacherByInitials(
          additionalTeacherInitials);
      if(additionalTeacher == null) throw new IllegalArgumentException("teacher initials incorrect");
    }
    return courses.editCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
  }

  /**
   * Deletes a course from the database.
   *
   * @param code the code of the course to be deleted
   */
  @Override
  public void deleteCourse(String code){
    courses.deleteCourse(code);
  }

  /**
   * Creates a new announcement associated with an exam in the database.
   *
   * @param title   the title of the announcement
   * @param content the content of the announcement
   * @param exam    the exam associated with the announcement
   * @return        the created announcement
   */
  @Override
  public Announcement createAnnouncement(String title, String content, Exam exam){
    return exams.createAnnouncement(title, content, exam);
  }

  /**
   * Marks an exam as completed in the database.
   *
   * @param exam the exam to be marked as completed
   */
  @Override
  public void markExamCompleted(Exam exam){
    exams.markExamCompleted(exam);
  }

  /**
   * Retrieves results associated with a specific student from the database.
   *
   * @param studentId the ID of the student
   * @return a list of the student's exam results
   */
  @Override
  public List<Result> getResultsByStudentId(int studentId){
    return results.getResultsByStudentID(studentId);
  }

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
  @Override
  public Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws SQLException {
    return exams.createExam(title, content, room, course, date, time, written, examiners, students);
  }

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
  @Override
  public Exam editExam(
      int id, String title, String content,
      String room, Course course, LocalDate date, LocalTime time,
      boolean written, Examiners examiners,
      List<Student> students
  ) throws SQLException{
    return exams.editExam(id, title, content, room, course, date, time, written, examiners, students);
  }

  /**
   * Deletes an exam from the database.
   *
   * @param id the ID of the exam to be deleted
   */
  @Override
  public void deleteExam(int id){
    exams.deleteExam(id);
  }

  /**
   * Retrieves the result of a specific student for a given exam from the database.
   *
   * @param exam    the exam
   * @param student the student
   * @return the result of the student for the exam
   */
  @Override
  public Result getStudentResultByExam(Exam exam, Student student){
    return results.getStudentResultByExamId(exam, student);
  }

  /**
   * Retrieves a list of results associated with a specific exam from the database.
   *
   * @param exam the exam
   * @return a list of results associated with the exam
   */
  @Override public List<Result> getResultsByExam(Exam exam)
  {
    return results.getResultsByExam(exam);
  }

  /**
   * Edits an existing result of a specific student for a given exam in the database.
   *
   * @param student  the student whose result is being edited
   * @param exam     the exam associated with the result
   * @param grade    the grade to be updated
   * @param feedback the feedback to be updated
   * @return         the edited result
   */
  @Override public Result editResult(Student student,
      Exam exam, Grade grade, String feedback) {
    return results.editResult(student, exam, grade, feedback);
  }

  /**
   * Retrieves all users from the database.
   *
   * @return a list of all users
   */
  @Override
  public List<User> readAllUsers() {
    ArrayList<User> answer = new ArrayList<>();
    answer.addAll(students.getAllStudents());
    answer.addAll(teachers.getAllTeachers());
    return answer;
  }

  /**
   * Retrieves a student from the database based on the student ID.
   *
   * @param studentID the ID of the student to be found and retrieved
   * @return the student with the specified ID
   */
  @Override
  public Student readStudent(int studentID){
    return students.getStudentByStudentNo(studentID);
  }
}
