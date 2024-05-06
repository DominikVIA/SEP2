package en.via.sep2_exammaster.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course
{
  private String code;
  private String title;
  private String description;
  private Teacher[] teachers;
  private ArrayList<Student> students;
  private ArrayList<Exam> exams;

  public Course(String code, String title, String description, Teacher teacherCreating){
    this.code = code;
    this.title = title;
    this.description = description;
    this.teachers = new Teacher[2];
    this.teachers[0] = teacherCreating;
    this.students = new ArrayList<>();
    this.exams = new ArrayList<>();
  }

  public void addAdditionalTeacher(Teacher teacher){
    teachers[1] = teacher;
  }

  public void addStudents(Student...students){
    this.students.addAll(List.of(students));
  }

  public void createExam(Exam exam){
    exams.add(new Exam(exam, this));
  }

  public void addExam(Exam exam){
    exams.add(exam);
  }

  public String getCode(){
    return code;
  }

  public String getDescription(){
    return description;
  }

  public Teacher getTeacher(int index){
    if(index < 0 || index > 1) throw new IndexOutOfBoundsException("A course can only have 2 teachers so you must use index 0 or 1.");
    return teachers[index];
  }

  public void addStudent(Student student){
    students.add(student);
  }

  public String getTitle(){
    return title;
  }

  public ArrayList<Student> getStudents(){
    return students;
  }

  public ArrayList<Exam> getExams(){
    return exams;
  }

  public String toString(){
    String answer = "";
    for(Exam temp : exams){
      answer += temp + "\n";
    }
    return "Class title: " + title + "\n"
        + "Class description: " + description + "\n"
        + "Class teacher: " + teachers[0] + "\n"
        + "~~~~~~~~~~ Students ~~~~~~~~~~\n"
        + students.toString() + "\n"
        + "~~~~~~~~~~ Exams ~~~~~~~~~~\n"
        + answer;
  }
}
