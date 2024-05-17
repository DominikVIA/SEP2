package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Result;
import en.via.sep2_exammaster.shared.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAOImpl implements ResultDAO {
  private static ResultDAOImpl instance;

  private ResultDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized ResultDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new ResultDAOImpl();
    return instance;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }

  @Override public List<Student> readStudentsEnrolledInExam(int examID){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM results WHERE exam_id = ?;");
      statement.setInt(1, examID);
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while(result.next()){
        answer.add(StudentDAOImpl.getInstance().getStudentByStudentNo(result.getInt(1)));
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Result getStudentResultByExamId(Exam exam, Student student){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM results WHERE student_id = ? AND exam_id = ?;");
      statement.setInt(1, student.getStudentNo());
      statement.setInt(2, exam.getId());
      ResultSet result = statement.executeQuery();
      Result answer = null;
      if(result.next()){
        answer = new Result(
            Grade.findGrade(result.getInt("grade")),
            result.getString("feedback"),
            exam);
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Result editResult(Student student, Exam exam, Grade grade, String feedback){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
          UPDATE results
          SET grade = ?, feedback = ?
          WHERE student_id = ? AND exam_id = ?;
          """);
      statement.setInt(1, grade.getGrade());
      statement.setString(2, (feedback.isBlank() ? null : feedback));
      statement.setInt(3, student.getStudentNo());
      statement.setInt(4, exam.getId());
      statement.execute();
      return getStudentResultByExamId(exam, student);
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

//  public List<Result> readResultByStudent(int student_ID){
//    try(Connection connection = getConnection()){
//      PreparedStatement statement = connection.prepareStatement("SELECT * FROM results WHERE student_id = ?;");
//      statement.setInt(1, student_ID);
//      ResultSet result = statement.executeQuery();
//      ArrayList<Exam> answer = new ArrayList<>();
//      while(result.next()){
//        answer.add(new Exam(
//            result.getString("title"),
//            result.getString("content"),
//            result.getString("room"),
//            course,
//            result.getDate("date").toLocalDate(),
//            result.getTime("time").toLocalTime(),
//            result.getBoolean("completed")
//        ));
//      }
//
//      return answer;
//    }
//    catch (SQLException e){
//      e.printStackTrace();
//      return null;
//    }
//  }
}
