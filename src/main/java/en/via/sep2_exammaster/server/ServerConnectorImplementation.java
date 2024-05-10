package en.via.sep2_exammaster.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import en.via.sep2_exammaster.server.database.CourseDAOImpl;
import en.via.sep2_exammaster.server.database.Database;
import en.via.sep2_exammaster.server.database.DatabaseManager;
import en.via.sep2_exammaster.shared.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
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

  @Override public void login(String username, String password) throws RemoteException
  {
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
        support.firePropertyChange("login success", null, user);
        System.out.println("logged in: " + user);
      }
      else support.firePropertyChange("login fail online", null, user);
    }
    else
    {
      support.firePropertyChange("login fail credentials", null, user);
      System.out.println("failed login: " + user);
    }
  }

  @Override public void logout(User user) throws RemoteException{
    onlineUsers.remove(user);
  }

  @Override public void createCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, Teacher additionalTeacher,
      List<Student> students) throws RemoteException {
    try {
      Course temp = database.createCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
      support.firePropertyChange("course create success", null, temp);
    }
    catch (IllegalArgumentException e){
      support.firePropertyChange("course create fail", null, false);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public Student getStudent(int studentID) throws RemoteException {
    Student temp = database.readStudent(studentID);
    if(temp != null) return temp;
    else support.firePropertyChange("student not found", null, false);
    return null;
  }

  @Override public Teacher getTeacher(String initials) throws RemoteException {
    Teacher temp =  database.readTeacher(initials);
    if(temp != null) return temp;
    else support.firePropertyChange("teacher not found", null, false);
    return null;
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

  @Override public void addListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
    for(RemotePropertyChangeListener temp : support.getPropertyChangeListeners()){
      if(temp.equals(listener)) return;
    }
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
    support.removePropertyChangeListener(listener);
  }
}
