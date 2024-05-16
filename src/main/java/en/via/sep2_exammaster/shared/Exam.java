package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exam implements Serializable
{
  private String title, content, room;
  private LocalDate date;
  private LocalTime time;
  private Course course;
  private ArrayList<Student> students;
  private ArrayList<Announcement> announcements;
  private boolean written, completed;
  private Examiners examiners;

  public Exam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners){
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

  public Exam(String title, String content, String room, LocalDate date, LocalTime time){
    this.title = title;
    this.content = content;
    this.room = room;
    this.date = date;
    this.time = time;
    this.course = null;
    this.completed = false;
    this.students = new ArrayList<>();
    this.announcements = new ArrayList<>();
  }

  public Exam(Exam exam, Course course){
    this.title = exam.getTitle();
    this.content = exam.getContent();
    this.room = exam.getRoom();
    this.course = course;
    this.date = exam.getDate();
    this.time = exam.getTime();
    this.completed = exam.isCompleted();
    this.students = exam.getStudents();
    this.announcements = exam.getAnnouncements();
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
}
