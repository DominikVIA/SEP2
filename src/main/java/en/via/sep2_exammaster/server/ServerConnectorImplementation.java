package en.via.sep2_exammaster.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import en.via.sep2_exammaster.server.database.CourseDAOImpl;
import en.via.sep2_exammaster.server.database.Database;
import en.via.sep2_exammaster.server.database.DatabaseManager;
import en.via.sep2_exammaster.shared.*;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ServerConnectorImplementation extends UnicastRemoteObject implements ServerConnector {
  private Database database;
  private RemotePropertyChangeSupport<Serializable> support;
  private ArrayList<User> onlineUsers;

  public ServerConnectorImplementation() throws RemoteException, SQLException {
    super(0);
    database = DatabaseManager.getInstance();
    support = new RemotePropertyChangeSupport<>();
    onlineUsers = new ArrayList<>();
  }

  @Override public User login(String username, String password) throws RemoteException {
    List<User> allUsers = database.readAllUsers();
    User user = null;

    if (!Pattern.matches("[a-zA-Z]+", username))
      user = new Student(Integer.parseInt(username), null, password);
    else
      user = new Teacher(username, null, password);

    if (allUsers.contains(user)) {
      if(!onlineUsers.contains(user))
      {
        int index = allUsers.indexOf(user);
        user = allUsers.get(index);
        onlineUsers.add(user);
      }
      else throw new IllegalArgumentException("login fail online");
    }
    else throw new IllegalArgumentException("login fail credentials");

    return user;
  }

  @Override public void logout(User user) throws RemoteException{
    onlineUsers.remove(user);
  }

  @Override public void createCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws RemoteException {
    try {
      Course temp = database.createCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
      support.firePropertyChange("course create success", primaryTeacher, temp);
    }
    catch (IllegalArgumentException e){
      switch (e.getMessage()){
        case "code exists" -> support.firePropertyChange("course create fail", primaryTeacher, null);
        case "teacher initials incorrect" -> support.firePropertyChange("teacher not found", primaryTeacher, null);
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void editCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, String additionalTeacher,
      List<Student> students) throws RemoteException {
    try {
      Course temp = database.editCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
      support.firePropertyChange("course edit success", primaryTeacher, temp);
    }
    catch (IllegalArgumentException e){
      support.firePropertyChange("teacher not found", primaryTeacher, null);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public List<Course> getCourses(Teacher teacher) throws RemoteException {
    try {
      List<Course> courses = CourseDAOImpl.getInstance().getCourses();
      List<Course> answer = new ArrayList<>();
      for(Course temp : courses){
        if(temp.getTeacher(0).equals(teacher) ||
            (temp.getTeacher(1) != null && temp.getTeacher(1).equals(teacher)))
          answer.add(temp);
      }
      return answer;
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteCourse(String code) throws RemoteException{
    database.deleteCourse(code);
  }

  @Override
  public void markExamCompleted(Exam exam) throws RemoteException{
    database.markExamCompleted(exam);
  }

  @Override
  public List<Exam> getExamsByStudentId(int studentId) throws RemoteException {
    return database.getExamsByStudentId(studentId);
  }

  @Override
  public List<Result> getResultsByStudentId(int studentId) throws RemoteException {
    return database.getResultsByStudentId(studentId);
  }

  @Override
  public void createExam(User loggedIn, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws RemoteException {
    try{
      Exam temp = database.createExam(title, content, room, course, date, time, written, examiners, students);
      support.firePropertyChange("exam create success", loggedIn, temp);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void editExam(
      User loggedIn, int id, String title,
      String content, String room, Course course, LocalDate date,
      LocalTime time, boolean written,
      Examiners examiners, List<Student> students
  ) throws RemoteException {
    try{
      Exam temp = database.editExam(id, title, content, room, course, date, time, written, examiners, students);
      support.firePropertyChange("exam edit success", loggedIn, temp);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteExam(int id) throws RemoteException{
    database.deleteExam(id);
  }

  @Override
  public void createAnnouncement(User loggedIn, String title, String content, Exam exam) throws RemoteException{
    Announcement temp = database.createAnnouncement(title, content, exam);
    support.firePropertyChange("announcement create success", loggedIn, temp);
  }

  @Override
  public Result getStudentExamResult(Exam exam, Student student) throws
      RemoteException
  {
    return database.getStudentResultByExamId(exam, student);
  }

  @Override public List<Result> getResultsByExam(Exam exam)
      throws RemoteException
  {
    return database.getResultsByExam(exam);
  }

  @Override
  public void editResult(User loggedIn, Student student, Exam exam, Grade grade, String feedback) throws RemoteException{
    Result temp = database.editResult(student, exam, grade, feedback);
    support.firePropertyChange("result edit success", loggedIn, temp);
  }

  @Override public Student getStudent(User loggedIn, int studentID) throws RemoteException {
    Student temp = database.readStudent(studentID);
    if(temp != null) return temp;
    support.firePropertyChange("student not found", loggedIn, null);
    return null;
  }

  @Override public Teacher getTeacher(User loggedIn, String initials) throws RemoteException {
    Teacher temp = null;
    try{
       temp = database.readTeacher(initials);
    }
    catch (IllegalArgumentException e){
      support.firePropertyChange("teacher not found", loggedIn, null);
    }
    return temp;
  }

  @Override public void addListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
    support.removePropertyChangeListener(listener);
  }
}
