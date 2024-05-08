package en.via.sep2_exammaster.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerConnector extends Remote {
  void createCourse(String code, int semester,
      String title, String description,
      Teacher primaryTeacher, Teacher additionalTeacher,
      List<Student> students) throws RemoteException;
  List<Course> getCourses() throws RemoteException;

}
