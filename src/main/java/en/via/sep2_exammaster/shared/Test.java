package en.via.sep2_exammaster.shared;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Test {

  public static Student readStudentById(int id){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ?");
      statement.setInt(1, id);
      ResultSet result = statement.executeQuery();
      Student answer = null;
      if(result.next()){
        answer = new Student(result.getInt("id"), result.getString("name"));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Teacher readTeacherByInitials(String initials){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE initials = ?");
      statement.setString(1, initials);
      ResultSet result = statement.executeQuery();
      Teacher answer = null;
      if(result.next()){
        answer = new Teacher(result.getString("initials"), result.getString("name"));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static ArrayList<Exam> readExamByCourseCode(String courseCode){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
          SELECT *
          FROM courses
          JOIN exams e ON courses.code = e.course_code
          WHERE code = ?;
          """);
      statement.setString(1, courseCode);
      ResultSet result = statement.executeQuery();
      ArrayList<Exam> answer = new ArrayList<>();
      while(result.next()){
        answer.add(new Exam(result.getDate("date").toLocalDate(),
            result.getTime("time").toLocalTime()));
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  public static Course readCourseByCode(String code){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
          SELECT * FROM courses
          JOIN course_students cs ON courses.code = cs.course_code
          JOIN students s ON cs.student = s.id
          WHERE code = ?
          """);
      statement.setString(1, code);
      ResultSet result = statement.executeQuery();
      Course answer = null;
      if(result.next()){
        answer = new Course(result.getString("code"),
            result.getString("title"),
            result.getString("description"),
            readTeacherByInitials(result.getString("teacher")));
        answer.addStudent(readStudentById(result.getInt("id")));
        while(result.next()){
          answer.addStudent(readStudentById(result.getInt("id")));
        }
      }
      if(readExamByCourseCode(code) != null)
        for(Exam temp : readExamByCourseCode(code)) answer.createExam(temp);
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master","postgres","ViaViaVia");
  }

  public static void main(String[] args) throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());

    Course course1 = readCourseByCode("SDJ2");
//    Student student1 = readStudentById(123456);
//    Student student2 = readStudentById(234567);
//    Student student3 = readStudentById(345678);
//    course1.addStudent(student1);
//    course1.addStudent(student2);
//    course1.addStudent(student3);
    //    Exam exam2 = new Exam(LocalDate.of(2024, 5, 23), LocalTime.of(10, 0), student3, student1);
//    course1.createExam(exam2);
//    student1.newResult(new Result(Grade.B, exam1));
//    student2.newResult(new Result(Grade.A, exam1));
//    student3.newResult(new Result(Grade.C, exam1));
    System.out.println(course1);
    System.out.println(course1.getExams().get(0).getStudents());
  }
}
