package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Exam {
  private String title, content, room;
  private LocalDate date;
  private LocalTime time;
  private Course course;
  private ArrayList<Student> students;
  private ArrayList<Announcement> announcements;
  private boolean completed;

  public Exam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean completed){
    this.title = title;
    this.content = content;
    this.room = room;
    this.course = course;
    this.date = date;
    this.time = time;
    this.completed = completed;
    this.students = new ArrayList<>();
    this.announcements = new ArrayList<>();
  }

  public Exam(String title, String content, String room, LocalDate date, LocalTime time, boolean completed){
    this.title = title;
    this.content = content;
    this.room = room;
    this.date = date;
    this.time = time;
    this.course = null;
    this.completed = completed;
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

  public ArrayList<Announcement> getAnnouncements(){
    return announcements;
  }

  public void addStudent(Student student){
    students.add(student);
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
    return completed ? "A(n) " + course.getTitle() + " exam happened on " + date + " | " + time : "A(n) " + course.getTitle() + " exam will happen on " + date + " | " + time;
  }
}
