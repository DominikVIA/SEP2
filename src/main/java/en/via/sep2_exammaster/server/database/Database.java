package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.shared.User;

import java.util.List;

public interface Database {
  List<User> readAllUsers();
  List<Student> readAllStudents();
  List<Teacher> readAllTeachers();

}
