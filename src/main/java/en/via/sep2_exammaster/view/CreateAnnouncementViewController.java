package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Grade;
import en.via.sep2_exammaster.viewmodel.AddResultsViewModel;
import en.via.sep2_exammaster.viewmodel.CreateAnnouncementViewModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class CreateAnnouncementViewController implements PropertyChangeListener {
  @FXML public TextField titleField;
  @FXML public TextArea contentArea;
  @FXML public Label examLabel;

  private ViewHandler viewHandler;
  private CreateAnnouncementViewModel viewModel;
  private Region root;

  @FXML void onCancel() {
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  @FXML void onSave() throws IOException {
    viewModel.onSave();
  }

  public void init(ViewHandler viewHandler, CreateAnnouncementViewModel createAnnouncementViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = createAnnouncementViewModel;
    this.root = root;

    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindExamLabel(examLabel.textProperty());

    viewModel.addListener(this);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    viewModel.reset();
  }

  public void showError(String message){
    Alert alert = new Alert(Alert.AlertType.WARNING, message);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "announcement create success" -> {
        Platform.runLater(() -> {
          viewModel.viewExamInfo();
          viewModel.removeListener(this);
          viewHandler.openView(ViewFactory.EXAM_INFO);
        });
      }
      case "creating error" -> {
        Platform.runLater(() -> {
          showError("The title and content cannot be left empty.");
        });
      }
    }
  }
}

