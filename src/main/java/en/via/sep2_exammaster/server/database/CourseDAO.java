package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO {
  Course createCourse(
      String code, int semester,
      String title, String description,
      Teacher primaryTeacher, Teacher additionalTeacher,
      List<Student> students
  )
      throws SQLException;
  Course getCourseByCode(String courseCode);
  List<Course> getCourses();
}
