package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.AddResultsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.Optional;

public class AddResultsViewController {
  @FXML public TextArea feedbackArea;
  @FXML public ChoiceBox<Grade> gradeBox;
  @FXML public ListView<Student> studentsList;
  @FXML public Button saveButton;

  private ViewHandler viewHandler;
  private AddResultsViewModel viewModel;
  private Region root;
  private int index;

  @FXML void onBack() {
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  @FXML void onSave() throws IOException {
    if(studentsList.getSelectionModel().getSelectedItem() != null) {
      if (gradeBox.getSelectionModel().getSelectedItem().getGrade() == -2) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "You need to select a grade first.");
        alert.setHeaderText(null);
        alert.showAndWait();
        return;
      }
      if (!gradeBox.isDisabled()) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Saving a grade is irreversible. Are you sure you want to continue?",
            ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
          gradeBox.getSelectionModel().select(Grade.Null);
          saveButton.setDisable(true);
          return;
        }
        gradeBox.setDisable(true);
      }
      viewModel.saveInformation(
          studentsList.getSelectionModel().getSelectedItem());
      saveButton.setDisable(true);
    }
  }

  @FXML void onClickStudents() throws IOException
  {
    if(studentsList.getSelectionModel().getSelectedItem() != null && index != studentsList.getSelectionModel().getSelectedIndex()){
      if(!saveButton.isDisabled()){
        Alert alert = new Alert(Alert.AlertType.WARNING, "Changing to another student will cause your changes to be lost. "
            + "Are you sure you want to continue?",
            ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.CANCEL){
          studentsList.getSelectionModel().select(index);
          return;
        }
      }
      viewModel.studentClicked(studentsList.getSelectionModel().getSelectedItem());
      gradeBox.setDisable(gradeBox.getSelectionModel().getSelectedItem().getGrade() != -2);
      index = studentsList.getSelectionModel().getSelectedIndex();
      saveButton.setDisable(true);
    }

  }

  public void init(ViewHandler viewHandler, AddResultsViewModel addResultsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = addResultsViewModel;
    this.root = root;

    gradeBox.getItems().addAll(Grade.A, Grade.B, Grade.C, Grade.D, Grade.E, Grade.Fx, Grade.F, Grade.Sick);

    gradeBox.valueProperty().addListener(
        (observable, oldValue, newValue) -> saveButton.setDisable(false));

    feedbackArea.textProperty().addListener(
        (observable, oldValue, newValue) -> saveButton.setDisable(false));

    viewModel.bindGrade(gradeBox.valueProperty());
    viewModel.bindFeedback(feedbackArea.textProperty());
    viewModel.bindStudents(studentsList.itemsProperty());

    saveButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset()
  {
    studentsList.getSelectionModel().selectFirst();
    saveButton.setDisable(true);
    try
    {
      viewModel.reset();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    gradeBox.setDisable(gradeBox.getSelectionModel().getSelectedItem().getGrade() != -2);
  }
}

