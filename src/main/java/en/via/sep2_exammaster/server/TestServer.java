package en.via.sep2_exammaster.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TestServer {
  public static void main(String[] args) throws Exception {
    Registry registry = LocateRegistry.createRegistry(1099);
    ServerConnectorImplementation server = new ServerConnectorImplementation();
//    Remote remote = UnicastRemoteObject.exportObject(server, 0);
    registry.bind("server", server);
    System.out.println("server ready");
  }
}
