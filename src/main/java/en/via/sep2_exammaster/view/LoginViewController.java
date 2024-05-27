package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * This ViewController class gives functionalities to the login view by providing code to its methods,
 * making it responsible for handling user interactions when logging in.
 * This class also implements PropertyChangeListener, making it a listener in the Observer pattern.
 */
public class LoginViewController implements PropertyChangeListener {
  @FXML public TextField usernameField;
  @FXML public PasswordField passwordField;

  private ViewHandler viewHandler;
  private LoginViewModel viewModel;
  private Region root;

  /**
   * Attempts to log in using the provided username and password.
   * If either the username or password is blank, proper error message is displayed.
   *
   * @throws IOException if an I/O error occurs during the login operation
   */
  @FXML void onLogin() throws IOException {
    viewModel.login();
  }

  /**
   * Initializes this ViewController with the specified ViewHandler, LoginViewModel and Region objects.
   * While initializing, it also binds all the properties of the editable/interactive FXML objects of the view to
   * their respective properties in the viewModel.
   *
   * @param viewHandler     ViewHandler used for opening other views
   * @param loginViewModel  ViewModel for getting functionalities and connecting to the server
   * @param root            the Region of this ViewController
   */
  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = loginViewModel;
    this.root = root;

    viewModel.bindUsername(usernameField.textProperty());
    viewModel.bindPassword(passwordField.textProperty());

    TextFormatter<String> formatter = new TextFormatter<>(change -> {
      if (change.isAdded() && usernameField.getText().length() >= 6) {
        return null;
      }
      return change;
    });
    usernameField.setTextFormatter(formatter);
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
      case "login success" -> Platform.runLater(() -> {
        if(evt.getNewValue() instanceof Teacher) viewHandler.openView(ViewFactory.MY_COURSES);
        else viewHandler.openView(ViewFactory.MY_EXAMS);
        viewModel.removeListener(this);
      });
      case "login fail credentials" -> Platform.runLater(() -> {
        showError("The username and/or password is incorrect.");
        reset();
      });
      case "login fail online" -> Platform.runLater(() -> {
        showError("This user is already logged in.");
        reset();
      });
      default -> Platform.runLater(() -> showError("The username and/or password are empty."));
    }
  }
}
