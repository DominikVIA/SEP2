package en.via.sep2_exammaster.view;

import en.via.sep2_exammaster.viewmodel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * The class responsible for managing and showing all the different windows and pages that have to be loaded in.
 */
public class ViewHandler {
  private final Scene currentScene;
  private Stage primaryStage;
  private final ViewFactory viewFactory;

  /**
   * Creates a new instance of the ViewHandler class, initializing it with the specified ViewModelFactory instance.
   *
   * @param viewModelFactory Used by the application to initialize the ViewModel classes,
   *                        representing the intermediary layer between app logic and app GUI.
   */
  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewFactory = new ViewFactory(this, viewModelFactory);
    this.currentScene = new Scene(new Region());
  }


  /**
   * Starts the application and ensures the Login view is the first view loaded in by the application.
   *
   * @param primaryStage The apps application window used to display the GUI.
   */
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    openView(ViewFactory.LOGIN);
  }

  /**
   * Displays views requested by the application.
   *
   * @param id ID of the view to be opened by the application.
   */
  public void openView(String id) {
    Region root = viewFactory.load(id);
    currentScene.setRoot(root);
    if (root.getUserData() == null) {
      primaryStage.setTitle("");
    } else {
      primaryStage.setTitle(root.getUserData().toString());
    }
    primaryStage.setScene(currentScene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }

  /**
   * Closes the graphic view of the application being closed.
   */
  public void closeView() {
    primaryStage.close();
  }
}
