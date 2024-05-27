package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.view.student.AnnouncementInfoViewControllerStudent;
import en.via.sep2_exammaster.view.student.InfoExamViewController;
import en.via.sep2_exammaster.view.student.MyExamsViewController;
import en.via.sep2_exammaster.view.student.StudentAnalyticsViewController;
import en.via.sep2_exammaster.view.teacher.*;
import en.via.sep2_exammaster.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOError;
import java.io.IOException;

/**
 * The ViewFactory class is responsible for initializing every ViewController
 * when it's being loaded to be shown by the ViewHandler.
 */
public class ViewFactory {
  public static final String LOGIN = "login";
  public static final String MY_COURSES = "my courses";
  public static final String MY_EXAMS = "my exams";
  public static final String COURSE_INFO = "course information";
  public static final String COURSE_CREATE = "create course";
  public static final String COURSE_EDIT = "edit course";
  public static final String EXAM_CREATE = "create exam";
  public static final String EXAM_INFO = "exam info";
  public static final String EXAM_EDIT = "edit exam";
  public static final String RESULTS_ADD = "add results";
  public static final String RESULT_INFO = "result info";
  public static final String ANNOUNCEMENT_CREATE = "create announcement";
  public static final String ANNOUNCEMENT_INFO_TEACHER = "announcement info teacher";
  public static final String ANNOUNCEMENT_INFO_STUDENT = "announcement info student";
  public static final String STUDENT_ANALYTICS = "student analytics";
  public static final String TEACHER_ANALYTICS = "teacher analytics";

  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;
  private LoginViewController loginViewController;
  private MyCoursesViewController myCoursesViewController;
  private CourseInfoViewController courseInfoViewController;
  private CreateCourseViewController createCourseViewController;
  private EditCourseViewController editCourseViewController;
  private CreateExamViewController createExamViewController;
  private ExamInfoViewController examInfoViewController;
  private EditExamViewController editExamViewController;
  private AddResultsViewController addResultsViewController;
  private CreateAnnouncementViewController createAnnouncementViewController;
  private AnnouncementInfoViewControllerTeacher announcementInfoViewControllerTeacher;
  private AnnouncementInfoViewControllerStudent announcementInfoViewControllerStudent;
  private MyExamsViewController myExamsViewController;
  private InfoExamViewController infoExamViewController;
  private StudentAnalyticsViewController studentAnalyticsViewController;
  private TeacherAnalyticsViewController teacherAnalyticsViewController;

  /**
   * Constructs a ViewFactory object with a ViewHandler and ViewModelFactory
   * as parameters in order to use them in the construction of each ViewController.
   *
   * @param viewHandler       ViewHandler object to be used in the initialization of ViewControllers
   * @param viewModelFactory  ViewModelFactory to be used as a source of ViewModels for the initialization of ViewControllers
   */
  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    this.loginViewController = null;
    this.myCoursesViewController = null;
    this.courseInfoViewController = null;
    this.createCourseViewController = null;
    this.editCourseViewController = null;
    this.createExamViewController = null;
    this.examInfoViewController = null;
    this.editExamViewController = null;
    this.addResultsViewController = null;
    this.createAnnouncementViewController = null;
    this.myExamsViewController = null;
    this.infoExamViewController = null;
    this.studentAnalyticsViewController = null;
    this.teacherAnalyticsViewController = null;
  }

  /**
   * Returns the Region of the "login view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "login view" FXML file
   */
  public Region loadLoginView() {
    if (loginViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("LoginView.fxml"));
      try {
        Region root = loader.load();
        loginViewController = loader.getController();
        loginViewController.init(viewHandler, viewModelFactory.getLoginViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    loginViewController.reset();
    viewModelFactory.getLoginViewModel().addListener(loginViewController);
    return loginViewController.getRoot();
  }

  /**
   * Returns the Region of the "my courses view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "my courses view" FXML file
   */
  public Region loadMyCoursesView() {
    if (myCoursesViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/MyCoursesView.fxml"));
      try {
        Region root = loader.load();
        myCoursesViewController = loader.getController();
        myCoursesViewController.init(viewHandler, viewModelFactory.getMyCoursesViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    myCoursesViewController.reset();
    return myCoursesViewController.getRoot();
  }

  /**
   * Returns the Region of the "course info view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "course info view" FXML file
   */
  public Region loadCourseInfoView() {
    if (courseInfoViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/InfoCourseView.fxml"));
      try {
        Region root = loader.load();
        courseInfoViewController = loader.getController();
        courseInfoViewController.init(viewHandler, viewModelFactory.getCourseInfoViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    courseInfoViewController.reset();
    viewModelFactory.getCourseInfoViewModel().addListener(courseInfoViewController);
    return courseInfoViewController.getRoot();
  }

  /**
   * Returns the Region of the "create course view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "create course view" FXML file
   */
  public Region loadCreateCourseView() {
    if (createCourseViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/CreateCourseView.fxml"));
      try {
        Region root = loader.load();
        createCourseViewController = loader.getController();
        createCourseViewController.init(viewHandler, viewModelFactory.getCreateCourseViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    createCourseViewController.reset();
    viewModelFactory.getCreateCourseViewModel().addListener(createCourseViewController);
    return createCourseViewController.getRoot();
  }

  /**
   * Returns the Region of the "create exam view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "create exam view" FXML file
   */
  public Region loadCreateExamView() {
    if (createExamViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/CreateExamView.fxml"));
      try {
        Region root = loader.load();
        createExamViewController = loader.getController();
        createExamViewController.init(viewHandler, viewModelFactory.getCreateExamViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    createExamViewController.reset();
    viewModelFactory.getCreateExamViewModel().addListener(createExamViewController);
    return createExamViewController.getRoot();
  }

  /**
   * Returns the Region of the "edit course view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "edit course view" FXML file
   */
  public Region loadEditCourseView() {
    if (editCourseViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/EditCourseView.fxml"));
      try {
        Region root = loader.load();
        editCourseViewController = loader.getController();
        editCourseViewController.init(viewHandler, viewModelFactory.getEditCourseViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    editCourseViewController.reset();
    viewModelFactory.getEditCourseViewModel().addListener(editCourseViewController);
    return editCourseViewController.getRoot();
  }

  /**
   * Returns the Region of the "exam info view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "exam info view" FXML file
   */
  public Region loadExamInfoView() {
    if (examInfoViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/InfoExamView.fxml"));
      try {
        Region root = loader.load();
        examInfoViewController = loader.getController();
        examInfoViewController.init(viewHandler, viewModelFactory.getExamInfoViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    examInfoViewController.reset();
    viewModelFactory.getExamInfoViewModel().addListener(examInfoViewController);
    return examInfoViewController.getRoot();
  }

  /**
   * Returns the Region of the "edit exam view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "edit exam view" FXML file
   */
  public Region loadEditExamView() {
    if (editExamViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/EditExamView.fxml"));
      try {
        Region root = loader.load();
        editExamViewController = loader.getController();
        editExamViewController.init(viewHandler, viewModelFactory.getEditExamViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    editExamViewController.reset();
    viewModelFactory.getEditExamViewModel().addListener(editExamViewController);
    return editExamViewController.getRoot();
  }

  /**
   * Returns the Region of the "add results view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "add results view" FXML file
   */
  public Region loadAddResultsView() {
    if (addResultsViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/AddResultsView.fxml"));
      try {
        Region root = loader.load();
        addResultsViewController = loader.getController();
        addResultsViewController.init(viewHandler, viewModelFactory.getAddResultsViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    addResultsViewController.reset();
    return addResultsViewController.getRoot();
  }

  /**
   * Returns the Region of the "create announcement view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "create announcement view" FXML file
   */
  public Region loadCreateAnnouncementView() {
    if (createAnnouncementViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/CreateAnnouncementView.fxml"));
      try {
        Region root = loader.load();
        createAnnouncementViewController = loader.getController();
        createAnnouncementViewController.init(viewHandler, viewModelFactory.getCreateAnnouncementViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    createAnnouncementViewController.reset();
    viewModelFactory.getCreateAnnouncementViewModel().addListener(createAnnouncementViewController);
    return createAnnouncementViewController.getRoot();
  }

  /**
   * Returns the Region of the "info announcement view" FXML file to be displayed by the ViewHandler for the teacher.
   *
   * @return Region of the "info announcement view" FXML file
   */
  public Region loadAnnouncementInfoViewTeacher() {
    if (announcementInfoViewControllerTeacher == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/InfoAnnouncementView.fxml"));
      try {
        Region root = loader.load();
        announcementInfoViewControllerTeacher = loader.getController();
        announcementInfoViewControllerTeacher.init(viewHandler, viewModelFactory.getAnnouncementInfoViewModelTeacher(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    announcementInfoViewControllerTeacher.reset();
    return announcementInfoViewControllerTeacher.getRoot();
  }

  /**
   * Returns the Region of the "info announcement view" FXML file to be displayed by the ViewHandler for the student.
   *
   * @return Region of the "info announcement view" FXML file
   */
  public Region loadAnnouncementInfoViewStudent() {
    if (announcementInfoViewControllerStudent == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("student/InfoAnnouncementView.fxml"));
      try {
        Region root = loader.load();
        announcementInfoViewControllerStudent = loader.getController();
        announcementInfoViewControllerStudent.init(viewHandler, viewModelFactory.getAnnouncementInfoViewModelStudent(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    announcementInfoViewControllerStudent.reset();
    return announcementInfoViewControllerStudent.getRoot();
  }

  /**
   * Returns the Region of the "my exams view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "my exams view" FXML file
   */
  public Region loadMyExamsView() {
    if (myExamsViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("student/MyExamsView.fxml"));
      try {
        Region root = loader.load();
        myExamsViewController = loader.getController();
        myExamsViewController.init(viewHandler, viewModelFactory.getMyExamsViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    myExamsViewController.reset();
    return myExamsViewController.getRoot();
  }

  /**
   * Returns the Region of the "info exam view" FXML file to be displayed by the ViewHandler for the student.
   *
   * @return Region of the "info exam view" FXML file
   */
  public Region loadResultInfoView() {
    if (infoExamViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("student/InfoExamView.fxml"));
      try {
        Region root = loader.load();
        infoExamViewController = loader.getController();
        infoExamViewController.init(viewHandler, viewModelFactory.getInfoExamViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    infoExamViewController.reset();
    return infoExamViewController.getRoot();
  }

  /**
   * Returns the Region of the "student analytics view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "student analytics view" FXML file
   */
  public Region loadStudentAnalyticsView() {
    if (studentAnalyticsViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("student/StudentAnalyticsView.fxml"));
      try {
        Region root = loader.load();
        studentAnalyticsViewController = loader.getController();
        studentAnalyticsViewController.init(viewHandler, viewModelFactory.getStudentAnalyticsViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    studentAnalyticsViewController.reset();
    return studentAnalyticsViewController.getRoot();
  }

  /**
   * Returns the Region of the "teacher analytics view" FXML file to be displayed by the ViewHandler.
   *
   * @return Region of the "teacher analytics view" FXML file
   */
  public Region loadTeacherAnalyticsView() {
    if (teacherAnalyticsViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("teacher/TeacherAnalyticsView.fxml"));
      try {
        Region root = loader.load();
        teacherAnalyticsViewController = loader.getController();
        teacherAnalyticsViewController.init(viewHandler, viewModelFactory.getTeacherAnalyticsViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    teacherAnalyticsViewController.reset();
    return teacherAnalyticsViewController.getRoot();
  }

  /**
   * Returns the Region of the loaded in FXML file to be used by the ViewHandler
   *
   * @param id String id of the view to be loaded in
   * @return the Region of the chosen view
   */
  public Region load(String id) {
    return switch(id) {
      case LOGIN -> loadLoginView();
      case MY_COURSES -> loadMyCoursesView();
      case COURSE_INFO -> loadCourseInfoView();
      case COURSE_CREATE -> loadCreateCourseView();
      case COURSE_EDIT -> loadEditCourseView();
      case EXAM_CREATE -> loadCreateExamView();
      case EXAM_INFO -> loadExamInfoView();
      case EXAM_EDIT -> loadEditExamView();
      case RESULTS_ADD -> loadAddResultsView();
      case ANNOUNCEMENT_CREATE -> loadCreateAnnouncementView();
      case ANNOUNCEMENT_INFO_TEACHER -> loadAnnouncementInfoViewTeacher();
      case ANNOUNCEMENT_INFO_STUDENT -> loadAnnouncementInfoViewStudent();
      case MY_EXAMS -> loadMyExamsView();
      case RESULT_INFO -> loadResultInfoView();
      case STUDENT_ANALYTICS -> loadStudentAnalyticsView();
      case TEACHER_ANALYTICS -> loadTeacherAnalyticsView();
      default -> throw new IllegalArgumentException("Unknown view: " + id);
    };
  }
}
