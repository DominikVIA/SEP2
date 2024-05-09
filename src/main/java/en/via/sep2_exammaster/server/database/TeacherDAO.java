package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Teacher;

import java.util.List;

public interface TeacherDAO {
  List<Teacher> readAllTeachers();
  Teacher readTeacherByInitials(String initials);
}
