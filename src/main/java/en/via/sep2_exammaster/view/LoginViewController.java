package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;

public class LoginViewController implements PropertyChangeListener {
  @FXML public TextField usernameField;
  @FXML public PasswordField passwordField;

  private ViewHandler viewHandler;
  private LoginViewModel viewModel;
  private Region root;

  @FXML void onLogin() throws IOException {
    viewModel.login();
  }

  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = loginViewModel;
    this.root = root;

    viewModel.bindUsername(usernameField.textProperty());
    viewModel.bindPassword(passwordField.textProperty());
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
      case "login success" -> {
        Platform.runLater(() -> {
          if(evt.getNewValue() instanceof Teacher) viewHandler.openView(ViewFactory.MY_COURSES);
          else viewHandler.openView(ViewFactory.MY_EXAMS);
          viewModel.removeListener(this);
        });

      }
      case "login fail credentials" -> {
        Platform.runLater(() -> {
          showError("The username and/or password is incorrect.");
          reset();
        });
      }
      case "login fail online" -> {
        Platform.runLater(() -> {
          showError("This user is already logged in.");
          reset();
        });
      }
      default -> {
        Platform.runLater(() -> {
          showError("The username and/or password are empty.");
        });
      }
    }
  }
}
