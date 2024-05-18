package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ExamDAO {
  void markExamCompleted(Exam exam);
  Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students)
      throws SQLException;
  Announcement createAnnouncement(String title, String content, Exam exam);
  Exam editExam(int id, String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students) throws SQLException;
  List<Exam> getExamsByCourse(Course course) throws SQLException;
  void deleteExam(int id);
}
