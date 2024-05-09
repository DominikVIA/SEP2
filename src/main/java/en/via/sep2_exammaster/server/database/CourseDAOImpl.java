package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
  private static CourseDAOImpl instance;

  private CourseDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized CourseDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new CourseDAOImpl();
    return instance;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }

  @Override
  public Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, Teacher additionalTeacher, List<Student> students) throws SQLException {
    Connection connection = getConnection();
    try{
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement("INSERT INTO courses VALUES (?, ?, ?, ?);");
      statement.setString(1, code);
      statement.setInt(2, semester);
      statement.setString(3, title);
      statement.setString(4, description);
      statement.execute();

      Course course = new Course(code, semester, title, description, primaryTeacher);
      statement = connection.prepareStatement("INSERT INTO course_teachers VALUES (?, ?);");
      statement.setString(1, code);
      statement.setString(2, primaryTeacher.getInitials());
      statement.execute();

      if(additionalTeacher != null){
        course.addAdditionalTeacher(additionalTeacher);
        statement.setString(1, code);
        statement.setString(2, additionalTeacher.getInitials());
        statement.execute();
      }

      for(Student student : students) {
        course.addStudent(student);
        statement = connection.prepareStatement(
            "INSERT INTO course_students VALUES (?, ?);");
        statement.setString(1, code);
        statement.setInt(2, student.getStudentNo());
        statement.execute();
      }
      connection.commit();
      return course;
    }
    catch (SQLException e) {
      connection.rollback();
      e.printStackTrace();
      return null;
    }
    finally {
      connection.close();
    }
  }

  @Override
  public Course getCourseByCode(String courseCode){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
          SELECT * FROM courses
              JOIN course_teachers ct ON courses.code = ct.course_code
              JOIN teachers t ON ct.teacher = t.teacher_id
          WHERE code = ?;
          """);
      statement.setString(1, courseCode);
      ResultSet result = statement.executeQuery();

      Course course = null;
      if (result.next()){

        String code = result.getString(1);
        int semester = result.getInt(2);
        String title = result.getString(3);
        String description = result.getString(4);
        Teacher primary = TeacherDAOImpl.createTeacher(result);

        course = new Course(code, semester, title, description, primary);

        if (result.next()){
          Teacher additional = TeacherDAOImpl.createTeacher(result);
          course.addAdditionalTeacher(additional);
        }

        course.addExams(ExamDAOImpl.getInstance().getExamsByCourse(course).toArray(new Exam[0]));
        course.addStudents(StudentDAOImpl.getInstance().readStudentsFromCourse(course).toArray(new Student[0]));
      }
      return course;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Course> getCourses(){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("""
          SELECT * FROM courses;
          """);
      ResultSet result = statement.executeQuery();
      ArrayList<Course> list = new ArrayList<>();
      while (result.next()){
        list.add(getCourseByCode(result.getString(1)));
      }
      return list;
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
