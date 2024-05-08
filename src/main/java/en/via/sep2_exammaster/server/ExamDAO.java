package en.via.sep2_exammaster.server;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Exam;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ExamDAO {
  Exam createExam(String title, String content, String room, LocalDate date, LocalTime time, Course course);
  List<Exam> getExamsByCourse(Course course) throws SQLException;
}
