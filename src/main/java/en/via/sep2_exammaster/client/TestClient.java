package en.via.sep2_exammaster.client;

import en.via.sep2_exammaster.shared.ServerConnector;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
  public static void main(String[] args) throws Exception {
    Registry registry = LocateRegistry.getRegistry(1099);
    ServerConnector serverConnector = (ServerConnector) registry.lookup("examserver");
    System.out.println(serverConnector.getCourses());
  }
}
