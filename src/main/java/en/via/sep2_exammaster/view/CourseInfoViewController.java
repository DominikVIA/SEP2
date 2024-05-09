package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.LoginViewModel;
import en.via.sep2_exammaster.viewmodel.MyCoursesViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.io.IOException;

public class CourseInfoViewController
{
  @FXML private TextField codeField;

  @FXML private TextArea descriptionArea;

  @FXML private ListView<Exam> examsList;

  @FXML private TextField semesterField;

  @FXML private ListView<Student> studentsList;

  @FXML private TextField titleField;

  private ViewHandler viewHandler;
  private CourseInfoViewModel viewModel;
  private Region root;

  @FXML void onCreateExam(ActionEvent event) {

  }

  @FXML void onDeleteCourse(ActionEvent event) {

  }

  @FXML void onEditCourse(ActionEvent event) {

  }

  @FXML void onViewExam(ActionEvent event) {

  }

  public void init(ViewHandler viewHandler, CourseInfoViewModel courseInfoViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = courseInfoViewModel;
    this.root = root;

    viewModel.bindCode(codeField.textProperty());
    viewModel.bindSemester(semesterField.textProperty());
    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindDescription(descriptionArea.textProperty());
    viewModel.bindStudents(studentsList.itemsProperty());
    viewModel.bindExams(examsList.itemsProperty());
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewModel.reset();
  }
}
