package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.shared.User;

import java.sql.SQLException;
import java.util.List;

public interface Database {
  Course createCourse(String code, int semester, String title, String description, Teacher primaryTeacher, String additionalTeacher, List<Student> students)
      throws SQLException;
  Course editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher,
      String additionalTeacherInitials, List<Student> students) throws SQLException;
  List<User> readAllUsers();

  void deleteCourse(String code);
  Student readStudent(int studentID);
  Teacher readTeacher(String initials);

}
