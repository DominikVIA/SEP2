package en.via.sep2_exammaster.viewmodel.teacher;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.Course;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class MyCoursesViewModel{
  private final Model model;
  private final ObjectProperty<ObservableList<Course>> courses;

  public MyCoursesViewModel(Model model) {
    this.model = model;
    this.courses = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

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

  public void reset() throws IOException {
    courses.getValue().setAll(model.getCourses());
  }

  public void bindCourses(ObjectProperty<ObservableList<Course>> property){
    property.bind(courses);
  }
}
