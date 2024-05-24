package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CourseDAOImpl class implements the CourseDAO interface giving functionalities
 * to all the interface's methods for accessing and managing courses.
 * It also follows the Singleton creational design pattern.
 * <p>
 * This class contains methods for checking the availability of a given course code,
 * creating a new course, editing an existing course, getting a specific course by using a given course code,
 * getting a list of all courses and deleting a course.
 */
public class CourseDAOImpl implements CourseDAO {
  /**
   * Singleton instance of the ExamDAOImpl class.
   */
  private static CourseDAOImpl instance;

  /**
   * Private constructor to prevent instantiation from outside the class.
   *
   * @throws SQLException if a database access error occurs
   */
  private CourseDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Retrieves the instance of the CourseDAOImpl class.
   *
   * @return the instance of the CourseDAOImpl class
   * @throws SQLException if a database access error occurs
   */
  public static synchronized CourseDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new CourseDAOImpl();
    return instance;
  }


  /**
   * Establishes a connection to the database.
   *
   * @return a connection to the database
   * @throws SQLException if a database access error occurs
   */
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }

  /**
   * Checks whether there is already a course in the database with a given course code
   * (necessary because in our application a course's code is the primary key and so duplicates are not allowed)
   *
   * @param course    course code the availability of which is to be checked
   * @return    true if the given course code doesn't appear in the database once; false otherwise
   */
  public boolean checkCodeAvailability(String course){
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT count(*) FROM courses WHERE code = ?;");
      statement.setString(1, course);
      ResultSet result = statement.executeQuery();
      result.next();
      return result.getInt(1) == 0;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a new course with the specified details.
   *
   * @param code              the course code (cannot exceed 12 characters of length).
   * @param semester          the semester of the course (must be a number between 1 and 9 inclusive).
   * @param title             the title of the course
   * @param description       the description of the course
   * @param primaryTeacher    the primary teacher for the course
   * @param additionalTeacher the additional teacher for the course
   * @param students          the students enrolled in the course
   * @return the created course
   * @throws SQLException if an SQL exception occurs
   */
  @Override
  public Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, Teacher additionalTeacher, List<Student> students) throws SQLException {
    Connection connection = getConnection();
    try{
      connection.setAutoCommit(false);
      if (checkCodeAvailability(code)) {
        throw new IllegalArgumentException("code exists");
      }
      PreparedStatement statement = connection.prepareStatement("INSERT INTO courses VALUES (?, ?, ?, ?);");
      statement.setString(1, code);
      statement.setInt(2, semester);
      statement.setString(3, title);
      statement.setString(4, description);
      statement.execute();

      Course course = new Course(code, semester, title, description, primaryTeacher);
      statement = connection.prepareStatement("INSERT INTO course_teachers VALUES (?, ?);");
      statement.setString(1, code);
      statement.setString(2, primaryTeacher.getInitials());
      statement.execute();

      if(additionalTeacher != null){
        course.addAdditionalTeacher(additionalTeacher);
        statement.setString(1, code);
        statement.setString(2, additionalTeacher.getInitials());
        statement.execute();
      }

      for(Student student : students) {
        course.addStudent(student);
        statement = connection.prepareStatement(
            "INSERT INTO course_students VALUES (?, ?);");
        statement.setString(1, code);
        statement.setInt(2, student.getStudentNo());
        statement.execute();
      }
      connection.commit();
      return course;
    }
    catch (SQLException e) {
      connection.rollback();
      e.printStackTrace();
      return null;
    }
    finally {
      connection.close();
    }
  }

  /**
   * Retrieves the course with the specified code.
   *
   * @param courseCode          the code of the course
   * @param withExtraInformation specifies if extra information should be retrieved along with the course,
   *                             namely an ArrayList of enrolled students and an ArrayList of associated exams
   *
   * @return the course with the specified code
   */
  @Override
  public Course getCourseByCode(String courseCode, boolean withExtraInformation){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM courses "
             + "JOIN course_teachers ct ON courses.code = ct.course_code "
             + "JOIN teachers t ON ct.teacher = t.teacher_id "
             + "WHERE code = ?;");
      statement.setString(1, courseCode);
      ResultSet result = statement.executeQuery();

      Course course = null;
      if (result.next()){

        String code = result.getString(1);
        int semester = result.getInt(2);
        String title = result.getString(3);
        String description = result.getString(4);
        Teacher primary = TeacherDAOImpl.createTeacher(result);

        course = new Course(code, semester, title, description, primary);

        if (result.next()){
          Teacher additional = TeacherDAOImpl.createTeacher(result);
          course.addAdditionalTeacher(additional);
        }

        if(withExtraInformation)
        {
          course.addExams(ExamDAOImpl.getInstance().getExamsByCourse(course).toArray(new Exam[0]));
          course.addStudents(
              StudentDAOImpl.getInstance().getStudentsFromCourse(course).toArray(new Student[0]));
        }
      }
      return course;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Edits the specified course with the new details.
   *
   * @param code              the code of the course to be edited
   * @param semester          the new semester of the course
   * @param title             the new title of the course
   * @param description       the new description of the course
   * @param primaryTeacher    the new primary teacher for the course
   * @param additionalTeacher the new additional teacher for the course
   * @param students          the new list of students enrolled in the course
   * @return the edited course
   * @throws SQLException if an SQL exception occurs
   */
  @Override
  public Course editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher,
      Teacher additionalTeacher, List<Student> students)
      throws SQLException
  {
    Connection connection = getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement("UPDATE courses "
          + "SET title = ?, semester = ?, description = ? "
          + "WHERE code = ?;");
      statement.setString(1, title);
      statement.setInt(2, semester);
      statement.setString(3, description);
      statement.setString(4,code);
      statement.executeUpdate();

      statement = connection.prepareStatement("DELETE FROM course_students WHERE course_code = ?;");
      statement.setString(1, code);
      statement.executeUpdate();
      statement = connection.prepareStatement("DELETE FROM course_teachers WHERE course_code = ?;");
      statement.setString(1, code);
      statement.executeUpdate();

      statement = connection.prepareStatement("INSERT INTO course_teachers VALUES (?, ?);");
      statement.setString(1, code);
      statement.setString(2, primaryTeacher.getInitials());
      statement.execute();

      if(additionalTeacher != null){
        statement.setString(1, code);
        statement.setString(2, additionalTeacher.getInitials());
        statement.execute();
      }

      for(Student student : students) {
        statement = connection.prepareStatement(
            "INSERT INTO course_students VALUES (?, ?);");
        statement.setString(1, code);
        statement.setInt(2, student.getStudentNo());
        statement.execute();
      }

      connection.commit();
      return getCourseByCode(code, true);
    }
    catch (SQLException e){
      connection.rollback();
      e.printStackTrace();
      return null;
    }
    finally {
      connection.close();
    }
  }

  /**
   * Retrieves a list of all courses.
   *
   * @return a list of all courses
   */
  @Override
  public List<Course> getCourses(){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM courses;");
      ResultSet result = statement.executeQuery();
      ArrayList<Course> list = new ArrayList<>();
      while (result.next()){
        list.add(getCourseByCode(result.getString(1), true));
      }
      return list;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the course with the specified code.
   *
   * @param code the code of the course to delete
   */
  @Override
  public void deleteCourse(String code){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("DELETE FROM courses WHERE code = ?;");
      statement.setString(1, code);
      statement.execute();
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
