package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.io.Closeable;
import java.io.IOException;
import java.rmi.RemoteException;

public interface Model extends Closeable{
  void login(String username, String password) throws IOException;
  User getLoggedIn();
  void addListener(PropertyChangeListener listener);
  void removeListener(PropertyChangeListener listener);
}
