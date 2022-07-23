package Model.NetworkRelated;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkController {
    private static NetworkController instance;
    private NetworkController() {}
    public static NetworkController getInstance() {
        if (instance == null) instance = new NetworkController();
        return instance;
    }

    private ServerSocket serverSocket;
    public void initializeServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    public void listenForClients() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            new SocketHandler(socket).start();
        }
    }

}
