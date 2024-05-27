package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.view.teacher.TeacherAnalyticsViewController;
import en.via.sep2_exammaster.viewmodel.student.AnnouncementInfoViewModelStudent;
import en.via.sep2_exammaster.viewmodel.student.InfoExamViewModel;
import en.via.sep2_exammaster.viewmodel.student.MyExamsViewModel;
import en.via.sep2_exammaster.viewmodel.student.StudentAnalyticsViewModel;
import en.via.sep2_exammaster.viewmodel.teacher.*;

/**
 * The ViewModelFactory class is responsible for initializing and storing all view models of the application.
 */
public class ViewModelFactory {
  /**
   * Instance of the LoginViewModel.
   */
  private final LoginViewModel loginViewModel;
  /**
   * Instance of the MyCoursesViewModel.
   */
  private final MyCoursesViewModel myCoursesViewModel;
  /**
   * Instance of the CourseInfoViewModel.
   */
  private final CourseInfoViewModel courseInfoViewModel;
  /**
   * Instance of the CreateCourseViewModel.
   */
  private final CreateCourseViewModel createCourseViewModel;
  /**
   * Instance of the EditCourseViewModel.
   */
  private final EditCourseViewModel editCourseViewModel;
  /**
   * Instance of the CreateExamViewModel.
   */
  private final CreateExamViewModel createExamViewModel;
  /**
   * Instance of the ExamInfoViewModel.
   */
  private final ExamInfoViewModel examInfoViewModel;
  /**
   * Instance of the EditExamViewModel.
   */
  private final EditExamViewModel editExamViewModel;
  /**
   * Instance of the AddResultsViewModel.
   */
  private final AddResultsViewModel addResultsViewModel;
  /**
   * Instance of the CreateAnnouncementViewModel.
   */
  private final CreateAnnouncementViewModel createAnnouncementViewModel;
  /**
   * Instance of the AnnouncementInfoViewModelTeacher.
   */
  private final AnnouncementInfoViewModelTeacher announcementInfoViewModelTeacher;
  /**
   * Instance of the AnnouncementInfoViewModelStudent.
   */
  private final AnnouncementInfoViewModelStudent announcementInfoViewModelStudent;
  /**
   * Instance of the MyExamsViewModel.
   */
  private final MyExamsViewModel myExamsViewModel;
  /**
   * Instance of the InfoExamViewModel.
   */
  private final InfoExamViewModel infoExamViewModel;
  /**
   * Instance of the StudentAnalyticsViewModel.
   */
  private final StudentAnalyticsViewModel studentAnalyticsViewModel;
  /**
   * Instance of the TeacherAnalyticsViewModel.
   */
  private final TeacherAnalyticsViewModel teacherAnalyticsViewModel;

  /**
   * Constructor creating an object of the ViewModelFactory class, using the Model parameter
   * to initialize all the view models using their constructors.
   *
   * @param model instance of the model interface used to initialize the view model classes
   */
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
    this.createAnnouncementViewModel = new CreateAnnouncementViewModel(model);
    this.announcementInfoViewModelTeacher = new AnnouncementInfoViewModelTeacher(model);
    this.announcementInfoViewModelStudent = new AnnouncementInfoViewModelStudent(model);
    this.myExamsViewModel = new MyExamsViewModel(model);
    this.infoExamViewModel = new InfoExamViewModel(model);
    this.studentAnalyticsViewModel = new StudentAnalyticsViewModel(model);
    this.teacherAnalyticsViewModel = new TeacherAnalyticsViewModel(model);
  }

  /**
   * Returns an instance of the LoginViewModel.
   *
   * @return instance of the LoginViewModel
   */
  public LoginViewModel getLoginViewModel() {
    return loginViewModel;
  }

  /**
   * Returns an instance of the MyCoursesViewModel.
   *
   * @return instance of the MyCoursesViewModel
   */
  public MyCoursesViewModel getMyCoursesViewModel(){
    return myCoursesViewModel;
  }

  /**
   * Returns an instance of the CourseInfoViewModel.
   *
   * @return instance of the CourseInfoViewModel
   */
  public CourseInfoViewModel getCourseInfoViewModel() {
    return courseInfoViewModel;
  }

  /**
   * Returns an instance of the CreateCourseViewModel.
   *
   * @return instance of the CreateCourseViewModel
   */
  public CreateCourseViewModel getCreateCourseViewModel() {
    return createCourseViewModel;
  }

  /**
   * Returns an instance of the EditCourseViewModel.
   *
   * @return instance of the EditCourseViewModel
   */
  public EditCourseViewModel getEditCourseViewModel(){
    return editCourseViewModel;
  }

  /**
   * Returns an instance of the CreateExamViewModel.
   *
   * @return instance of the CreateExamViewModel
   */
  public CreateExamViewModel getCreateExamViewModel(){
    return createExamViewModel;
  }

  /**
   * Returns an instance of the ExamInfoViewModel.
   *
   * @return instance of the ExamInfoViewModel
   */
  public ExamInfoViewModel getExamInfoViewModel(){
    return examInfoViewModel;
  }

  /**
   * Returns an instance of the EditExamViewModel.
   *
   * @return instance of the EditExamViewModel
   */
  public EditExamViewModel getEditExamViewModel() {
    return editExamViewModel;
  }

  /**
   * Returns an instance of the AddResultsViewModel.
   *
   * @return instance of the AddResultsViewModel
   */
  public AddResultsViewModel getAddResultsViewModel() {
    return addResultsViewModel;
  }

  /**
   * Returns an instance of the CreateAnnouncementViewModel.
   *
   * @return instance of the CreateAnnouncementViewModel
   */
  public CreateAnnouncementViewModel getCreateAnnouncementViewModel() {
    return createAnnouncementViewModel;
  }

  /**
   * Returns an instance of the AnnouncementInfoViewModelTeacher.
   *
   * @return instance of the AnnouncementInfoViewModelTeacher
   */
  public AnnouncementInfoViewModelTeacher getAnnouncementInfoViewModelTeacher() {
    return announcementInfoViewModelTeacher;
  }

  /**
   * Returns an instance of the AnnouncementInfoViewModelStudent.
   *
   * @return instance of the AnnouncementInfoViewModelStudent
   */
  public AnnouncementInfoViewModelStudent getAnnouncementInfoViewModelStudent()
  {
    return announcementInfoViewModelStudent;
  }

  /**
   * Returns an instance of the MyExamsViewModel.
   *
   * @return instance of the MyExamsViewModel
   */
  public MyExamsViewModel getMyExamsViewModel() {
    return myExamsViewModel;
  }

  /**
   * Returns an instance of the InfoExamViewModel.
   *
   * @return instance of the InfoExamViewModel
   */
  public InfoExamViewModel getInfoExamViewModel() {
    return infoExamViewModel;
  }

  /**
   * Returns an instance of the StudentAnalyticsViewModel.
   *
   * @return instance of the StudentAnalyticsViewModel
   */
  public StudentAnalyticsViewModel getStudentAnalyticsViewModel() {
    return studentAnalyticsViewModel;
  }

  /**
   * Returns an instance of the TeacherAnalyticsViewModel.
   *
   * @return instance of the TeacherAnalyticsViewModel
   */
  public TeacherAnalyticsViewModel getTeacherAnalyticsViewModel() {
    return teacherAnalyticsViewModel;
  }
}
