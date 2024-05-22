package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
  private static StudentDAOImpl instance;

  private StudentDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized StudentDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new StudentDAOImpl();
    return instance;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }

  public void createStudent(Student student){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("INSERT INTO students VALUES (?, ?, ?);");
      statement.setInt(1, student.getStudentNo());
      statement.setString(2, student.getName());
      statement.setString(3, student.getPassword());
      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Student> getAllStudents(){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students;");
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while (result.next()){
        answer.add(new Student(
            result.getInt(1),
            result.getString(2),
            result.getString(3)
        ));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Student getStudentByStudentNo(int studentNo){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");
      statement.setInt(1, studentNo);
      ResultSet result = statement.executeQuery();
      Student answer = null;
      if(result.next()){
        answer = new Student(
            result.getInt("student_id"),
            result.getString("name"),
            result.getString("password"));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Student> getStudentsFromCourse(Course course){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students "
          + "JOIN course_students cs ON cs.student = students.student_id "
          + "WHERE course_code = ?;");
      statement.setString(1, course.getCode());
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while(result.next()){
        answer.add(new Student(
            result.getInt("student_id"),
            result.getString("name"),
            result.getString("password")));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
