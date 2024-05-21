package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.io.IOException;

 interface LoginManagerInterface {
   void login(String username, String password) throws IOException;
    void logout() throws IOException;
    void addListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}
