package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

 interface ResultManagerInterface {
  void setLoggedIn(User user);
   List<Result> getResults() throws IOException;
  Result getStudentExamResult(Exam exam, Student student)
      throws IOException;
   void viewResult(Result result) ;
   void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException;
    void addListener(PropertyChangeListener listener);
    void removeListener(PropertyChangeListener listener);
}
