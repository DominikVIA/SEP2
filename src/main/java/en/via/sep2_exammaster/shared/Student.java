package en.via.sep2_exammaster.shared;

import java.util.ArrayList;

public class Student extends User {
  private int studentNo;
  private ArrayList<Result> results;

  public Student(int studentNo, String name, String password){
    super(name, password);
    this.studentNo = studentNo;
    this.results = new ArrayList<>();
  }

  public int getStudentNo(){
    return studentNo;
  }

  public void newResult(Result result){
    results.add(result);
  }

  public String toString(){
    return studentNo + " " + super.toString();
  }
}
