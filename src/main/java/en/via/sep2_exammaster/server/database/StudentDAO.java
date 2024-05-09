package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Student;

import java.util.List;

public interface StudentDAO {
  List<Student> readAllStudents();
}
