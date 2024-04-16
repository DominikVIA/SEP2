package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Exam {
  private LocalDate date;
  private LocalTime time;
  private Class aClass;
  private boolean completed;

  public Exam(LocalDate date, LocalTime time){
    this.date = date;
    this.time = time;
    this.completed = false;
  }

  public Exam(Exam exam, Class aClass){
    this.date = exam.getDate();
    this.time = exam.getTime();
    this.completed = exam.isCompleted();
    this.aClass = aClass;
  }

  public void setClass(Class aClass){
    this.aClass = aClass;
  }

  public LocalDate getDate(){
    return date;
  }

  public LocalTime getTime(){
    return time;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void complete(){
    completed = true;
  }

  public String toString(){
    return completed ? "A(n) " + aClass.getTitle() + " exam happened on " + date + " | " + time : "A(n) " + aClass.getTitle() + " exam will happen on " + date + " | " + time;
  }
}
