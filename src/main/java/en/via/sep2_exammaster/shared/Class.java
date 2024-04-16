package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Class {
  private String title;
  private String description;
  private Teacher teacher;
  private StudentList students;
  private ArrayList<Exam> exams; // maybe should be a normal array, so that there
                                 // can only be 3 exams per class (exam, 2 re-exams)
  public Class(String title, String description, Teacher teacher){
    this.title = title;
    this.description = description;
    this.teacher = teacher;
    this.students = new StudentList();
    this.exams = new ArrayList<>();
  }

  public void addStudent(Student student){
    students.add(student);
  }

  public String getTitle(){
    return title;
  }

  public void createExam(Exam exam){
    exams.add(new Exam(exam, this));
  }

  public String toString(){
    return "Class title: " + title + "\n"
        + "Class description: " + description + "\n"
        + "Class teacher: " + teacher + "\n"
        + "~~~~~~~~~~ Students ~~~~~~~~~~\n"
        + students.toString() + "\n"
        + exams.get(0);
  }
}
