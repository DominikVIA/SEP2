package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;

import java.sql.SQLException;
import java.util.List;

/**
 * The CourseDAO interface provides methods for accessing and managing courses,
 * for example creating a new course or editing an existing one.
 */
public interface CourseDAO {

  /**
   * Creates a new course with the specified details.
   *
   * @param code              the course code (cannot exceed 12 characters of length).
   * @param semester          the semester of the course (must be a number between 1 and 9 inclusive).
   * @param title             the title of the course
   * @param description       the description of the course
   * @param primaryTeacher    the primary teacher for the course
   * @param additionalTeacher the additional teacher for the course
   * @param students          the students enrolled in the course
   * @return the created course
   * @throws SQLException if an SQL exception occurs
   */
  Course createCourse(
      String code, int semester,
      String title, String description,
      Teacher primaryTeacher, Teacher additionalTeacher,
      List<Student> students
  )
      throws SQLException;

  /**
   * Edits the specified course with the new details.
   *
   * @param code              the code of the course to be edited
   * @param semester          the new semester of the course
   * @param title             the new title of the course
   * @param description       the new description of the course
   * @param primaryTeacher    the new primary teacher for the course
   * @param additionalTeacher the new additional teacher for the course
   * @param students          the new list of students enrolled in the course
   * @return the edited course
   * @throws SQLException if an SQL exception occurs
   */
  Course editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher,
      Teacher additionalTeacher, List<Student> students)
      throws SQLException;

  /**
   * Retrieves the course with the specified code.
   *
   * @param courseCode          the code of the course
   * @param withExtraInformation specifies if extra information should be retrieved along with the course,
   *                             namely an ArrayList of enrolled students and an ArrayList of associated exams
   *
   * @return the course with the specified code
   */
  Course getCourseByCode(String courseCode, boolean withExtraInformation);

  /**
   * Retrieves a list of all courses.
   *
   * @return a list of all courses
   */
  List<Course> getCourses();

  /**
   * Deletes the course with the specified code.
   *
   * @param code the code of the course to delete
   */
  void deleteCourse(String code);
}

