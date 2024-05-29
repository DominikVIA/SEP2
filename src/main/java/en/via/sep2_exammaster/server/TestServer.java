package en.via.sep2_exammaster.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The TestServer class acts as the server of the application. It has the main method, so it can be started.
 * <p>
 * Since it is the starting point for the server, it first creates a registry using an unused port number (in this app it is 1099),
 * it instantiates a ServerConnectorImplementation object, binds it to the registry using the name "server" (duh),
 * and prints a simple message to show that it is ready for clients.
 */
public class TestServer {
  /**
   * The main method to start the server.
   *
   * @param args the command line arguments (not used)
   * @throws Exception if an exception occurs during server initialization
   */
  public static void main(String[] args) throws Exception {
    Registry registry = LocateRegistry.createRegistry(1099);
    ServerProtectionProxy server = new ServerProtectionProxy();
    registry.bind("server", server);
    System.out.println("Server ready");
  }
}

