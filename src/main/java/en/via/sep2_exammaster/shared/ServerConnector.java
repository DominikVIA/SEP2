package en.via.sep2_exammaster.shared;

import dk.via.remote.observer.RemotePropertyChangeListener;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ServerConnector extends Remote {

  User login(String username, String password) throws RemoteException;
  void logout(User user) throws RemoteException;
  void createCourse(String code, int semester,
      String title, String description,
      Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws RemoteException;
  void editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws RemoteException;
  void deleteCourse(String code) throws RemoteException;
  List<Course> getCourses(Teacher teacher) throws RemoteException;
  void markExamCompleted(Exam exam) throws RemoteException;
  List<Exam> getExamsByStudentId(int studentId) throws RemoteException;
  List<Result> getResultsByStudentId(int studentId) throws RemoteException;
  void createExam(User loggedIn, String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws RemoteException;
  void editExam(
      User loggedIn, int id, String title,
      String content, String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students
  ) throws RemoteException;
  void deleteExam(int id) throws RemoteException;
  void createAnnouncement(User loggedIn, String title, String content, Exam exam) throws RemoteException;
  Result getStudentExamResult(Exam exam, Student student) throws
      RemoteException;
  List<Result> getResultsByExam(Exam exam) throws RemoteException;
  void editResult(User loggedIn, Student student, Exam exam, Grade grade, String feedback) throws RemoteException;
  Student getStudent(User loggedIn, int studentID) throws RemoteException;
  Teacher getTeacher(User loggedIn, String initials) throws RemoteException;
  void addListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  void removeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;

}
