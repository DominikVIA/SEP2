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
  Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students);
  List<User> readAllUsers();
  Student readStudent(int studentID);
  Teacher readTeacher(String initials);

}
