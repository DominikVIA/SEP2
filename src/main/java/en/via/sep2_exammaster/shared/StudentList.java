package en.via.sep2_exammaster.shared;

import java.util.ArrayList;

public class StudentList {
  private ArrayList<Student> students;

  public StudentList(){
    students = new ArrayList<>();
  }

  public void add(Student student){
    students.add(student);
  }

  public int size(){
    return students.size();
  }

  public Student get(int index){
    return students.get(index);
  }

  public String toString(){
    String answer = "";
    for(int i = 0; i < students.size(); i++){
      answer += (i + 1) + ". student: " + students.get(i) + "\n";
    }
    answer = answer.substring(0, answer.length() - 1);
    return answer;
  }
}
