package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Exam {
  private LocalDate date;
  private LocalTime time;
  private Course course;
  private ArrayList<Student> students;
  private boolean completed;

  public Exam(LocalDate date, LocalTime time, Student...students){
    this.date = date;
    this.time = time;
    this.completed = false;
    if(students == null){
      this.students = course.getStudents();
    }
    else this.students = new ArrayList<>(Arrays.asList(students));
  }

  public Exam(Exam exam, Course course){
    this.date = exam.getDate();
    this.time = exam.getTime();
    this.completed = exam.isCompleted();
    this.course = course;
    this.students = course.getStudents();
  }

  public void setClass(Course course){
    this.course = course;
  }

  public LocalDate getDate(){
    return date;
  }

  public LocalTime getTime(){
    return time;
  }

  public ArrayList<Student> getStudents(){
    return students;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void complete(){
    completed = true;
  }

  public String toString(){
    return completed ? "A(n) " + course.getTitle() + " exam happened on " + date + " | " + time : "A(n) " + course.getTitle() + " exam will happen on " + date + " | " + time;
  }
}
