package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginManager extends UnicastRemoteObject implements LoginManagerInterface, PropertyChangeListener {
  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public LoginManager(ServerConnector server) throws IOException {
    this.server = server;
    this.support = new PropertyChangeSupport(this);
  }

  @Override
  public void login(String username, String password) throws IOException
  {
    try{
      User temp = server.login(username, password);
      if(temp != null){
        loggedIn = temp;
        support.firePropertyChange("login success", null, loggedIn);
      }
    }
    catch (IllegalArgumentException e){
      support.firePropertyChange(e.getMessage(), null, false);
    }
  }

  @Override public void logout() throws IOException {
//    server.removeListener(this);
    server.logout(loggedIn);
  }

  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {

  }
}
