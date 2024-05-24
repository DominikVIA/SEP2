package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The Exam class represents an exam (obviously).
 * <p>
 * An Exam object contains the following information:
 * a unique numerical ID, a title, the contents of the exam, the room the exam takes place in,
 * the date of the exam, the time of the exam's start, the course the exam is a part of,
 * a list of students participating in an exam (required as in the cases of re-exams the list of students might be different,
 * SEP exams are also made up of different groups of students), a list of announcements associated with the exam,
 * a boolean whether an exam is written or not (then it is an oral exam), a boolean whether an exam is completed
 * or not (decides whether results can be added and whether an exam can be edited), the type of examiners (Internal, External, Both).
 */
public class Exam implements Serializable {

  /** The unique numerical ID of the exam. */
  private int id;

  /** The exam title. */
  private String title;

  /** The content or description of the exam. */
  private String content;

  /** The room where the exam will take place. */
  private String room;

  /** The date of the exam. */
  private LocalDate date;

  /** The time of the exam. */
  private LocalTime time;

  /** The course associated with the exam. */
  private Course course;

  /** The list of students enrolled for the exam. */
  private ArrayList<Student> students;

  /** The list of announcements related to the exam. */
  private ArrayList<Announcement> announcements;

  /** Indicates whether the exam is a written exam or an oral exam. */
  private boolean written;

  /** Indicates whether the exam has been completed. */
  private boolean completed;

  /** The type of examiners (Internal, External, Both). */
  private Examiners examiners;

  /**
   * Constructs a new Exam with the specified attributes.
   *
   * @param id        the unique identifier of the exam
   * @param title     the title of the exam
   * @param content   the content or description of the exam
   * @param room      the room where the exam will take place
   * @param course    the course associated with the exam
   * @param date      the date of the exam
   * @param time      the time of the exam
   * @param written   indicates whether the exam is a written exam
   * @param examiners the type of examiners of the exam (the same 3 we've repeated twice before now)
   */
  public Exam(int id, String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners) {
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

  /**
   * Returns the unique identifier of the exam.
   *
   * @return the unique identifier of the exam
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the title of the exam.
   *
   * @return the title of the exam
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the content or description of the exam.
   *
   * @return the content or description of the exam
   */
  public String getContent() {
    return content;
  }

  /**
   * Returns the room where the exam will take place.
   *
   * @return the room where the exam will take place
   */
  public String getRoom() {
    return room;
  }

  /**
   * Returns the date of the exam.
   *
   * @return the date of the exam
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Returns the time of the exam.
   *
   * @return the time of the exam
   */
  public LocalTime getTime() {
    return time;
  }

  /**
   * Returns the list of students enrolled for the exam.
   *
   * @return the list of students enrolled for the exam
   */
  public ArrayList<Student> getStudents() {
    return students;
  }

  /**
   * Returns the course associated with the exam.
   *
   * @return the course associated with the exam
   */
  public Course getCourse() {
    return course;
  }

  /**
   * Sets the course of the exam.
   *
   * @param course the course of the exam
   */
  public void setCourse(Course course)
  {
    this.course = course;
  }

  /**
   * Returns true if the exam is a written exam, false otherwise.
   *
   * @return true if the exam is a written exam, false otherwise
   */
  public boolean isWritten() {
    return written;
  }

  /**
   * Returns the examiners responsible for conducting the exam.
   *
   * @return the examiners responsible for conducting the exam
   */
  public Examiners getExaminers() {
    return examiners;
  }

  /**
   * Adds announcements related to the exam.
   *
   * @param announcements the announcements to be added
   */
  public void addAnnouncements(Announcement... announcements) {
    this.announcements.addAll(List.of(announcements));
  }

  /**
   * Returns the list of announcements related to the exam.
   *
   * @return the list of announcements related to the exam
   */
  public ArrayList<Announcement> getAnnouncements() {
    return announcements;
  }

  /**
   * Adds a student to the list of students enrolled for the exam.
   *
   * @param student the student to be added
   */
  public void addStudent(Student student) {
    students.add(student);
  }

  /**
   * Adds multiple students to the list of students enrolled for the exam.
   *
   * @param students the students to be added
   */
  public void addStudents(Student... students) {
    this.students.addAll(List.of(students));
  }

  /**
   * Sets the completion status of the exam.
   *
   * @param completed the completion status of the exam
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * Returns true if the exam has been completed, false otherwise.
   *
   * @return true if the exam has been completed, false otherwise
   */
  public boolean isCompleted() {
    return completed;
  }

  /**
   * Returns a string representation of the Exam object.
   * <p>
   * The string representation consists of the title of the exam followed by the date and time of the exam.
   *
   * @return a string representation of the Exam object
   */
  @Override
  public String toString() {
    return title + " | " + date + ", " + time;
  }

  /**
   * Indicates whether some other object is equal to <b>this</b> one.
   * <p>
   * Two Exam objects are considered equal if they have the same ID.
   *
   * @param obj the reference object with which to compare
   * @return true if <b>this</b> object is the same class as the given <b>obj</b> and both have the same ; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != getClass()) return false;
    Exam temp = (Exam) obj;
    return id == temp.id;
  }
}

