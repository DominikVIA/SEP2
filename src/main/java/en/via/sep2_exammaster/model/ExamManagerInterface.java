package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The interface ExamManagerInterface provides methods for managing exams and announcements by interacting with the server.
 */
public interface ExamManagerInterface {

  /**
   * Sets the currently logged-in user.
   *
   * @param user the logged-in user.
   */
  void setLoggedIn(User user);

  /**
   * Creates a new exam with the given details.
   *
   * @param title       the title of the exam
   * @param content     the content of the exam
   * @param room        the room for the exam
   * @param course      the course for which the exam is created
   * @param date        the date of the exam
   * @param time        the time of the exam
   * @param written     indicates if the exam is written or oral
   * @param examiners   the type of examiners grading the exam
   * @param students    the list of students enrolled for the exam
   * @throws IOException if an I/O error occurs
   */
  void createExam(String title, String content, String room, Course course,
      LocalDate date, LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException;

  /**
   * Edits an existing exam with the given details.
   *
   * @param id          the id of the exam to edit
   * @param title       the new title of the exam
   * @param content     the new content of the exam
   * @param room        the new room for the exam
   * @param course      the new course for the exam
   * @param date        the new date of the exam
   * @param time        the new time of the exam
   * @param written     indicates if the exam is written or oral
   * @param examiners   the new type examiners grading the exam
   * @param students    the new list of students enrolled for the exam
   * @throws IOException if an I/O error occurs
   */
  void editExam(int id, String title, String content, String room, Course course,
      LocalDate date, LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException;

  /**
   * Deletes the exam with the given id.
   *
   * @param id          the id of the exam to delete
   * @throws IOException if an I/O error occurs
   */
  void deleteExam(int id) throws IOException;

  /**
   * Marks the given exam as completed.
   *
   * @param exam the exam to mark as completed
   * @throws IOException if an I/O error occurs
   */
  void markExamCompleted(Exam exam) throws IOException;

  /**
   * Creates an announcement for the given exam.
   *
   * @param title       the title of the announcement
   * @param content     the content of the announcement
   * @param exam        the exam for which the announcement is created
   * @throws IOException if an I/O error occurs
   */
  void createAnnouncement(String title, String content, Exam exam) throws IOException;

  /**
   * Displays information about the given announcement and the associated exam.
   * Sends an event with the announcement to be viewed using property change events to the other layers of the app.
   *
   * @param announcement the announcement to view
   * @param examTitle the title of the exam associated with the announcement
   */
  void viewAnnouncementInfo(Announcement announcement, String examTitle);

  /**
   * Displays the edit view for the given exam.
   * Sends an event with the exam to be edited using property change events to the other layers of the app.
   *
   * @param exam the exam to edit
   */
  void viewEditExam(Exam exam);

  /**
   * Displays the create announcement view for the given exam.
   * Sends an event with the exam for which a new announcement will be created
   * using property change events to the other layers of the app.
   *
   * @param exam the exam for which to create the announcement.
   */
  void viewCreateAnnouncement(Exam exam);

  /**
   * Displays information about the given exam.
   * Sends an event with the exam to be viewed using property change events to the other layers of the app.
   *
   * @param exam the exam to view.
   */
  void viewExamInfo(Exam exam);

  /**
   * Displays the add results view for the given exam.
   * Sends an event with the exam for which results are to be added
   * using property change events to the other layers of the app.
   *
   * @param exam the exam for which to add results.
   */
  void viewAddResults(Exam exam);

  /**
   * Displays the analytics view for the given exam.
   * Sends an event with the exam the analytics of which are to be viewed
   * using property change events to the other layers of the app.
   *
   * @param exam the exam for which to view analytics.
   */
  void viewAnalytics(Exam exam);

  /**
   * Adds a property change listener.
   *
   * @param listener the listener to add.
   */
  void addListener(PropertyChangeListener listener);

  /**
   * Removes a property change listener.
   *
   * @param listener the listener to remove.
   */
  void removeListener(PropertyChangeListener listener);
}

