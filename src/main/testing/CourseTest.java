
import en.via.sep2_exammaster.shared.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class CourseTest {
  private Course course;
  private Teacher teacher1;
  private Teacher teacher2;
  private Student student1;
  private Student student2;
  private Exam exam1;
  private Exam exam2;

  @BeforeEach
  void setUp() {
    teacher1 = new Teacher("ALHE", "ALLAN", "abc");
    teacher2 = new Teacher("OHJ", "OLE", "abc");
    course = new Course("MAT101", 1, "Algebra", "Basic Algebra Course", teacher1);

    student1 = new Student(1, "John Doe", "Maths");
    student2 = new Student(2, "Jane Smith", "Physics");

    exam1 = new Exam(1, "Midterm", "Midterm exam", "101", course, java.time.LocalDate.now(), java.time.LocalTime.now(), true, Examiners.Internal);
    exam2 = new Exam(2, "Final", "Final exam", "102", course, java.time.LocalDate.now(), java.time.LocalTime.now(), false, Examiners.External);
  }

  @Test
  void constructorAndGetterTest() {
    assertEquals("MAT101", course.getCode());
    assertEquals(1, course.getSemester());
    assertEquals("Algebra", course.getTitle());
    assertEquals("Basic Algebra Course", course.getDescription());
    assertSame(teacher1, course.getTeacher(0));
  }

  @Test
  void addAdditionalTeacherTest() {
    course.addAdditionalTeacher(teacher2);
    assertSame(teacher2, course.getTeacher(1));
  }

  @Test
  void addAndRetrieveStudentsTest() {
    course.addStudent(student1);
    course.addStudents(student2);
    ArrayList<Student> students = course.getStudents();
    assertTrue(students.contains(student1));
    assertTrue(students.contains(student2));
    assertEquals(2, students.size());
  }

  @Test
  void addAndRetrieveExamsTest() {
    course.addExam(exam1);
    course.addExams(exam2);
    ArrayList<Exam> exams = course.getExams();
    assertTrue(exams.contains(exam1));
    assertTrue(exams.contains(exam2));
    assertEquals(2, exams.size());
  }

  @Test
  void deleteExamTest() {
    course.addExam(exam1);
    course.addExam(exam2);
    course.deleteExam(exam1);
    ArrayList<Exam> exams = course.getExams();
    assertFalse(exams.contains(exam1));
    assertTrue(exams.contains(exam2));
    assertEquals(1, exams.size());
  }

  @Test
  void indexOutOfBoundsTest() {
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      course.getTeacher(2);
    });
    String expectedMessage = "A course can only have 2 teachers so you must use index 0 or 1.";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testToString() {
    assertEquals("MAT101 - Algebra", course.toString());
  }
}
