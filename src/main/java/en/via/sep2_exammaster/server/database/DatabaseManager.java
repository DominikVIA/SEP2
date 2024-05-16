package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
  public Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, String additionalTeacherInitials, List<Student> students)
      throws SQLException
  {
    Teacher additionalTeacher = null;
    if(additionalTeacherInitials != null && !additionalTeacherInitials.isBlank()) {
      additionalTeacher = teachers.readTeacherByInitials(
          additionalTeacherInitials);
      if(additionalTeacher == null) throw new IllegalArgumentException("teacher initials incorrect");
    }
    return courses.createCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
  }

  @Override
  public Course editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher,
      String additionalTeacherInitials, List<Student> students) throws SQLException
  {
    Teacher additionalTeacher = null;
    if(additionalTeacherInitials != null && !additionalTeacherInitials.isBlank()) {
      additionalTeacher = teachers.readTeacherByInitials(
          additionalTeacherInitials);
      if(additionalTeacher == null) throw new IllegalArgumentException("teacher initials incorrect");
    }
    return courses.editCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
  }

  @Override
  public void deleteCourse(String code){
    courses.deleteCourse(code);
  }

  @Override
  public Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students){
    return exams.createExam(title, content, room, course, date, time, written, examiners, students);
  }

  @Override
  public List<User> readAllUsers() {
    ArrayList<User> answer = new ArrayList<>();
    answer.addAll(students.readAllStudents());
    answer.addAll(teachers.readAllTeachers());
    return answer;
  }

  @Override
  public Student readStudent(int studentID){
    return students.readStudentByStudentNo(studentID);
  }

  @Override
  public Teacher readTeacher(String initials){
    return teachers.readTeacherByInitials(initials);
  }
}
