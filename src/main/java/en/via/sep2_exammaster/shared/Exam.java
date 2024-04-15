package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Exam {
  private LocalDate date;
  private LocalTime time;
  private boolean completed;

  public Exam(LocalDate date, LocalTime time){
    this.date = date;
    this.time = time;
    this.completed = false;
  }

  public void complete(){
    completed = true;
  }

  public String toString(){
    return completed ? "An exam happened on " + date + " | " + time : "An exam will happen on " + date + " | " + time;
  }
}
