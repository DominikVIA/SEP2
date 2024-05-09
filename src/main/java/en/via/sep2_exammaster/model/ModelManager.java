package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ModelManager implements Model, RemotePropertyChangeListener, Serializable {

  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public ModelManager(ServerConnector server) throws IOException {
    this.server = server;
    this.support = new PropertyChangeSupport(this);
    this.server.addListener(this);
  }

  @Override
  public void login(String username, String password) throws IOException{
    server.login(username, password);
  }

  @Override public User getLoggedIn() {
    return loggedIn;
  }

  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(RemotePropertyChangeEvent evt) {
    System.out.println("got event");
    switch (evt.getPropertyName()){
      case "login success" -> {
        System.out.println("model is sending event");
        loggedIn = (User) evt.getNewValue();
        System.out.println(loggedIn);
        support.firePropertyChange("login success", null, loggedIn);
        System.out.println("model send event");
      }
      case "login fail credentials" -> support.firePropertyChange("login fail credentials", null, loggedIn);
    }

  }

  @Override public void close() {

  }

}
