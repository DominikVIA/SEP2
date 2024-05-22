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
  }

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
//    viewModelFactory.getAddResultsViewModel().addListener(addResultsViewController);
    addResultsViewController.reset();
    return addResultsViewController.getRoot();
  }

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

  public Region load(String id) {
    Region root = switch(id) {
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
      default -> throw new IllegalArgumentException("Unknown view: " + id);
    };
    return root;
  }
}
