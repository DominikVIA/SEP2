package en.via.sep2_exammaster.view.teacher;

import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.teacher.CreateAnnouncementViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * This ViewController class gives functionalities to the "create announcement" view by providing code to its methods,
 * making it responsible for handling user interactions when creating a new announcement.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class CreateAnnouncementViewController implements PropertyChangeListener {
  @FXML public TextField titleField;
  @FXML public TextArea contentArea;
  @FXML public Label examLabel;

  private ViewHandler viewHandler;
  private CreateAnnouncementViewModel viewModel;
  private Region root;

  /**
   * Cancels the creation process and opens the InfoExamView.
   */
  @FXML void onCancel() {
    viewModel.removeListener(this);
    viewHandler.openView(ViewFactory.EXAM_INFO);
  }

  /**
   * Creates a new announcement using the information provided by the user in the view. Displays appropriate errors if necessary.
   *
   * @throws IOException if an I/O error occurs while creating a new announcement
   */
  @FXML void onCreate() throws IOException {
    viewModel.onCreate();
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, CreateAnnouncementViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param createAnnouncementViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, CreateAnnouncementViewModel createAnnouncementViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = createAnnouncementViewModel;
    this.root = root;

    viewModel.bindTitle(titleField.textProperty());
    viewModel.bindContent(contentArea.textProperty());
    viewModel.bindExamLabel(examLabel.textProperty());

    viewModel.addListener(this);
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
    viewModel.reset();
  }

  /**
   * Shows an error with the specified message.
   *
   * @param message the error message to be displayed
   */
  public void showError(String message){
    Alert alert = new Alert(Alert.AlertType.WARNING, message);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  /**
   * Handles property change events fired by its subject (ViewModel).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()){
      case "announcement create success" -> Platform.runLater(() -> {
        viewModel.viewExamInfo();
        viewModel.removeListener(this);
        viewHandler.openView(ViewFactory.EXAM_INFO);
      });
      case "creating error" -> Platform.runLater(() -> showError("The title and content cannot be left empty."));
    }
  }
}

