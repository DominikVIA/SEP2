package en.via.sep2_exammaster.shared;

public class Result {
  private Grade grade;
  private Exam exam;

  public Result(Grade grade, Exam exam){
    this.grade = grade;
    this.exam = exam;
  }

  public String toString(){
    return exam + " and student received a(n) " + grade;
  }
}
