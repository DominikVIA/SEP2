package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exam implements Serializable {
  private int id;
  private String title, content, room;
  private LocalDate date;
  private LocalTime time;
  private Course course;
  private ArrayList<Student> students;
  private ArrayList<Announcement> announcements;
  private boolean written, completed;
  private Examiners examiners;

  public Exam(int id, String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners){
    this.id = id;
    this.title = title;
    this.content = content;
    this.room = room;
    this.course = course;
    this.date = date;
    this.time = time;
    this.completed = false;
    this.written = written;
    this.examiners = examiners;
    this.students = new ArrayList<>();
    this.announcements = new ArrayList<>();
  }

  public int getId(){
    return id;
  }

  public String getTitle(){
    return title;
  }

  public String getContent(){
    return content;
  }

  public String getRoom(){
    return room;
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

  public Course getCourse(){
    return course;
  }

  public boolean isWritten(){
    return written;
  }

  public Examiners getExaminers(){
    return examiners;
  }

  public void addAnnouncements(Announcement...announcements){
    this.announcements.addAll(List.of(announcements));
  }

  public ArrayList<Announcement> getAnnouncements(){
    return announcements;
  }

  public void addStudent(Student student){
    students.add(student);
  }

  public void addStudents(Student...students){
    this.students.addAll(List.of(students));
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public void setCourse(Course course){
    this.course = course;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void complete(){
    completed = true;
  }

  public String toString(){
      return title + " (" + date + ", " + time + ")";
  }

  @Override public boolean equals(Object obj) {
    if(obj == null || obj.getClass() != getClass()) return false;
    Exam temp = (Exam) obj;
    return id == temp.id;
  }
}
