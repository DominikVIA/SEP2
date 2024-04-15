package en.via.sep2_exammaster.shared;

import java.time.LocalDate;
import java.time.LocalTime;

public class Test {
  public static void main(String[] args) {
    Teacher teacher = new Teacher("OLE", "Ole");
    Class class1 = new Class("SDJ2", "making java applications", teacher);
    Student student1 = new Student(123456, "Maria");
    Student student2 = new Student(234567, "Seba");
    Student student3 = new Student(345678, "Joan");
    class1.addStudent(student1);
    class1.addStudent(student2);
    class1.addStudent(student3);
    Exam exam = new Exam(LocalDate.of(2024, 5, 22), LocalTime.of(12, 40));
    class1.createExam(exam);
    exam.complete();
    student1.newResult(new Result(Grade.B, exam));
    student2.newResult(new Result(Grade.A, exam));
    student3.newResult(new Result(Grade.C, exam));
    System.out.println(class1);
  }
}
