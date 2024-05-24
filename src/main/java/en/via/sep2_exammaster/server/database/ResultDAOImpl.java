package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ResultDAOImpl class implements the ResultDAO interface providing functionalities
 * to all the interface's methods for accessing and managing exam results.
 * It also follows the Singleton creational design pattern.
 * <p>
 * This class contains methods for reading students enrolled in an exam, retrieving results by exam or student ID,
 * retrieving a student's result for a specific exam, and editing a student's result for an exam.
 */
public class ResultDAOImpl implements ResultDAO {
  /**
   * Singleton instance of the ResultDAOImpl class.
   */
  private static ResultDAOImpl instance;

  /**
   * Private constructor to prevent instantiation from outside the class.
   *
   * @throws SQLException if a database access error occurs
   */
  private ResultDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Retrieves the instance of the ResultDAOImpl class.
   *
   * @return the singleton instance of the ResultDAOImpl class
   * @throws SQLException if a database access error occurs
   */
  public static synchronized ResultDAOImpl getInstance() throws SQLException {
    if (instance == null) instance = new ResultDAOImpl();
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
   * Retrieves a list of students enrolled in the exam with the specified ID.
   *
   * @param examID the ID of the exam
   * @return a list of students enrolled in the specified exam
   */
  @Override
  public List<Student> readStudentsEnrolledInExam(int examID){
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

  /**
   * Retrieves a list of results for the specified exam.
   *
   * @param exam the exam
   * @return a list of results for the specified exam
   */
  @Override
  public List<Result> getResultsByExam(Exam exam){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM results WHERE exam_id = ?;");
      statement.setInt(1, exam.getId());
      ResultSet result = statement.executeQuery();
      ArrayList<Result> answer = new ArrayList<>();
      while(result.next()){
        answer.add(new Result(
            Grade.findGrade(result.getString("grade") != null ?
                Integer.parseInt(result.getString("grade")) : -2),
            result.getString("feedback"),
            exam));
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves the result of the specified student for the specified exam.
   *
   * @param exam    the exam
   * @param student the student
   * @return the result of the specified student for the specified exam
   */
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
            Grade.findGrade(result.getString("grade") != null ?
                Integer.parseInt(result.getString("grade")) : -2),
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


  /**
   * Updates the result of the specified student for the specified exam.
   *
   * @param student  the student whose result is to be updated
   * @param exam     the exam for which the result is to be updated
   * @param grade    the new grade for the result
   * @param feedback the new feedback for the result
   * @return the updated result
   */
  @Override
  public Result editResult(Student student, Exam exam, Grade grade, String feedback){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("UPDATE results "
          + "SET grade = ?, feedback = ? "
          + "WHERE student_id = ? AND exam_id = ?;");
      statement.setString(1, grade.getGrade() + "");
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

  /**
   * Retrieves a list of results for the specified student ID.
   *
   * @param studentId the ID of the student
   * @return a list of results for the specified student ID
   */
  @Override
  public List<Result> getResultsByStudentID(int studentId){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM results "
          + "JOIN exams e ON e.id = results.exam_id "
          + "JOIN courses c on e.course_code = c.code "
          + "WHERE student_id = ? ORDER BY semester;");
      statement.setInt(1, studentId);
      ResultSet result = statement.executeQuery();
      ArrayList<Result> answer = new ArrayList<>();
      while (result.next()){
        Exam tempExam = new Exam(
            result.getInt("id"),
            result.getString("title"),
            result.getString("content"),
            result.getString("room"),
            CourseDAOImpl.getInstance().getCourseByCode(result.getString("course_code"), false),
            result.getDate("date").toLocalDate(),
            result.getTime("time").toLocalTime(),
            result.getBoolean("written"),
            Examiners.valueOf(result.getString("examiners"))
        );
        tempExam.setCompleted(result.getBoolean("completed"));
        tempExam.addAnnouncements(ExamDAOImpl.getInstance()
            .getExamAnnouncements(tempExam).toArray(new Announcement[0]));
        Result tempResult = new Result(
            Grade.findGrade(result.getString("grade") != null ?
                Integer.parseInt(result.getString("grade")) : -2),
            result.getString("feedback"), tempExam);
        answer.add(tempResult);
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

}
