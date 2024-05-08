package en.via.sep2_exammaster.server;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAOImpl {
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
