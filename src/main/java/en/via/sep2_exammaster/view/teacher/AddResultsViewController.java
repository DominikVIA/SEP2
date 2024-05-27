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

/**
 * This ViewController class gives functionalities to the "course info" view by providing code to its methods,
 * making it responsible for handling user interactions when adding a grade or feedback for students' exam results.
 */
public class AddResultsViewController {
  @FXML public TextArea feedbackArea;
  @FXML public ChoiceBox<Grade> gradeBox;
  @FXML public ListView<Student> studentsList;
  @FXML public Button saveButton;

  private ViewHandler viewHandler;
  private AddResultsViewModel viewModel;
  private Region root;
  private int index;

  /**
   * Loads the InfoExamView into the application window, replacing this one.
   */
  @FXML void onBack() {
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  /**
   * Saves the input information about a selected student's exam result.
   * If a user is inputting a grade for a student for the first time, a message is displayed
   * asking the user for confirmation, as this action is irreversible.
   *
   * @throws IOException if an I/O exception occurs while saving the information
   */
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

  /**
   * When a student is clicked in the students ListView, the program loads in information about that student's exam result.
   * If there have been changes introduced to a result and the user clicks on a different student in the list,
   * the new data being loaded in will overwrite the unsaved changes, and so an appropriate
   * message is displayed informing the user and asking for confirmation.
   *
   * @throws IOException if an I/O error occurs while getting the information
   */
  @FXML void onClickStudents() throws IOException {
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

  /**
   * Initializes this ViewController with the specified ViewHandler, AddResultsViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param addResultsViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
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

  /**
   * Returns the Region of this ViewController.
   *
   * @return the Region saved in the root variable
   */
  public Region getRoot() {
    return root;
  }

  /**
   * Resets this ViewController and its ViewModel.
   */
  public void reset() {
    try {
      studentsList.getSelectionModel().selectFirst();
      saveButton.setDisable(true);
      viewModel.reset();
      gradeBox.setDisable(gradeBox.getSelectionModel().getSelectedItem().getGrade() != -2);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

