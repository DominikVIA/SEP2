package en.via.sep2_exammaster;

import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.model.ModelManager;
import en.via.sep2_exammaster.shared.ServerConnector;
import en.via.sep2_exammaster.view.ViewHandler;
import en.via.sep2_exammaster.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The class that launches the GUI of the program.
 *
 * @author Group SiedemSyvSiete
 * @version 1.0
 */
public class HelloApplication extends Application
{
  static Model client;

  /**
   * Starts the client-side application.
   *
   * @param primaryStage the primary stage for this application, onto which
   * the application scene can be set.
   * Applications may create other stages, if needed, but they will not be
   * primary stages.
   * @throws Exception if any exception occurs while starting the application
   */
  @Override public void start(Stage primaryStage) throws Exception {
    Registry registry = LocateRegistry.getRegistry(1099);
    ServerConnector serverConnector = (ServerConnector) registry.lookup("server");
    client = new ModelManager(serverConnector);
    ViewModelFactory viewModelFactory = new ViewModelFactory(client);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }

  /**
   * Code executed when the application is turned off.
   *
   * @throws Exception if any exception occurs while closing
   */
  @Override public void stop() throws Exception {
    client.close();
  }

  /**
   * main class duh
   * @param args we dont really know
   */
  public static void main(String[] args) {
    launch();
  }
}