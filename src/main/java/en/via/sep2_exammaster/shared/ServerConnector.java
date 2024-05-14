package en.via.sep2_exammaster.shared;

import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.model.Model;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
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
  Student getStudent(User loggedIn, int studentID) throws RemoteException;
  Teacher getTeacher(User loggedIn, String initials) throws RemoteException;
  void addListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  void removeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;

}
