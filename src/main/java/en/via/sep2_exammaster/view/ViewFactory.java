package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.viewmodel.CreateCourseViewModel;
import en.via.sep2_exammaster.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory {
  public static final String LOGIN = "login";
  public static final String MY_COURSES = "my courses";
  public static final String COURSE_INFO = "course information";
  public static final String CREATE_COURSE = "create course";

  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;
  private LoginViewController loginViewController;
  private MyCoursesViewController myCoursesViewController;
  private CourseInfoViewController courseInfoViewController;
  private CreateCourseViewController createCourseViewController;
  private EditCourseViewController editCourseViewController;

  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    this.loginViewController = null;
    this.myCoursesViewController = null;
    this.courseInfoViewController = null;
    this.createCourseViewController = null;
    this.editCourseViewController = null;
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
      loader.setLocation(getClass().getResource("MyCoursesView.fxml"));
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
      loader.setLocation(getClass().getResource("CourseInfoView.fxml"));
      try {
        Region root = loader.load();
        courseInfoViewController = loader.getController();
        courseInfoViewController.init(viewHandler, viewModelFactory.getCourseInfoViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    courseInfoViewController.reset();
    return courseInfoViewController.getRoot();
  }

  public Region loadCreateCourseView() {
    if (createCourseViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("CreateCourseView.fxml"));
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

  public Region loadEditCourseView() {
    if (editCourseViewController == null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("EditCourseView.fxml"));
      try {
        Region root = loader.load();
        editCourseViewController = loader.getController();
        editCourseViewController.init(viewHandler, viewModelFactory.getEditCourseViewModel(), root);
      } catch (IOException e) {
        throw new IOError(e);
      }
    }
    editCourseViewController.reset();
    viewModelFactory.getCreateCourseViewModel().addListener(editCourseViewController);
    return editCourseViewController.getRoot();
  }

  public Region load(String id) {
    Region root = switch(id) {
      case LOGIN -> loadLoginView();
      case MY_COURSES -> loadMyCoursesView();
      case COURSE_INFO -> loadCourseInfoView();
      case CREATE_COURSE -> loadCreateCourseView();
      default -> throw new IllegalArgumentException("Unknown view: " + id);
    };
    return root;
  }
}
