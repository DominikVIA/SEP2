package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.User;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

/**
 * The interface CourseManagerInterface provides methods for managing courses by interacting with the server.
 */
public interface CourseManagerInterface {

 /**
  * Sets the currently logged-in user.
  *
  * @param user The logged-in user.
  */
 void setLoggedIn(User user);

 /**
  * Creates a new course with the given details.
  *
  * @param code                       the code of the course (cannot exceed 12 characters in length)
  * @param semester                   the semester of the course
  * @param title                      the title of the course
  * @param description                the description of the course
  * @param additionalTeacherInitials  the initials of additional teacher
  * @param students                   the list of students enrolled in the course
  * @throws IOException if an I/O error occurs
  */
 void createCourse(String code, int semester, String title, String description,
     String additionalTeacherInitials, List<Student> students) throws IOException;

 /**
  * Edits an existing course with the given details.
  *
  * @param code                        the code of the course to edit (cannot be changed)
  * @param semester                    the new semester of the course
  * @param title                       the new title of the course
  * @param description                 the new description of the course
  * @param additionalTeacherInitials   the new initials of additional teacher
  * @param students                    the new list of students enrolled in the course
  * @throws IOException if an I/O error occurs
  */
 void editCourse(String code, int semester, String title, String description,
     String additionalTeacherInitials, List<Student> students) throws IOException;

 /**
  * Retrieves the list of all courses.
  *
  * @return the list of all courses
  * @throws IOException if an I/O error occurs
  */
 List<Course> getCourses() throws IOException;

 /**
  * Views the details of the given course. Sends an event with the course to
  * be viewed using property change events to the other layers of the app.
  *
  * @param course the course to view
  */
 void viewCourse(Course course);

 /**
  * Views the edit form for the given course. Sends an event with the course to
  * be edited using property change events to the other layers of the app.
  *
  * @param course the course to edit
  */
 void viewEditCourse(Course course);

 /**
  * Deletes the course with the given code.
  *
  * @param code the code of the course to delete
  * @throws IOException if an I/O error occurs
  */
 void deleteCourse(String code) throws IOException;

 /**
  * Views the form for creating an exam for the given course. Sends an event with the course to
  * have a new exam added using property change events to the other layers of the app.
  *
  * @param course the course for which to create an exam
  */
 void viewCreateExam(Course course);

 /**
  * Adds a property change listener to be notified of changes.
  *
  * @param listener the property change listener to add
  */
 void addListener(PropertyChangeListener listener);

 /**
  * Removes a property change listener.
  *
  * @param listener the property change listener to remove
  */
 void removeListener(PropertyChangeListener listener);
}

