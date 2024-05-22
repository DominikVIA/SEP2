package en.via.sep2_exammaster.model;

import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

 interface ExamManagerInterface {
  
   void setLoggedIn(User user);
   void createExam(String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException;
   void editExam(int id, String title, String content,
      String room, Course course, LocalDate date,
      LocalTime time, boolean written, Examiners examiners,
      List<Student> students) throws IOException ;
   void deleteExam(int id) throws IOException;
   void markExamCompleted(Exam exam) throws IOException;
   void createAnnouncement(String title, String content, Exam exam) throws IOException;
   void viewAnnouncementInfo(Announcement announcement, String examTitle);
   void viewEditExam(Exam exam);
   void viewCreateAnnouncement(Exam exam);
   void viewExamInfo(Exam exam);
   void viewAddResults(Exam exam);
   void addListener(PropertyChangeListener listener);
   void removeListener(PropertyChangeListener listener);
}
