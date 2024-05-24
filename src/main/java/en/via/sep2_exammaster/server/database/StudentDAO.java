package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Student;

import java.util.List;

/**
 * The StudentDAO interface provides methods for accessing Student data,
 * namely getting all students and getting a single student with a specific ID.
 */
public interface StudentDAO {
  /**
   * Retrieves a list of all students.
   *
   * @return a list of all students
   */
  List<Student> getAllStudents();

  /**
   * Retrieves a student with the specified student number.
   *
   * @param studentNo the student number of the student to retrieve
   * @return the student with the specified student number, or null if not found
   */
  Student getStudentByStudentNo(int studentNo);
}

