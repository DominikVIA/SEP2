package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * The LoginViewModel class represents the view model for handling
 * user login functionality. It interacts with the Model to perform
 * login operations and provides properties for username and password inputs.
 * This class also implements PropertyChangeListener and has a PropertyChangeSupport instance,
 * making it both a listener and a subject in the Observer design pattern.
 */
public class LoginViewModel implements PropertyChangeListener {
  /**
   * Instance of the Model class to allow communication with the server.
   */
  private final Model model;
  /**
   * StringProperty for getting and setting the username input.
   */
  private final StringProperty username;
  /**
   * StringProperty for getting and setting the password input.
   */
  private final StringProperty password;
  /**
   * PropertyChangeSupport for firing events to communicate with this object's listeners.
   */
  private final PropertyChangeSupport support;


  /**
   * Constructs a LoginViewModel with the given model.
   *
   * @param model the model for handling login operations
   */
  public LoginViewModel(Model model) {
    this.model = model;
    this.username = new SimpleStringProperty("");
    this.password = new SimpleStringProperty("");
    this.support = new PropertyChangeSupport(this);
    this.model.addListener(this);
  }

  /**
   * Attempts to log in using the provided username and password.
   * If either the username or password is blank, it notifies listeners
   * of a failed login due to empty fields.
   *
   * @throws IOException if an I/O error occurs during the login operation
   */
  public void login() throws IOException {
    if(!username.get().isBlank() && !password.get().isBlank())
      model.login(username.get(), password.get());
    else
      support.firePropertyChange("login fail empty", null, false);
  }

  /**
   * Resets the password property to an empty string.
   */
  public void reset(){
    password.set("");
  }

  /**
   * Bidirectionally binds the provided StringProperty to the username property,
   * enabling two-way accessing and changing of the username field in the FXML view.
   *
   * @param property the StringProperty to bind to the username
   */
  public void bindUsername(StringProperty property){
    property.bindBidirectional(username);
  }

  /**
   * Bidirectionally binds the provided StringProperty to the password property,
   * enabling two-way accessing and changing of the password field in the FXML view.
   *
   * @param property the StringProperty to bind to the password
   */
  public void bindPassword(StringProperty property){
    property.bindBidirectional(password);
  }

  /**
   * Adds a PropertyChangeListener to listen for property change events.
   *
   * @param listener the PropertyChangeListener to add
   */
  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a PropertyChangeListener from listening for property change events.
   *
   * @param listener the PropertyChangeListener to remove
   */
  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  /**
   * Handles property change events, specifically those related to logging in.
   * Forwards login-related events to registered listeners (that being the LoginViewController).
   *
   * @param evt the PropertyChangeEvent representing the event
   */
  @Override public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().contains("login"))
      support.firePropertyChange(evt);
  }
}
