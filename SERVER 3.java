import java.io.BufferedReader;                                //reads text from input stream (client messages)
import java.io.IOException;                                      // handles input/output errors
import java.io.InputStreamReader;                     //convert raw byte stream to text stream
import java.io.PrintWriter;                                    // sends text data to the client
import java.net.ServerSocket;                            // server's door, waits for client to connect
import java.net.Socket;                                        // represent the client's connnection once accepted

public class Server {
    private static int clientCounter = 0;                   // keeps track of how many clients have connected

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {                 // creates a server listening in port 1234 
       // 'try' ensures that server socket closes automatically when program ends
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket socket = serverSocket.accept();          // accept a client and once client connects, it returns a socket object for communication
                clientCounter++;                                                    // increase counter when a client connects 
                int clientId = clientCounter;                              // assign the ID to the client

                System.out.println("Client " + clientId + " connected.");

                // Create a thread using a Runnable implementation
                ClientHandler handler = new ClientHandler(socket, clientId);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();                                           // print detailed error report to the console
        }
    }
}

// Runnable class to handle client
class ClientHandler implements Runnable {
    private Socket socket;
    private int clientId;

    public ClientHandler(Socket socket, int clientId) {       //Constructor
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override    
    // it is the annotation that tells the compiler that the method following it, is intended to override a method from supperclass or implement a method from an interface
//simply it will check if a method with that name exists in the parent class else error occurs2   
public void run() {       //over riding, run method
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)                                // printwriter enables auto flush so messages are sent immediately
        ) {
            // Send welcome message to client
            out.println("Hello Client " + clientId + "! You are connected.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client " + clientId + " says: " + message);
                out.println("Echo from server: " + message);
            }

            System.out.println("Client " + clientId + " disconnected.");
        } catch (IOException e) {
            System.out.println("Error handling client " + clientId);
        }
    }
}
