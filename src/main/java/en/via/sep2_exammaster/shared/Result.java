package en.via.sep2_exammaster.shared;

public class Result {
  private Grade grade;
  private Student student;
  private Exam exam;

  public Result(Grade grade, Student student, Exam exam){
    this.grade = grade;
    this.student = student;
    this.exam = exam;
  }
}
