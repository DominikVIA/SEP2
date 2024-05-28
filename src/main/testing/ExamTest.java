
import static org.junit.jupiter.api.Assertions.*;

import en.via.sep2_exammaster.shared.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;


public class ExamTest {
  private Exam exam;
  private Course course;
  private Student student1, student2;
  private Announcement announcement1, announcement2;

  @BeforeEach
  void setUp() {
    course = new Course("CSC101", 1, "Intro to Computer Science", "Course Description", new Teacher("ALHE", "ALLAN", "abc"));
    exam = new Exam(1, "Midterm", "Content of the exam", "Room 101", course, LocalDate.of(2023, 10, 15), LocalTime.of(10, 0), true, Examiners.Internal);

    student1 = new Student(1, "John Doe", "johndoe@example.com");
    student2 = new Student(2, "Jane Smith", "janesmith@example.com");
    announcement1 = new Announcement(1, "Announcement Title 1", "Details of the announcement 1", LocalDate.of(2023, 10, 16), LocalTime.of(15, 30));
    announcement2 = new Announcement(2, "Announcement Title 2", "Details of the announcement 2", LocalDate.of(2023, 10, 17), LocalTime.of(11, 0));
  }

  @Test
  void testConstructorAndGetters() {
    assertEquals(1, exam.getId());
    assertEquals("Midterm", exam.getTitle());
    assertEquals("Content of the exam", exam.getContent());
    assertEquals("Room 101", exam.getRoom());
    assertEquals(LocalDate.of(2023, 10, 15), exam.getDate());
    assertEquals(LocalTime.of(10, 0), exam.getTime());
    assertTrue(exam.isWritten());
    assertEquals(Examiners.Internal, exam.getExaminers());
    assertNotNull(exam.getStudents());
    assertTrue(exam.getStudents().isEmpty());
    assertNotNull(exam.getAnnouncements());
    assertTrue(exam.getAnnouncements().isEmpty());
    assertFalse(exam.isCompleted());
  }

  @Test
  void addAndRetrieveStudents() {
    exam.addStudent(student1);
    exam.addStudents(student2);
    assertEquals(2, exam.getStudents().size());
    assertTrue(exam.getStudents().contains(student1));
    assertTrue(exam.getStudents().contains(student2));
  }

  @Test
  void addAndRetrieveAnnouncements() {
    exam.addAnnouncements(announcement1, announcement2);
    assertEquals(2, exam.getAnnouncements().size());
    assertTrue(exam.getAnnouncements().contains(announcement1));
    assertTrue(exam.getAnnouncements().contains(announcement2));
  }

  @Test
  void testCompletionStatusChanges() {
    assertFalse(exam.isCompleted());
    exam.setCompleted(true);
    assertTrue(exam.isCompleted());
    exam.complete(); // should maintain true
    assertTrue(exam.isCompleted());
  }

  @Test
  void testEqualityBasedOnId() {
    Exam sameExam = new Exam(1, "DBS", "Different Content", "C 2.02", course, LocalDate.of(2024, 12, 20), LocalTime.of(15, 30), false, Examiners.External);
    Exam differentExam = new Exam(2, "SDJ1", "Content of the exam", "C 3.21", course, LocalDate.of(2024, 10, 15), LocalTime.of(10, 0), true, Examiners.Internal);

    assertEquals(exam, sameExam);
    assertNotEquals(exam, differentExam);
  }

  @Test
  void testToString() {
    String expected = "SDJ1 ½ | 2024-10-15, 10:00";
    assertEquals(expected, exam.toString());
  }
}
