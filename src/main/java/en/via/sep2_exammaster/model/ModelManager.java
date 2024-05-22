package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ModelManager implements Model, PropertyChangeListener  {

  //    course manager
  public static final String VIEW_COURSE = "view course";
  public static final String EDIT_COURSE = "edit course";
  public static final String DELETE_COURSE = "delete course";
  public static final String CREATE_EXAM = "create exam";

  //    exam manager
  public static final String EDIT_EXAM = "edit exam";
  public static final String VIEW_EXAM = "view exam";
  public static final String DELETE_EXAM = "delete exam";
  public static final String CREATE_ANNOUNCEMENT = "create announcement";
  public static final String ADD_RESULTS = "add results";

  private final PropertyChangeSupport support;
  private final CourseManagerInterface courseManager;
  private final ExamManagerInterface examManager;
  private final LoginManagerInterface loginManager;
  private final ResultManagerInterface resultManager;
  private final UserManagerInterface userManager;

  public ModelManager(ServerConnector server) throws IOException {
    this.support = new PropertyChangeSupport(this);
    courseManager = new CourseManager(server);
    courseManager.addListener(this);
    examManager = new ExamManager(server);
    examManager.addListener(this);
    loginManager = new LoginManager(server);
    loginManager.addListener(this);
    resultManager = new ResultManager(server);
    resultManager.addListener(this);
    userManager = new UserManager(server);
    userManager.addListener(this);
  }

  @Override public void login(String username, String password)
      throws IOException
  {
    loginManager.login(username, password);
  }

  @Override public void logout() throws IOException
  {
    loginManager.logout();
  }

  @Override public void createCourse(String code, int semester, String title,
      String description, String additionalTeacherInitials,
      List<Student> students) throws IOException
  {
    courseManager.createCourse(code, semester, title, description, additionalTeacherInitials, students);
  }

  @Override public void editCourse(String code, int semester, String title,
      String description, String additionalTeacherInitials,
      List<Student> students) throws IOException
  {
    courseManager.editCourse(code, semester, title, description, additionalTeacherInitials, students);
  }

  @Override public List<Course> getCourses() throws IOException
  {
    return courseManager.getCourses();
  }

  @Override
  public void viewCourseRelated(Course course, String window) throws IOException {
    switch (window){
      case VIEW_COURSE -> courseManager.viewCourse(course);
      case EDIT_COURSE -> courseManager.viewEditCourse(course);
      case DELETE_COURSE -> courseManager.deleteCourse(course.getCode());
      case CREATE_EXAM -> courseManager.viewCreateExam(course);
    }
  }

  @Override public void createExam(String title, String content, String room,
      Course course, LocalDate date, LocalTime time, boolean written,
      Examiners examiners, List<Student> students) throws IOException
  {
    examManager.createExam(title, content, room, course, date, time, written, examiners, students);
  }

  @Override public void editExam(int id, String title, String content,
      String room, Course course, LocalDate date, LocalTime time,
      boolean written, Examiners examiners, List<Student> students)
      throws IOException
  {
    examManager.editExam(id, title, content, room, course, date, time, written, examiners, students);
  }

  @Override public void deleteExam(int id) throws IOException
  {
    examManager.deleteExam(id);
  }

  @Override public void markExamCompleted(Exam exam) throws IOException
  {
    examManager.markExamCompleted(exam);
  }

  @Override public void createAnnouncement(String title, String content,
      Exam exam) throws IOException
  {
    examManager.createAnnouncement(title, content, exam);
  }

  @Override public void viewAnnouncementInfo(Announcement announcement,
      String examTitle)
  {
    examManager.viewAnnouncementInfo(announcement, examTitle);
  }

  @Override
  public void viewExamRelated(Exam exam, String window) throws IOException {
    switch (window){
      case VIEW_EXAM -> examManager.viewExamInfo(exam);
      case EDIT_EXAM -> examManager.viewEditExam(exam);
      case DELETE_EXAM -> examManager.deleteExam(exam.getId());
      case CREATE_ANNOUNCEMENT -> examManager.viewCreateAnnouncement(exam);
      case ADD_RESULTS -> examManager.viewAddResults(exam);
    }
  }

  @Override public List<Result> getResults()
  {
    try
    {
      return resultManager.getResults();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Result getStudentExamResult(Exam exam, Student student)
      throws IOException
  {
    return resultManager.getStudentExamResult(exam, student);
  }

  @Override public void viewResult(Result result)
  {
    resultManager.viewResult(result);
  }

  @Override public void editResult(Student student, Exam exam, Grade grade,
      String feedback) throws IOException
  {
    resultManager.editResult(student, exam, grade, feedback);
  }

  @Override public Student getStudent(int studentID) throws IOException
  {
    return userManager.getStudent(studentID);
  }

  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().contains("success")) support.firePropertyChange("refresh", null, false);
    if(evt.getPropertyName().equals("login success")){
      courseManager.setLoggedIn((User) evt.getNewValue());
      examManager.setLoggedIn((User) evt.getNewValue());
      resultManager.setLoggedIn((User) evt.getNewValue());
    }
    support.firePropertyChange(evt);
  }

  @Override public void close() throws IOException {
    loginManager.logout();
    UnicastRemoteObject.unexportObject((CourseManager) courseManager, true);
    UnicastRemoteObject.unexportObject((ExamManager) examManager, true);
    UnicastRemoteObject.unexportObject((LoginManager) loginManager, true);
    UnicastRemoteObject.unexportObject((ResultManager) resultManager, true);
    UnicastRemoteObject.unexportObject((UserManager) userManager, true);
  }

}
