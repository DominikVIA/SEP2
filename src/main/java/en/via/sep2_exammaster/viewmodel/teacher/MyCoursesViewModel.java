package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Course;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * The MyCoursesViewModel class represents the view model for displaying
 * a list of courses taught by a logged-in teacher.
 * It interacts with the Model as a source of courses
 * and provides a property for the list of courses.
 */
public class MyCoursesViewModel{
  /**
   * Instance of the Model class to allow communication with the server.
   */
  private final Model model;
  /**
   * Property for binding with the ListView in the view to add courses to it.
   */
  private final ObjectProperty<ObservableList<Course>> courses;

  /**
   * Constructs a MyCoursesViewModel with the given model.
   *
   * @param model the model for getting all of a teacher's courses
   */
  public MyCoursesViewModel(Model model) {
    this.model = model;
    this.courses = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  /**
   * Sends a chosen course from the ListView to the model to be viewed by a different view in the application.
   *
   * @param course course selected in the ListView to display in a different view
   */
  public void viewCourse(Course course){
    try
    {
      model.viewCourseRelated(course, ModelManager.VIEW_COURSE);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Resets the viewModel by setting all the course property's
   * items to an ArrayList of courses received from the model.
   */
  public void reset() throws IOException {
    courses.getValue().setAll(model.getCourses());
  }

  /**
   * Binds the provided ObjectProperty to the course property,
   * enabling one-way accessing and changing of the ListView in the FXML view.
   *
   * @param property the ObjectProperty to bind to the course property
   */
  public void bindCourses(ObjectProperty<ObservableList<Course>> property){
    property.bind(courses);
  }
}
