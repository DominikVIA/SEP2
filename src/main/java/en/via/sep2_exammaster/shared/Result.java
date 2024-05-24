package en.via.sep2_exammaster.shared;

import java.io.Serializable;

/**
 * The Result class represents a student's exam results.
 * The object itself includes the grade, feedback, and the associated exam.
 */
public class Result implements Serializable {

  /** The grade achieved in the exam. */
  private final Grade grade;

  /** Feedback provided by the examiner. */
  private final String feedback;

  /** The exam associated with this result. */
  private final Exam exam;

  /**
   * Constructs a new Result with the specified grade, feedback, and exam.
   *
   * @param grade    the grade achieved in the exam
   * @param feedback feedback provided by the examiner
   * @param exam     the exam associated with this result
   */
  public Result(Grade grade, String feedback, Exam exam) {
    this.grade = grade;
    this.feedback = feedback;
    this.exam = exam;
  }

  /**
   * Returns the grade achieved in the exam.
   *
   * @return the grade achieved in the exam
   */
  public Grade getGrade() {
    return grade;
  }

  /**
   * Returns the feedback provided by the examiner.
   *
   * @return the feedback provided by the examiner
   */
  public String getFeedback() {
    return feedback;
  }

  /**
   * Returns the exam associated with this result.
   *
   * @return the exam associated with this result
   */
  public Exam getExam() {
    return exam;
  }

  /**
   * Returns a string representation of the Result object.
   * The returned string is made up of a string representation of the associated exam.
   *
   * @return a string representation of the Result object (basically just a string representation of the associated Exam object)
   */
  @Override
  public String toString() {
    return exam.toString();
  }
}