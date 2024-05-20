package en.via.sep2_exammaster.viewmodel.student;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class MyExamsViewModel{
  private final Model model;
  private final ObjectProperty<ObservableList<Result>> upcoming;
  private final ObjectProperty<ObservableList<Result>> completed;

  public MyExamsViewModel(Model model){
    this.model = model;
    upcoming = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    completed = new SimpleObjectProperty<>(FXCollections.observableArrayList());
  }

  public void viewResult(Result result){
    model.viewResult(result);
  }

  public void reset() throws IOException {
    upcoming.getValue().setAll();
    completed.getValue().setAll();
    ArrayList<Result> results = (ArrayList<Result>) model.getResults();
    for(Result temp : results){
      if(temp.getExam().isCompleted()) completed.getValue().add(temp);
      else upcoming.getValue().add(temp);
    }

  }

  public void bindUpcoming(ObjectProperty<ObservableList<Result>> property){
    property.bind(upcoming);
  }

  public void bindCompleted(ObjectProperty<ObservableList<Result>> property){
    property.bind(completed);
  }
}
