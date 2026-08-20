import java.net.;
import java.io.;

public class TCPServer {
public static void main(String[] args) throws Exception {
ServerSocket serverSocket = new ServerSocket(port:1234); // 1234 could be any port number.
System.out.println("Server is running and waiting for client...");

  Socket socket = serverSocket.accept(); // pauses a program until client connects.
    System.out.println(x:"Client connected!");
    
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //read message sent from client.
    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush:true); // sent messages back to client.
    
    String clientMessage = in.readLine(); // waits until the client sends a line of text and stores it in clientMessage.
    System.out.println("Client says: " + clientMessage);
    
    out.println(x:"Hello from server!");
    
    socket.close();
    serverSocket.close();
}
}
