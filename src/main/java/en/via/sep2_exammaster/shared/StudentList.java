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
}
