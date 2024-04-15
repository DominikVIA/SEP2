package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Exam {
  private LocalDate date;
  private LocalTime time;

  public Exam(LocalDate date, LocalTime time){
    this.date = date;
    this.time = time;
  }
}
