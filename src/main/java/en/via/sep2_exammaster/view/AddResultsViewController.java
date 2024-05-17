package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.viewmodel.AddResultsViewModel;
import en.via.sep2_exammaster.viewmodel.CourseInfoViewModel;
import en.via.sep2_exammaster.viewmodel.CreateCourseViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
    viewModel.saveInformation(studentsList.getSelectionModel().getSelectedItem());
    saveButton.setDisable(true);
  }

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
      index = studentsList.getSelectionModel().getSelectedIndex();
//      gradeBox.setDisable(false);
//      feedbackArea.setDisable(false);
      saveButton.setDisable(true);
    }
    else {
//      gradeBox.setDisable(true);
//      feedbackArea.setDisable(true);
    }

  }

  public void init(ViewHandler viewHandler, AddResultsViewModel addResultsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = addResultsViewModel;
    this.root = root;

    gradeBox.getItems().addAll(Grade.values());

    gradeBox.valueProperty().addListener(new ChangeListener<Grade>() {
      @Override
      public void changed(ObservableValue<? extends Grade> observable, Grade oldValue,
          Grade newValue) {
        saveButton.setDisable(false);
      }
    });

    feedbackArea.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        saveButton.setDisable(false);
      }
    });

    viewModel.bindGrade(gradeBox.valueProperty());
    viewModel.bindFeedback(feedbackArea.textProperty());
    viewModel.bindStudents(studentsList.itemsProperty());

//    gradeBox.setDisable(true);
//    feedbackArea.setDisable(true);
    saveButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    studentsList.getSelectionModel().selectFirst();
    System.out.println(studentsList.getSelectionModel().getSelectedIndex());
    saveButton.setDisable(true);
    viewModel.reset();
  }
}

