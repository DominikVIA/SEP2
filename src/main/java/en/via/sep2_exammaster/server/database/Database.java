package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface Database {
  Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, String additionalTeacher, List<Student> students)
      throws SQLException;
  Course editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher,
      String additionalTeacherInitials, List<Student> students) throws SQLException;
  void deleteCourse(String code);
  void markExamCompleted(Exam exam);
  Exam createExam(
      String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students
  ) throws SQLException;
  Exam editExam(
      int id, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students
  ) throws SQLException;
  void deleteExam(int id);
  Announcement createAnnouncement(String title, String content, Exam exam);
  Result getStudentResultByExamId(Exam exam, Student student);
  Result editResult(Student student, Exam exam, Grade grade, String feedback);
  List<User> readAllUsers();
  Student readStudent(int studentID);
  Teacher readTeacher(String initials);

}
