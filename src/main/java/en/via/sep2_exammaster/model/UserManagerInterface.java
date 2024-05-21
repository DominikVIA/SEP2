package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.io.IOException;

 interface UserManagerInterface {
   void setLoggedIn(User user);
   Student getStudent(int studentID) throws IOException;
    void addListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}
