package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The ExamDAO interface provides methods for accessing and managing exams,
 * for example creating a new exam or editing an existing one.
 */
public interface ExamDAO {

  /**
   * Marks the specified exam as completed.
   *
   * @param exam the exam to mark as completed
   */
  void markExamCompleted(Exam exam);

  /**
   * Retrieves a list of exams that a given student (who is specified using a numerical ID) is enrolled in.
   *
   * @param studentId the ID of the student
   * @return a list of exams associated with the given student ID
   */
  List<Exam> getExamsByStudentID(int studentId);

  /**
   * Creates a new exam with the specified details.
   *
   * @param title     the title of the exam
   * @param content   the content of the exam
   * @param room      the room of the exam
   * @param course    the course associated with the exam
   * @param date      the date of the exam
   * @param time      the time of the exam
   * @param written   specifies if the exam is written
   * @param examiners the type of examiners grading the exam
   * @param students  the students enrolled in the exam
   * @return the created exam
   * @throws SQLException if an SQL exception occurs
   */
  Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws SQLException;

  /**
   * Creates an announcement for the specified exam.
   *
   * @param title   the title of the announcement
   * @param content the content of the announcement
   * @param exam    the exam for which the announcement is created
   * @return the created announcement
   */
  Announcement createAnnouncement(String title, String content, Exam exam);

  /**
   * Edits the specified exam with the new details.
   *
   * @param id        the ID of the exam to edit
   * @param title     the new title of the exam
   * @param content   the new content of the exam
   * @param room      the new room of the exam
   * @param course    the new course associated with the exam
   * @param date      the new date of the exam
   * @param time      the new time of the exam
   * @param written   specifies if the exam is written
   * @param examiners the new type of examiners grading the exam
   * @param students  the new students enrolled in the exam
   * @return          the edited exam
   * @throws SQLException if an SQL exception occurs
   */
  Exam editExam(int id, String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws SQLException;

  /**
   * Retrieves a list of exams associated with the specified course.
   *
   * @param course the course associated with the exams
   * @return a list of exams associated with the specified course
   * @throws SQLException if an SQL exception occurs
   */
  List<Exam> getExamsByCourse(Course course) throws SQLException;

  /**
   * Deletes the exam with the specified ID.
   *
   * @param id the ID of the exam to delete
   */
  void deleteExam(int id);
}
