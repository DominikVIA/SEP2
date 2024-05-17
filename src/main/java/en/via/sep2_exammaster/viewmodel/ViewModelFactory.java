package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.view.CreateExamViewController;

import java.rmi.RemoteException;

public class ViewModelFactory {
  private final LoginViewModel loginViewModel;
  private final MyCoursesViewModel myCoursesViewModel;
  private final CourseInfoViewModel courseInfoViewModel;
  private final CreateCourseViewModel createCourseViewModel;
  private final EditCourseViewModel editCourseViewModel;
  private final CreateExamViewModel createExamViewModel;
  private final ExamInfoViewModel examInfoViewModel;
  private final EditExamViewModel editExamViewModel;
  private final AddResultsViewModel addResultsViewModel;

  public ViewModelFactory(Model model) {
    this.loginViewModel = new LoginViewModel(model);
    this.myCoursesViewModel = new MyCoursesViewModel(model);
    this.courseInfoViewModel = new CourseInfoViewModel(model);
    this.createCourseViewModel = new CreateCourseViewModel(model);
    this.editCourseViewModel = new EditCourseViewModel(model);
    this.createExamViewModel = new CreateExamViewModel(model);
    this.examInfoViewModel = new ExamInfoViewModel(model);
    this.editExamViewModel = new EditExamViewModel(model);
    this.addResultsViewModel = new AddResultsViewModel(model);
  }

  public LoginViewModel getLoginViewModel() {
    return loginViewModel;
  }

  public MyCoursesViewModel getMyCoursesViewModel(){
    return myCoursesViewModel;
  }

  public CourseInfoViewModel getCourseInfoViewModel() {
    return courseInfoViewModel;
  }

  public CreateCourseViewModel getCreateCourseViewModel() {
    return createCourseViewModel;
  }

  public EditCourseViewModel getEditCourseViewModel(){
    return editCourseViewModel;
  }

  public CreateExamViewModel getCreateExamViewModel(){
    return createExamViewModel;
  }

  public ExamInfoViewModel getExamInfoViewModel(){
    return examInfoViewModel;
  }

  public EditExamViewModel getEditExamViewModel() {
    return editExamViewModel;
  }

  public AddResultsViewModel getAddResultsViewModel() {
    return addResultsViewModel;
  }
}
