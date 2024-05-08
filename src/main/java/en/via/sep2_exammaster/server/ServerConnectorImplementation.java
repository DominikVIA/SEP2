package en.via.sep2_exammaster.server;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ServerConnectorImplementation extends UnicastRemoteObject
    implements ServerConnector {

  protected ServerConnectorImplementation() throws RemoteException {
    super(0);
  }

  @Override public void createCourse(String code, int semester, String title,
      String description, Teacher primaryTeacher, Teacher additionalTeacher,
      List<Student> students) throws RemoteException {
    try {
      CourseDAOImpl.getInstance().createCourse(code, semester, title, description, primaryTeacher, additionalTeacher, students);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public List<Course> getCourses() throws RemoteException {
    try
    {
      return CourseDAOImpl.getInstance().getCourses();
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }
}
