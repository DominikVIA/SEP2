package en.via.sep2_exammaster.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestServer {
  public static void main(String[] args) throws Exception
  {
    Registry registry = LocateRegistry.createRegistry(1099);
    ServerConnectorImplementation server = new ServerConnectorImplementation();
    registry.bind("examserver", server);
    System.out.println("server ready");
  }
}
