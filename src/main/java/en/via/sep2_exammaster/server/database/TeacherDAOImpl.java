package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {
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

  @Override
  public List<Teacher> getAllTeachers(){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers;");
      ResultSet result = statement.executeQuery();
      ArrayList<Teacher> answer = new ArrayList<>();
      while (result.next()){
        answer.add(new Teacher(
                result.getString(1),
                result.getString(2),
                result.getString(3)
            )
        );
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Teacher getTeacherByInitials(String initials){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE teacher_id = ?");
      statement.setString(1, initials);
      ResultSet result = statement.executeQuery();
      Teacher answer = null;
      if(result.next()){
        answer = new Teacher(
            result.getString("teacher_id"),
            result.getString("name"),
            result.getString("password")
        );
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

}
