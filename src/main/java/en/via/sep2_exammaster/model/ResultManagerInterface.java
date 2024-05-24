package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

/**
 * The interface ResultManagerInterface provides methods for managing exam results by interacting with the server.
 */
public interface ResultManagerInterface {

 /**
  * Sets the logged-in user.
  *
  * @param user the user to set as logged in
  */
 void setLoggedIn(User user);

 /**
  * Retrieves all results for the logged-in student.
  *
  * @return a list of a logged-in student's results
  * @throws IOException if an I/O error occurs
  */
 List<Result> getResults() throws IOException;

 /**
  * Retrieves the result of a specific student for a specific exam.
  *
  * @param exam      the exam
  * @param student   the student
  * @return the result of the student for the exam
  * @throws IOException if an I/O error occurs
  */
 Result getStudentExamResult(Exam exam, Student student) throws IOException;

 /**
  * Retrieves all results for a specific exam.
  *
  * @param exam the exam
  * @return a list of results for the exam
  * @throws IOException if an I/O error occurs
  */
 List<Result> getResultsByExam(Exam exam) throws IOException;

 /**
  * View a specific result. Sends the provided result through the different layers of the app, doesn't actually communicate with the server.
  *
  * @param result the result to view
  */
 void viewResult(Result result);

 /**
  * Edits the result of a specific student for a specific exam.
  *
  * @param student the student
  * @param exam the exam
  * @param grade the new grade
  * @param feedback the new feedback
  * @throws IOException if an I/O error occurs
  */
 void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException;

 /**
  * Adds a listener for property changes.
  *
  * @param listener the listener to add
  */
 void addListener(PropertyChangeListener listener);

 /**
  * Removes a listener for property changes.
  *
  * @param listener the listener to remove
  */
 void removeListener(PropertyChangeListener listener);
}

