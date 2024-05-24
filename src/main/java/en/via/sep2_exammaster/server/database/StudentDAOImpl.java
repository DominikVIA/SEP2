package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The StudentDAOImpl class implements the StudentDAO interface,
 * providing functionalities to all the interface's methods.
 * It also follows the Singleton creational design pattern.
 */
public class StudentDAOImpl implements StudentDAO {
  private static StudentDAOImpl instance;

  /**
   * Private constructor to prevent instantiation from outside the class.
   *
   * @throws SQLException if a database access error occurs
   */
  private StudentDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Returns the instance of StudentDAOImpl.
   *
   * @return the singleton instance of StudentDAOImpl
   * @throws SQLException if a database access error occurs
   */
  public static synchronized StudentDAOImpl getInstance() throws SQLException {
    if (instance == null) instance = new StudentDAOImpl();
    return instance;
  }

  /**
   * Establishes a connection to the database.
   *
   * @return a Connection object representing the database connection
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
   * Retrieves a list of all students from the database.
   *
   * @return a list of all students
   */
  @Override
  public List<Student> getAllStudents() {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students;");
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while (result.next()) {
        answer.add(new Student(
            result.getInt(1),
            result.getString(2),
            result.getString(3)
        ));
      }
      return answer;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves a student with the specified student ID from the database.
   *
   * @param studentNo the student ID of the student to get
   * @return the student with the specified student ID, or null if not found
   */
  @Override
  public Student getStudentByStudentNo(int studentNo) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");
      statement.setInt(1, studentNo);
      ResultSet result = statement.executeQuery();
      Student answer = null;
      if (result.next()) {
        answer = new Student(
            result.getInt("student_id"),
            result.getString("name"),
            result.getString("password"));
      }
      return answer;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves a list of students enrolled in the specified course from the database.
   *
   * @param course the course for which to retrieve enrolled students
   * @return a list of students enrolled in the specified course
   */
  public List<Student> getStudentsFromCourse(Course course) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students "
          + "JOIN course_students cs ON cs.student = students.student_id "
          + "WHERE course_code = ?;");
      statement.setString(1, course.getCode());
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while (result.next()) {
        answer.add(new Student(
            result.getInt("student_id"),
            result.getString("name"),
            result.getString("password")));
      }
      return answer;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
