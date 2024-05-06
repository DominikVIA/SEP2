package en.via.sep2_exammaster.server;

import en.via.sep2_exammaster.shared.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class DatabaseManagerImplementation
{
  private static DatabaseManagerImplementation instance;

  private DatabaseManagerImplementation(){

  }

  public static DatabaseManagerImplementation getInstance(){
    if(instance == null) instance = new DatabaseManagerImplementation();
    return instance;
  }

  public void writeCourse(Course course){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("INSERT INTO courses VALUES (?, ?, ?, ?, ?);");
      statement.setString(1, course.getCode());
      statement.setString(2, course.getTitle());
      statement.setString(3, course.getDescription());
      statement.setString(4, course.getTeacher(0).getInitials());
      statement.setString(5, (course.getTeacher(1) != null ? course.getTeacher(1).getInitials() : null));
      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void writeStudent(Student student){
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

  public void writeTeacher(Teacher teacher){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers VALUES (?, ?, ?);");
      statement.setString(1, teacher.getInitials());
      statement.setString(2, teacher.getName());
      statement.setString(3, teacher.getPassword());
      statement.execute();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Student> readAllStudents(){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students;");
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while (result.next()){
        answer.add(readStudentByStudentNoWithResults(result.getInt(1)));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Teacher> readAllTeachers(){
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

  public ArrayList<User> readAllUsers() {
    ArrayList<User> answer = new ArrayList<>();
    answer.addAll(readAllStudents());
    answer.addAll(readAllTeachers());
    return answer;
  }

  public Student readStudentByStudentNoWithoutResults(int studentNo){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");
      statement.setInt(1, studentNo);
      ResultSet result = statement.executeQuery();
      Student answer = null;
      if(result.next()){
        answer = new Student(result.getInt("student_id"), result.getString("name"), result.getString("password"));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Student readStudentByStudentNoWithResults(int studentNo){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");
      statement.setInt(1, studentNo);
      ResultSet result = statement.executeQuery();
      Student answer = null;
      if(result.next()){
        answer = new Student(result.getInt("student_id"), result.getString("name"), result.getString("password"));
        do{
          readResultsByStudentNo(studentNo);
        } while (result.next());
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Student> readStudentsFromCourse(String course_code){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM course_students WHERE course_code = ?;");
      statement.setString(1, course_code);
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while(result.next()){
        answer.add(readStudentByStudentNoWithoutResults(result.getInt("student")));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Student> readStudentsEnrolledInExam(Exam exam){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
          SELECT *
          FROM results JOIN exams e on results.exam_id = e.id
          WHERE date = ? AND time = ? AND course_code = ?;
          """);
      statement.setDate(1, Date.valueOf(exam.getDate()));
      statement.setTime(2, Time.valueOf(exam.getTime()));
      statement.setString(3, exam.getCourse().getCode());
      ResultSet result = statement.executeQuery();
      ArrayList<Student> answer = new ArrayList<>();
      while(result.next()){
        answer.add(readStudentByStudentNoWithoutResults(result.getInt("student_id")));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Result> readResultsByStudentNo(int studentNo){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
            SELECT *
            FROM students
                JOIN results r on students.student_id = r.student_id
                JOIN exams e on r.exam_id = e.id
            WHERE grade IS NOT NULL AND students.student_id = ?;
            """);
      statement.setInt(1, studentNo);
      ResultSet result = statement.executeQuery();
      ArrayList<Result> answer = new ArrayList<>();
      while (result.next()){
        answer.add(new Result(
            Grade.findGrade(result.getInt("grade")),
            result.getString("feedback"),
            readExamBySerialId(result.getInt("id"))
        ));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Teacher readTeacherByInitials(String initials){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE teacher_id = ?");
      statement.setString(1, initials);
      ResultSet result = statement.executeQuery();
      Teacher answer = null;
      if(result.next()){
        answer = new Teacher(result.getString("teacher_id"), result.getString("name"), result.getString("password"));
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Exam> readExamByCourse(Course course){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM exams WHERE course_code = ?;");
      statement.setString(1, course.getCode());
      ResultSet result = statement.executeQuery();
      ArrayList<Exam> answer = new ArrayList<>();
      while(result.next()){
        answer.add(new Exam(
            result.getString("title"),
            result.getString("content"),
            result.getString("room"),
            course,
            result.getDate("date").toLocalDate(),
            result.getTime("time").toLocalTime(),
            result.getBoolean("completed")
        ));
      }

      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  public Exam readExamBySerialId(int id){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM exams WHERE id = ?;");
      statement.setInt(1, id);
      ResultSet result = statement.executeQuery();
      Exam answer = null;
      if(result.next()){
        answer = new Exam(
            result.getString("title"),
            result.getString("content"),
            result.getString("room"),
            readCourseByCode(result.getString("course_code")),
            result.getDate("date").toLocalDate(),
            result.getTime("time").toLocalTime(),
            result.getBoolean("completed")
        );
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  public Course readCourseByCode(String code){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM courses WHERE code = ?;");
      statement.setString(1, code);
      ResultSet result = statement.executeQuery();
      Course answer = null;
      if(result.next()){

        answer = new Course(
            result.getString("code"),
            result.getString("title"),
            result.getString("description"),
            readTeacherByInitials(result.getString("teacher_1"))
        );

        answer.addStudents(readStudentsFromCourse(code).toArray(new Student[0]));

        if(!readExamByCourse(answer).isEmpty())
          for(Exam temp : readExamByCourse(answer)) answer.addExam(temp);
      }
      return answer;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master","postgres","ViaViaVia");
  }

}
