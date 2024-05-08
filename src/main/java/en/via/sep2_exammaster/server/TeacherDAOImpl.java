package en.via.sep2_exammaster.server;

import en.via.sep2_exammaster.shared.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAOImpl {
  private static TeacherDAOImpl instance;

  private TeacherDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized TeacherDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new TeacherDAOImpl();
    return instance;
  }

  public static Teacher createTeacher(ResultSet resultSet) throws SQLException {
    String initials = resultSet.getString("teacher_id");
    String name = resultSet.getString("name");
    String password = resultSet.getString("password");
    return new Teacher(initials, name, password);
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }



}
