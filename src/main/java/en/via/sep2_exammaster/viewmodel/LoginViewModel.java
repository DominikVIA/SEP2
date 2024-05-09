package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

public class LoginViewModel implements PropertyChangeListener {
  private final Model model;
  private final StringProperty username;
  private final StringProperty password;
  private final PropertyChangeSupport support;

  public LoginViewModel(Model model) {
    this.model = model;
    this.username = new SimpleStringProperty("");
    this.password = new SimpleStringProperty("");
    this.support = new PropertyChangeSupport(this);
    this.model.addListener(this);
  }

  public void login() throws IOException {
    model.login(username.get(), password.get());
    System.out.println(model.getLoggedIn());
  }

  public void reset(){
    username.set("");
    password.set("");
  }

  public void bindUsername(StringProperty property){
    property.bindBidirectional(username);
  }

  public void bindPassword(StringProperty property){
    property.bindBidirectional(password);
  }

  public void addListener(PropertyChangeListener listener){
    support.addPropertyChangeListener(listener);
  }

  public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    System.out.println("view model got event");
    support.firePropertyChange(evt);
  }
}
