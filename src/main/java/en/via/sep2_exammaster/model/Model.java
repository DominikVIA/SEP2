package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface Model extends Closeable{
  void login(String username, String password) throws IOException;
  void logout() throws IOException;
  void createCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException;
  void editCourse(String code, int semester,
      String title, String description, String additionalTeacherInitials,
      List<Student> students) throws IOException;
  List<Course> getCourses() throws IOException;
  void viewCourse(Course course);
  void viewEditCourse(Course course);
  void deleteCourse(String code) throws IOException;
  void createExam(String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws IOException;
  void editExam(int id, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws IOException;
  void deleteExam(int id) throws IOException;
  void markExamCompleted(Exam exam) throws IOException;
  void viewCreateExam(Course course);
  void viewExamInfo(Exam exam);
  void viewEditExam(Exam exam);
  void viewAddResults(Exam exam);
  Result getStudentExamResult(Exam exam, Student student);
  void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException;
  Student getStudent(int studentID) throws IOException;
  Teacher getTeacher(String initials) throws IOException;
  User getLoggedIn();
  void addListener(PropertyChangeListener listener);
  void removeListener(PropertyChangeListener listener);
}
