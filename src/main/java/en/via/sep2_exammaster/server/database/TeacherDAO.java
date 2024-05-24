package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Teacher;

import java.util.List;

/**
 * The TeacherDAO interface provides methods for accessing and managing Teacher data,
 * namely, getting all teachers in the database and getting a single teacher by initials.
 */
public interface TeacherDAO {
  /**
   * Retrieves a list of all teachers.
   *
   * @return a list of all teachers
   */
  List<Teacher> getAllTeachers();

  /**
   * Retrieves a teacher with the specified initials.
   *
   * @param initials the initials of the teacher to retrieve
   * @return the teacher with the specified initials, or null if not found
   */
  Teacher getTeacherByInitials(String initials);
}

