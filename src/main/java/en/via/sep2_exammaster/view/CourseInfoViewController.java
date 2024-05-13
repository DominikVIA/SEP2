package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.LoginViewModel;
import en.via.sep2_exammaster.viewmodel.MyCoursesViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.Optional;

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

  @FXML void onDeleteCourse() throws IOException {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this course?\n"+
        "It will cause all associated information to be deleted, such as exams, the corresponding results, etc.",
        ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK){
      viewModel.onDelete(codeField.getText());
      viewHandler.openView(ViewFactory.MY_COURSES);
    }
  }

  @FXML void onEditCourse() throws IOException {
    viewHandler.openView(ViewFactory.EDIT_COURSE);
    viewModel.onEdit();
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
