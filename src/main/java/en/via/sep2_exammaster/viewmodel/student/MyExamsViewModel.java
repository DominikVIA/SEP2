package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The MyExamsViewModel class represents the view model for displaying all of a logged-in student's exams.
 * It interacts with the Model as a source of exams
 * and provides properties for the list of upcoming exams and the list of completed exams.
 */
public class MyExamsViewModel{
  private final Model model;
  private final ObjectProperty<ObservableList<Result>> upcoming;
  private final ObjectProperty<ObservableList<Result>> completed;

  /**
   * Constructs a StudentAnalyticsViewModel with the given model.
   *
   * @param model the model for communication with the server
   */
  public MyExamsViewModel(Model model){
    this.model = model;
    upcoming = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    completed = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  /**
   * Opens a view for viewing a selected result's information.
   */
  public void viewResult(Result result){
    model.viewResult(result);
  }

  /**
   * Resets the ViewModel and its ViewController by emptying both lists and then adding the exam results to each
   * depending on the exam's completion status.
   *
   * @throws IOException
   */
  public void reset() throws IOException {
    upcoming.getValue().setAll();
    completed.getValue().setAll();
    ArrayList<Result> results = (ArrayList<Result>) model.getResults();
    for(Result temp : results){
      if(temp.getExam().isCompleted()) completed.getValue().add(temp);
      else upcoming.getValue().add(temp);
    }

  }

  /**
   * Binds a property to the upcoming ObjectProperty for one-way accessing and managing of the upcoming ListView.
   *
   * @param property the ObjectProperty value to which upcoming will be bound to
   */
  public void bindUpcoming(ObjectProperty<ObservableList<Result>> property){
    property.bind(upcoming);
  }

  /**
   * Binds a property to the completed ObjectProperty for one-way accessing and managing of the completed ListView.
   *
   * @param property the ObjectProperty value to which completed will be bound to
   */
  public void bindCompleted(ObjectProperty<ObservableList<Result>> property){
    property.bind(completed);
  }
}
