package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Student extends User
{
  private int studentID;
  private ArrayList<Result> results;

  public Student(int studentID, String name, String password){
    super(name, password);
    this.studentID = studentID;
    this.results = new ArrayList<>();
  }

  public int getStudentNo(){
    return studentID;
  }

  public void newResult(Result result){
    results.add(result);
  }

  public String toString(){
    return studentID + " " + super.toString();
  }
}
