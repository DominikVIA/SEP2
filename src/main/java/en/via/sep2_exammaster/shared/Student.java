package en.via.sep2_exammaster.shared;

import java.util.ArrayList;

public class Student {
  private int id;
  private String name;
  private ArrayList<Result> examResults;

  public Student(int id, String name){
    this.id = id;
    this.name = name;
    this.examResults = new ArrayList<>();
  }

  public void newResult(Result result){
    examResults.add(result);
  }

  public String toString(){
    String answer = "";
    for(Result temp : examResults){
      answer += temp;
    }
    return id + " - " + name + "\n"+
        "student's exams\n" + answer;
  }
}
