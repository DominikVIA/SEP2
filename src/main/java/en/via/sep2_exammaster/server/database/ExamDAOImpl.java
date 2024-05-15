package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Examiners;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExamDAOImpl implements ExamDAO {
  private static ExamDAOImpl instance;

  private ExamDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized ExamDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new ExamDAOImpl();
    return instance;
  }

  @Override
  public Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners) {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, false);");
      statement.setString(1, title);
      statement.setString(2, content);
      statement.setString(3, room);
      statement.setString(4, examiners.name());
      statement.setObject(5, date);
      statement.setObject(6, time);
      statement.setString(7, course.getCode());
      statement.setBoolean(8, written);
      statement.execute();
      return new Exam(title, content, room, course, date, time, written, examiners);
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  public static Exam createExam(ResultSet resultSet) throws SQLException {
    String title = resultSet.getString(7);
    String content = resultSet.getString(8);
    String room = resultSet.getString(9);
    LocalDate date = LocalDate.parse(resultSet.getString(10));
    LocalTime time = LocalTime.parse(resultSet.getString(11));
    boolean completed = resultSet.getBoolean(13);
    Exam temp = new Exam(title, content, room, date, time);
    temp.setCompleted(completed);
    return temp;
  }

  @Override
  public List<Exam> getExamsByCourse(Course course) throws SQLException {
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM exams WHERE course_code = ?;");
      statement.setString(1, course.getCode());
      ResultSet result = statement.executeQuery();
      ArrayList<Exam> answer = new ArrayList<>();
      while(result.next()){
        Exam temp = new Exam(
            result.getString("title"),
            result.getString("content"),
            result.getString("room"),
            course,
            result.getDate("date").toLocalDate(),
            result.getTime("time").toLocalTime(),
            result.getBoolean("written"),
            Examiners.valueOf(result.getString("examiners"))
            );
        temp.setCompleted(result.getBoolean("completed"));
        answer.add(temp);
      }

      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }
}
