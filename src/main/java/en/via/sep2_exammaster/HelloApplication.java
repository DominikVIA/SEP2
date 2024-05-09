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

public class HelloApplication extends Application
{
  static Model client;

  @Override public void start(Stage primaryStage) throws Exception {
    Registry registry = LocateRegistry.getRegistry(1099);
    ServerConnector serverConnector = (ServerConnector) registry.lookup("server");
    client = new ModelManager(serverConnector);
    ViewModelFactory viewModelFactory = new ViewModelFactory(client);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }

  @Override public void stop() throws Exception {
    client.close();
  }

  public static void main(String[] args) {
    launch();
  }
}