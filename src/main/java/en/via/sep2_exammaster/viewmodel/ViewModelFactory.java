package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.viewmodel.student.AnnouncementInfoViewModelStudent;
import en.via.sep2_exammaster.viewmodel.student.InfoExamViewModel;
import en.via.sep2_exammaster.viewmodel.student.MyExamsViewModel;
import en.via.sep2_exammaster.viewmodel.student.StudentAnalyticsViewModel;
import en.via.sep2_exammaster.viewmodel.teacher.*;

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
  private final CreateAnnouncementViewModel createAnnouncementViewModel;
  private final AnnouncementInfoViewModelTeacher announcementInfoViewModelTeacher;
  private final AnnouncementInfoViewModelStudent announcementInfoViewModelStudent;
  private final MyExamsViewModel myExamsViewModel;
  private final InfoExamViewModel infoExamViewModel;
  private final StudentAnalyticsViewModel studentAnalyticsViewModel;

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

  public CreateAnnouncementViewModel getCreateAnnouncementViewModel() {
    return createAnnouncementViewModel;
  }

  public AnnouncementInfoViewModelTeacher getAnnouncementInfoViewModelTeacher() {
    return announcementInfoViewModelTeacher;
  }

  public AnnouncementInfoViewModelStudent getAnnouncementInfoViewModelStudent()
  {
    return announcementInfoViewModelStudent;
  }

  public MyExamsViewModel getMyExamsViewModel() {
    return myExamsViewModel;
  }

  public InfoExamViewModel getInfoExamViewModel() {
    return infoExamViewModel;
  }

  public StudentAnalyticsViewModel getStudentAnalyticsViewModel() {
    return studentAnalyticsViewModel;
  }
}
