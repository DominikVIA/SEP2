package en.via.sep2_exammaster.server.database;

import en.via.sep2_exammaster.shared.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The ExamDAOImpl class implements the ExamDAO interface giving functionalities
 * to all the interface's methods for accessing and managing exams and announcements.
 * It also follows the Singleton creational design pattern.
 * <p>
 * This class contains methods for marking an exam as completed, creating a new exam, editing an existing exam,
 * creating a new announcement regarding an exam, getting all exams belonging to a given course,
 * getting all exams in which a given student is participating in, deleting a given exam,
 * getting a specific announcements by using its ID, getting all announcements related to a given exam,
 * and getting a specific exam by using its ID.
 */
public class ExamDAOImpl implements ExamDAO {
  /**
   * Singleton instance of the ExamDAOImpl class.
   */
  private static ExamDAOImpl instance;

  /**
   * Private constructor to prevent instantiation from outside the class.
   *
   * @throws SQLException if a database access error occurs
   */
  private ExamDAOImpl() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Retrieves the instance of the ExamDAOImpl class.
   *
   * @return the singleton instance of the ExamDAOImpl class
   * @throws SQLException if a database access error occurs
   */
  public static synchronized ExamDAOImpl getInstance() throws SQLException {
    if(instance == null) instance = new ExamDAOImpl();
    return instance;
  }

  /**
   * Establishes a connection to the database.
   *
   * @return a connection to the database
   * @throws SQLException if a database access error occurs
   */
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=exam_master",
        "postgres",
        "ViaViaVia"
    );
  }

  /**
   * Marks the specified exam as completed.
   *
   * @param exam the exam to mark as completed
   */
  @Override
  public void markExamCompleted(Exam exam){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("UPDATE exams SET completed = true WHERE id = ?");
      statement.setInt(1, exam.getId());
      statement.execute();
    }
    catch (SQLException e){
      e.printStackTrace();
    }
  }

  /**
   * Creates a new exam with the specified details.
   *
   * @param title     the title of the exam
   * @param content   the content of the exam
   * @param room      the room of the exam
   * @param course    the course associated with the exam
   * @param date      the date of the exam
   * @param time      the time of the exam
   * @param written   specifies if the exam is written
   * @param examiners the type of examiners grading the exam
   * @param students  the students enrolled in the exam
   * @return the created exam
   * @throws SQLException if an SQL exception occurs
   */
  @Override
  public Exam createExam(String title, String content, String room, Course course, LocalDate date, LocalTime time, boolean written, Examiners examiners, List<Student> students) throws SQLException {
    Connection connection = getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement("INSERT INTO "
              + "exams(title, content, room, examiners, date, time, course_code, written, completed) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, false);", PreparedStatement.RETURN_GENERATED_KEYS);
      statement.setString(1, title);
      statement.setString(2, content);
      statement.setString(3, room);
      statement.setString(4, examiners.name());
      statement.setObject(5, date);
      statement.setObject(6, time);
      statement.setString(7, course.getCode());
      statement.setBoolean(8, written);
      statement.executeUpdate();

      ResultSet resultSet = statement.getGeneratedKeys();
      resultSet.next();
      int id = resultSet.getInt(1);

      statement = connection.prepareStatement("INSERT INTO results(student_id, exam_id) VALUES (?,?)");
      for(Student temp : students){
        statement.setInt(1, temp.getStudentNo());
        statement.setInt(2, id);
        statement.executeUpdate();
      }

      Exam answer = new Exam(id, title, content, room, course, date, time, written, examiners);
      answer.addStudents(students.toArray(new Student[0]));
      connection.commit();
      return answer;
    }
    catch (SQLException e){
      connection.rollback();
      e.printStackTrace();
      return null;
    }
    finally {
      connection.close();
    }
  }

  /**
   * Creates an announcement for the specified exam.
   *
   * @param title   the title of the announcement
   * @param content the content of the announcement
   * @param exam    the exam for which the announcement is created
   * @return the created announcement
   */
  @Override
  public Announcement createAnnouncement(String title, String content, Exam exam){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("INSERT INTO "
          + "announcements(exam_id, title, content, date, time) "
          + "VALUES(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
      statement.setInt(1, exam.getId());
      statement.setString(2, title);
      statement.setString(3, content);
      statement.setObject(4, LocalDate.now());
      statement.setObject(5, LocalTime.now());
      statement.executeUpdate();
      ResultSet result = statement.getGeneratedKeys();
      result.next();
      return getAnnouncementById(result.getInt(1));
    }
    catch (SQLException e){
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves a list of exams associated with the specified course.
   *
   * @param course the course associated with the exams
   * @return a list of exams associated with the specified course
   * @throws SQLException if an SQL exception occurs
   */
  @Override
  public List<Exam> getExamsByCourse(Course course) throws SQLException {
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM exams WHERE course_code = ?;");
      statement.setString(1, course.getCode());
      ResultSet result = statement.executeQuery();
      ArrayList<Exam> answer = new ArrayList<>();
      while(result.next()){
        int examID = result.getInt(1);
        Exam temp = new Exam(
            examID,
            result.getString("title"),
            result.getString("content"),
            result.getString("room"),
            course,
            result.getDate("date").toLocalDate(),
            result.getTime("time").toLocalTime(),
            result.getBoolean("written"),
            Examiners.valueOf(result.getString("examiners"))
            );
        temp.setCompleted(result.getBoolean("completed"));
        temp.addStudents(ResultDAOImpl.getInstance().readStudentsEnrolledInExam(examID).toArray(new Student[0]));
        temp.addAnnouncements(getExamAnnouncements(temp).toArray(new Announcement[0]));
        answer.add(temp);
      }

      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves an exam with a given ID.
   *
   * @param id the ID of the exam to retrieve
   * @return exam with the given ID
   */
  public Announcement getAnnouncementById(int id){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM announcements WHERE id = ?;");
      statement.setInt(1, id);
      ResultSet result = statement.executeQuery();
      Announcement answer = null;
      if(result.next()){
        answer = new Announcement(
            result.getInt(1),
            result.getString(3),
            result.getString(4),
            ((Date) result.getObject(5)).toLocalDate(),
            ((Time) result.getObject(6)).toLocalTime()
        );
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves a list of announcements associated with the given Exam object.
   *
   * @param exam the exam that the returned announcements should be associated with
   * @return a list of announcements associated with the given Exam object
   */
  public List<Announcement> getExamAnnouncements(Exam exam){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM announcements WHERE exam_id = ?;");
      statement.setInt(1, exam.getId());
      ResultSet result = statement.executeQuery();
      ArrayList<Announcement> answer = new ArrayList<>();
      while(result.next()){
        answer.add(new Announcement(
            result.getInt(1),
            result.getString(3),
            result.getString(4),
            ((Date) result.getObject(5)).toLocalDate(),
            ((Time) result.getObject(6)).toLocalTime()
          )
        );
      }
      return answer;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves a list of exams that a given student (who is specified using a numerical ID) is enrolled in.
   *
   * @param studentId the ID of the student
   * @return a list of exams associated with the given student ID
   */
  @Override
  public List<Exam> getExamsByStudentID(int studentId){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * "
          + "FROM exams JOIN results r on exams.id = r.exam_id "
          + "WHERE student_id = ?;");
      statement.setInt(1, studentId);
      ResultSet result = statement.executeQuery();
      ArrayList<Exam> exams = new ArrayList<>();
      while (result.next()){
        Exam temp = new Exam(
            result.getInt(1),
            result.getString(2),
            result.getString(3),
            result.getString(4),
            null,
            result.getDate(6).toLocalDate(),
            result.getTime(7).toLocalTime(),
            result.getBoolean(9),
            Examiners.valueOf(result.getString(5))
        );
        temp.setCompleted(result.getBoolean("completed"));
        exams.add(temp);
      }
      return exams;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves an exam with a given ID.
   *
   * @param id the ID of the exam to retrieve
   * @return exam with the given ID
   */
  public Exam getExamByID(int id){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM exams WHERE id = ?;");
      statement.setInt(1, id);
      ResultSet result = statement.executeQuery();
      Exam exam = null;
      if(result.next()){
        exam = new Exam(
            id,
            result.getString(2),
            result.getString(3),
            result.getString(4),
            null,
            result.getDate(6).toLocalDate(),
            result.getTime(7).toLocalTime(),
            result.getBoolean(9),
            Examiners.valueOf(result.getString(5))
            );
        exam.setCompleted(result.getBoolean("completed"));
      }
      return exam;
    }
    catch (SQLException e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Edits the specified exam with the new details.
   *
   * @param id        the ID of the exam to edit
   * @param title     the new title of the exam
   * @param content   the new content of the exam
   * @param room      the new room of the exam
   * @param course    the new course associated with the exam
   * @param date      the new date of the exam
   * @param time      the new time of the exam
   * @param written   specifies if the exam is written
   * @param examiners the new type of examiners grading the exam
   * @param students  the new students enrolled in the exam
   * @return          the edited exam
   * @throws SQLException if an SQL exception occurs
   */
  @Override
  public Exam editExam(
      int id, String title, String content,
      String room, Course course, LocalDate date, LocalTime time,
      boolean written, Examiners examiners,
      List<Student> students
  ) throws SQLException {
    Connection connection = getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement("UPDATE exams "
          + "SET title = ?, room = ?, content = ?, "
          + "date = ?, time = ?, written = ?, examiners = ? "
          + "WHERE id = ?;");
      statement.setString(1, title);
      statement.setString(2, room);
      statement.setString(3, content);
      statement.setObject(4, date);
      statement.setObject(5, time);
      statement.setBoolean(6, written);
      statement.setString(7, examiners.name());
      statement.setInt(8, id);
      statement.executeUpdate();

      statement = connection.prepareStatement("DELETE FROM results WHERE exam_id = ?;");
      statement.setInt(1, id);
      statement.executeUpdate();

      for(Student student : students) {
        statement = connection.prepareStatement(
            "INSERT INTO results(student_id, exam_id) VALUES (?, ?);");
        statement.setInt(1, student.getStudentNo());
        statement.setInt(2, id);
        statement.execute();
      }

      connection.commit();
      Exam temp = getExamByID(id);
      temp.addStudents(students.toArray(new Student[0]));
      temp.setCourse(course);
      return temp;
    }
    catch (SQLException e){
      connection.rollback();
      e.printStackTrace();
      return null;
    }
    finally {
      connection.close();
    }
  }

  /**
   * Deletes the exam with the specified ID.
   *
   * @param id the ID of the exam to delete
   */
  @Override
  public void deleteExam(int id){
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("DELETE FROM exams WHERE id = ?;");
      statement.setInt(1, id);
      statement.execute();
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
