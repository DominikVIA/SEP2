package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Result;
import en.via.sep2_exammaster.shared.Student;

import java.util.List;

/**
 * The ResultDAO interface provides methods for accessing and managing exam results,
 * for example getting all results or editing a result.
 */
public interface ResultDAO {
  /**
   * Retrieves a list of students enrolled in the specified exam.
   *
   * @param examID the ID of the exam
   * @return a list of students enrolled in the specified exam
   */
  List<Student> readStudentsEnrolledInExam(int examID);

  /**
   * Retrieves a list of results for the specified exam.
   *
   * @param exam the exam
   * @return a list of results for the specified exam
   */
  List<Result> getResultsByExam(Exam exam);

  /**
   * Retrieves a list of results for the specified student ID.
   *
   * @param studentId the ID of the student
   * @return a list of results for the specified student ID
   */
  List<Result> getResultsByStudentID(int studentId);

  /**
   * Retrieves the result of the specified student for the specified exam.
   *
   * @param exam    the exam
   * @param student the student
   * @return the result of the specified student for the specified exam
   */
  Result getStudentResultByExamId(Exam exam, Student student);

  /**
   * Edits the result of the specified student for the specified exam.
   *
   * @param student  the student
   * @param exam     the exam
   * @param grade    the new grade
   * @param feedback the new feedback
   * @return the updated result
   */
  Result editResult(Student student, Exam exam, Grade grade, String feedback);
}

