package en.via.sep2_exammaster.view.student;

import en.via.sep2_exammaster.shared.Exam;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.student.MyExamsViewModel;
import en.via.sep2_exammaster.viewmodel.teacher.AddResultsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.io.IOException;

public class MyExamsViewController {

  @FXML public Button viewExamButton;
  @FXML public ListView<Exam> examsList;

  private ViewHandler viewHandler;
  private MyExamsViewModel viewModel;
  private Region root;

  @FXML void onClick() {
    viewExamButton.setDisable(true);
    if(examsList.getSelectionModel().getSelectedItem() != null)
      viewExamButton.setDisable(false);
  }

  @FXML void onViewExam() {

  }

  public void init(ViewHandler viewHandler, MyExamsViewModel myExamsViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = myExamsViewModel;
    this.root = root;

    viewModel.bindExam(examsList.itemsProperty());
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
