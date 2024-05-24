package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Course class represents a course taught at VIA.
 * <p>
 * It consists of a code (in the ViewControllers and database restricted to only 12 characters of length),
 * a title, a description, a semester (restricted in ViewControllers to only be between 1 and 9 due to GBE studies),
 * an array of maximum 2 teachers (primary and an additional teacher, like in the case of SEP),
 * an ArrayList of students representing enrolled students and lastly
 * an Arraylist of exams representing exams part of the course.
 */
public class Course implements Serializable {

  /** The code of the course. */
  private String code;

  /** The title of the course. */
  private String title;

  /** The description of the course. */
  private String description;

  /** The semester of the course. */
  private int semester;

  /** An array of teachers associated with the course. */
  private Teacher[] teachers;

  /** A list of students enrolled in the course. */
  private ArrayList<Student> students;

  /** A list of exams scheduled for the course. */
  private ArrayList<Exam> exams;

  /**
   * Constructs a new Course with the specified code, semester, title, description, and teacher creating the course.
   *
   * @param code           the code of the course
   * @param semester       the semester in which the course is offered
   * @param title          the title of the course
   * @param description    the description of the course
   * @param teacherCreating the teacher creating the course
   */
  public Course(String code, int semester, String title, String description, Teacher teacherCreating) {
    this.code = code;
    this.semester = semester;
    this.title = title;
    this.description = description;
    this.teachers = new Teacher[2];
    this.teachers[0] = teacherCreating;
    this.students = new ArrayList<>();
    this.exams = new ArrayList<>();
  }

  /**
   * Adds an additional teacher to the course.
   *
   * @param teacher the additional teacher to be added
   */
  public void addAdditionalTeacher(Teacher teacher) {
    teachers[1] = teacher;
  }

  /**
   * Adds multiple students to the course.
   *
   * @param students the students to be added
   */
  public void addStudents(Student... students) {
    this.students.addAll(List.of(students));
  }

  /**
   * Adds a student to the course.
   *
   * @param student the student to be added
   */
  public void addStudent(Student student) {
    students.add(student);
  }

  /**
   * Adds an exam to the course.
   *
   * @param exam the exam to be added
   */
  public void addExam(Exam exam) {
    exams.add(exam);
  }

  /**
   * Deletes an exam from the course.
   *
   * @param exam the exam to be deleted
   */
  public void deleteExam(Exam exam) {
    exams.remove(exam);
  }

  /**
   * Adds multiple exams to the course.
   *
   * @param exams the exams to be added
   */
  public void addExams(Exam... exams) {
    this.exams.addAll(List.of(exams));
  }

  /**
   * Returns the code of the course.
   *
   * @return the code of the course
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the semester in which the course is offered.
   *
   * @return the semester in which the course is offered
   */
  public int getSemester() {
    return semester;
  }

  /**
   * Returns the description of the course.
   *
   * @return the description of the course
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the teacher at the specified index.
   *
   * @param index the index of the teacher (0 or 1)
   * @return the teacher at the specified index
   * @throws IndexOutOfBoundsException if the index is invalid (not 0 or 1)
   */
  public Teacher getTeacher(int index) {
    if (index < 0 || index > 1)
      throw new IndexOutOfBoundsException("A course can only have 2 teachers, so you must use index 0 or 1.");
    return teachers[index];
  }

  /**
   * Returns the title of the course.
   *
   * @return the title of the course
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns a list of students enrolled in the course.
   *
   * @return a list of students enrolled in the course
   */
  public ArrayList<Student> getStudents() {
    return students;
  }

  /**
   * Returns a list of exams scheduled for the course.
   *
   * @return a list of exams scheduled for the course
   */
  public ArrayList<Exam> getExams() {
    return exams;
  }

  /**
   * Returns a string representation of the Course object.
   * The returned string is made up of the course's 12 character code and the title, separated by a hyphen.
   *
   * @return a string representation of the Course object
   */
  @Override
  public String toString() {
    return code + " - " + title;
  }
}

