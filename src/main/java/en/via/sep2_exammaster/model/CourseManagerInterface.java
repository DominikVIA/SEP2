package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

 interface CourseManagerInterface {
  void setLoggedIn(User user);
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
   void viewCreateExam(Course course);
   void addListener(PropertyChangeListener listener);
   void removeListener(PropertyChangeListener listener);
}
