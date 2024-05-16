package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course implements Serializable
{
  private String code, title, description;
  private int semester;
  private Teacher[] teachers;
  private ArrayList<Student> students;
  private ArrayList<Exam> exams;

  public Course(String code, int semester, String title, String description, Teacher teacherCreating){
    this.code = code;
    this.semester = semester;
    this.title = title;
    this.description = description;
    this.teachers = new Teacher[2];
    this.teachers[0] = teacherCreating;
    this.students = new ArrayList<>();
    this.exams = new ArrayList<>();
  }

  public void addAdditionalTeacher(Teacher teacher){
    teachers[1] = teacher;
  }

  public void addStudents(Student...students){
    this.students.addAll(List.of(students));
  }

  public void addStudent(Student student){
    students.add(student);
  }

  public void addExam(Exam exam){
    exams.add(exam);
  }

  public void addExams(Exam...exams){
    this.exams.addAll(List.of(exams));
  }

  public String getCode(){
    return code;
  }

  public int getSemester(){
    return semester;
  }

  public String getDescription(){
    return description;
  }

  public Teacher getTeacher(int index){
    if(index < 0 || index > 1) throw new IndexOutOfBoundsException("A course can only have 2 teachers so you must use index 0 or 1.");
    return teachers[index];
  }

  public String getTitle(){
    return title;
  }

  public ArrayList<Student> getStudents(){
    return students;
  }

  public ArrayList<Exam> getExams(){
    return exams;
  }

  public String toString(){
    return code + " - " + title;
  }
}
