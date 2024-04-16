package en.via.sep2_exammaster.shared;

import java.util.ArrayList;
import java.util.Arrays;

public class Course
{
  private String code;
  private String title;
  private String description;
  private Teacher teacher;
  private ArrayList<Student> students;
  private ArrayList<Exam> exams; // maybe should be a normal array, so that there
                                 // can only be 3 exams per class (exam, 2 re-exams)
  public Course(String code, String title, String description, Teacher teacher){
    this.code = code;
    this.title = title;
    this.description = description;
    this.teacher = teacher;
    this.students = new ArrayList<>();
    this.exams = new ArrayList<>();
  }

  public Course(String code, String title, String description, Teacher teacher, Student...students){
    this.code = code;
    this.title = title;
    this.description = description;
    this.teacher = teacher;
    this.students = new ArrayList<>(Arrays.asList(students));;
    this.exams = new ArrayList<>();
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

  public void createExam(Exam exam){
    exams.add(new Exam(exam, this));
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
        + "Class teacher: " + teacher + "\n"
        + "~~~~~~~~~~ Students ~~~~~~~~~~\n"
        + students.toString() + "\n"
        + answer;
  }
}
