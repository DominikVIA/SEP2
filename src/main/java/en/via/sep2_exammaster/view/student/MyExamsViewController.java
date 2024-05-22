package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.shared.Result;
import en.via.sep2_exammaster.view.ViewFactory;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.MyExamsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class MyExamsViewController {

  @FXML public Button viewExamButton;
  @FXML public ListView<Result> upcomingList;
  @FXML public ListView<Result> completedList;
  @FXML public TabPane tabPane;

  private ViewHandler viewHandler;
  private MyExamsViewModel viewModel;
  private Region root;

  @FXML void onClick() {
    viewExamButton.setDisable(true);
    if(upcomingList.getSelectionModel().getSelectedItem() != null
        || completedList.getSelectionModel().getSelectedItem() != null)
      viewExamButton.setDisable(false);
  }

  @FXML void onViewExam() {
    if(completedList.getSelectionModel().getSelectedItem() != null) {
      viewModel.viewResult(completedList.getSelectionModel().getSelectedItem());
      viewHandler.openView(ViewFactory.RESULT_INFO);
    }
    else {
      viewModel.viewResult(upcomingList.getSelectionModel().getSelectedItem());
      viewHandler.openView(ViewFactory.RESULT_INFO);
    }
  }

  @FXML void onViewAnalytics(){
    viewHandler.openView(ViewFactory.STUDENT_ANALYTICS);
  }

  public void init(ViewHandler viewHandler, MyExamsViewModel myExamsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = myExamsViewModel;
    this.root = root;

    tabPane.getSelectionModel().selectedItemProperty().addListener(
        (ov, t, t1) -> {
          upcomingList.getSelectionModel().clearSelection();
          completedList.getSelectionModel().clearSelection();
          viewExamButton.setDisable(true);
        });

    viewModel.bindUpcoming(upcomingList.itemsProperty());
    viewModel.bindCompleted(completedList.itemsProperty());
    viewExamButton.setDisable(true);
  }

  public Region getRoot() {
    return root;
  }

  public void reset() {
    try{
      viewExamButton.setDisable(true);
      viewModel.reset();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
