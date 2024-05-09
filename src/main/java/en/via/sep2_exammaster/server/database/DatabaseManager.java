package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.shared.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements Database {
  static private DatabaseManager instance;
  private CourseDAO courses;
  private ExamDAO exams;
  private ResultDAO results;
  private StudentDAO students;
  private TeacherDAO teachers;

  private DatabaseManager() throws SQLException {
    courses = CourseDAOImpl.getInstance();
    exams = ExamDAOImpl.getInstance();
    results = ResultDAOImpl.getInstance();
    students = StudentDAOImpl.getInstance();
    teachers = TeacherDAOImpl.getInstance();
  }

  public static DatabaseManager getInstance() throws SQLException {
    if(instance == null) instance = new DatabaseManager();
    return instance;
  }

  @Override
  public List<User> readAllUsers() {
    ArrayList<User> answer = new ArrayList<>();
    answer.addAll(students.readAllStudents());
    answer.addAll(teachers.readAllTeachers());
    return answer;
  }

  @Override
  public List<Student> readAllStudents(){
    return students.readAllStudents();
  }

  @Override
  public List<Teacher> readAllTeachers(){
    return teachers.readAllTeachers();
  }
}