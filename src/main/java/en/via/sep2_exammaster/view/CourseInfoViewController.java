package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.LoginViewModel;
import en.via.sep2_exammaster.viewmodel.MyCoursesViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.io.IOException;

public class CourseInfoViewController
{
  @FXML public TextField codeField;

  @FXML public TextArea descriptionArea;

  @FXML public ListView<Exam> examsList;

  @FXML public TextField semesterField;

  @FXML public ListView<Student> studentsList;

  @FXML public TextField titleField;
  @FXML public Button viewButton;

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

  @FXML void onClick(){
    viewButton.setDisable(true);

    if(examsList.getSelectionModel().getSelectedItem() != null)
      viewButton.setDisable(false);
  }

  @FXML void onBack(){
    viewHandler.openView(ViewFactory.MY_COURSES);
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

    viewButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewButton.setDisable(true);
    viewModel.reset();
  }
}
