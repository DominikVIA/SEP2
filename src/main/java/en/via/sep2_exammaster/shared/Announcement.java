package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Announcement implements Serializable {
  private int id;
  private String title, content;
  private LocalDate date;
  private LocalTime time;

  public Announcement(int id, String title, String content, LocalDate date, LocalTime time){
    this.id = id;
    this.title = title;
    this.content = content;
    this.date = date;
    this.time = time;
  }

  public String getTitle(){
    return title;
  }

  public String getContent(){
    return content;
  }

  public LocalDate getDate(){
    return date;
  }

  public LocalTime getTime(){
    return time;
  }

  @Override public String toString() {
    return date + " - " + title;
  }
}
