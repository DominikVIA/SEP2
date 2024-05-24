package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TeacherDAOImpl class implements the TeacherDAO interface,
 * providing functionalities to all the interface's methods.
 * It also follows the Singleton creational design pattern.
 */
public class TeacherDAOImpl implements TeacherDAO {
  private static TeacherDAOImpl instance;

  /**
   * Private constructor to prevent instantiation from outside the class.
   *
   * @throws SQLException if a database access error occurs
   */
  private TeacherDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Returns the instance of TeacherDAOImpl.
   *
   * @return the singleton instance of TeacherDAOImpl
   * @throws SQLException if a database access error occurs
   */
  public static synchronized TeacherDAOImpl getInstance() throws SQLException {
    if (instance == null) instance = new TeacherDAOImpl();
    return instance;
  }

  /**
   * Creates a Teacher object based on the provided ResultSet.
   *
   * @param resultSet the ResultSet containing teacher data
   * @return a Teacher object created from the ResultSet
   * @throws SQLException if a database access error occurs
   */
  public static Teacher createTeacher(ResultSet resultSet) throws SQLException {
    String initials = resultSet.getString("teacher_id");
    String name = resultSet.getString("name");
    String password = resultSet.getString("password");
    return new Teacher(initials, name, password);
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
   * Retrieves a list of all teachers from the database.
   *
   * @return a list of all teachers
   */
  @Override
  public List<Teacher> getAllTeachers() {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers;");
      ResultSet result = statement.executeQuery();
      ArrayList<Teacher> answer = new ArrayList<>();
      while (result.next()) {
        answer.add(new Teacher(
                result.getString(1),
                result.getString(2),
                result.getString(3)
            )
        );
      }
      return answer;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves a teacher with the specified initials from the database.
   *
   * @param initials the initials of the teacher to retrieve
   * @return the teacher with the specified initials, or null if not found
   */
  @Override
  public Teacher getTeacherByInitials(String initials) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE teacher_id = ?");
      statement.setString(1, initials);
      ResultSet result = statement.executeQuery();
      Teacher answer = null;
      if (result.next()) {
        answer = new Teacher(
            result.getString("teacher_id"),
            result.getString("name"),
            result.getString("password")
        );
      }
      return answer;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
