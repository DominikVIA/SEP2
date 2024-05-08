package en.via.sep2_exammaster.shared;

import en.via.sep2_exammaster.server.CourseDAOImpl;

import java.sql.*;

public class Test {

  public static void main(String[] args) throws SQLException {
    System.out.println(CourseDAOImpl.getInstance().getCourses());

//    DriverManager.registerDriver(new org.postgresql.Driver());
//    DatabaseManagerImplementation databaseManagerImplementation = DatabaseManagerImplementation.getInstance();
//
////    Teacher teacher = databaseManagerImplementation.readTeacherByInitials("ALHE");
////
////    Course courseNew = new Course("SDJ1", "Software Development in Java 1", "Learning the basics of Java and creating basic apps", teacher);
////    databaseManagerImplementation.writeCourse(courseNew);
//
////    databaseManagerImplementation.writeStudent(new Student(344353,"Dominik", "Kielbowski"));
////      databaseManagerImplementation.writeTeacher(new Teacher("MONA", "Mona", "Wendel"));
//
//    Course course1 = databaseManagerImplementation.readCourseByCode("SDJ2");
////    Student student1 = readStudentById(123456);
////    Student student2 = readStudentById(234567);
////    Student student3 = readStudentById(345678);
////    course1.addStudent(student1);
////    course1.addStudent(student2);
////    course1.addStudent(student3);
//    //    Exam exam2 = new Exam(LocalDate.of(2024, 5, 23), LocalTime.of(10, 0), student3, student1);
////    course1.createExam(exam2);
////    student1.newResult(new Result(Grade.B, exam1));
////    student2.newResult(new Result(Grade.A, exam1));
////    student3.newResult(new Result(Grade.C, exam1));
//    System.out.println(course1);
////    System.out.println(course1.getExams().get(0).getStudents());
//    System.out.println(databaseManagerImplementation.readAllUsers());
//    System.out.println(databaseManagerImplementation.readStudentsFromCourse("SDJ2"));
////    System.out.println(databaseManagerImplementation.readExamByCourseCode("SDJ2"));
//    System.out.println(databaseManagerImplementation.readStudentsEnrolledInExam(
//        databaseManagerImplementation.readCourseByCode("SDJ2").getExams().get(0)));
//    System.out.println(databaseManagerImplementation.readCourseByCode("SDJ2").getExams().get(1));
//    System.out.println(databaseManagerImplementation.readStudentsEnrolledInExam(
//        databaseManagerImplementation.readCourseByCode("SDJ2").getExams().get(1)));
//    System.out.println(databaseManagerImplementation.readResultsByStudentNo(234567));
  }
}
