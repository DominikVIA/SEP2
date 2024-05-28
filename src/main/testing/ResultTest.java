import en.via.sep2_exammaster.shared.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
  private Exam exam;
  private Result result;
  private Grade grade;

  @BeforeEach
  void setUp() {
    exam = new Exam(1, "SDJ2", "Complete the final assessment.", "Room 302", new Course("MAT101", 2, "Calculus", "A calculus course", new Teacher("ALHE", "Allan", "abc")), LocalDate.now(), LocalTime.now(), true, Examiners.Internal);
    grade = Grade.A;
    result = new Result(grade, "Excellent performance", exam);
  }

  @Test
  void testConstructorAndGetters() {
    assertSame(grade, result.getGrade(), "Grade should match the input value.");
    assertEquals("Excellent performance", result.getFeedback(), "Feedback should match the input value.");
    assertSame(exam, result.getExam(), "Exam should match the input value.");
  }

  @Test
  void testToString() {
    String expectedToString = exam.toString();
    assertEquals(expectedToString, result.toString(), "toString should return the exam's string representation.");
  }
}

