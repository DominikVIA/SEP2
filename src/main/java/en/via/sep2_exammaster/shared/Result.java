package en.via.sep2_exammaster.shared;

import java.io.Serializable;

public class Result implements Serializable
{
  private Grade grade;
  private String feedback;
  private Exam exam;

  public Result(Grade grade, String feedback, Exam exam){
    this.grade = grade;
    this.feedback = feedback;
    this.exam = exam;
  }

  public Grade getGrade(){
    return grade;
  }

  public String getFeedback(){
    return feedback;
  }

  public Exam getExam(){
    return exam;
  }

  public String toString(){
    return exam.toString();
  }

//  public String toString(){
//    return exam + (exam.isCompleted() ? " and student received a(n) " + grade + " | " + (feedback != null ? "feedback: " + feedback : "feedback has not been added yet.") : "");
//  }
}
