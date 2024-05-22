package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Result;
import en.via.sep2_exammaster.shared.Student;

import java.util.List;

public interface ResultDAO {
  List<Student> readStudentsEnrolledInExam(int examID);
  List<Result> getResultsByExam(Exam exam);
  List<Result> getResultsByStudentID(int studentId);
  Result getStudentResultByExamId(Exam exam, Student student);
  Result editResult(Student student, Exam exam, Grade grade, String feedback);
}
