import java.io.BufferedReader;                                //reads text from input stream (client messages)
import java.io.IOException;                                      // handles input/output errors
import java.io.InputStreamReader;                     //convert raw byte stream to text stream
import java.io.PrintWriter;                                    // sends text data to the client
import java.net.ServerSocket;                            // server's door, waits for client to connect
import java.net.Socket;                                        // represent the client's connnection once accepted
public class Server {
    private static boolean isClientConnected = false;       // shared flag indicating if client is currrently active
  // ^ initially 'false' as it is server free

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {         // creates a server listening in port 1234
    // 'try' ensures that server sockt closes automatically when program ends
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();     //accept a client and once client connects, it returns a socket object for communication

                // If already a client is connected
                if (isClientConnected) {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);   //auto flush
                    out.println("Server is busy. Please wait until the current client disconnects.");
                    clientSocket.close();
                    continue;
                }

                isClientConnected = true;        //new clients will be told busy until this handler finishes and sets it false
                System.out.println("Client connected.");
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
// ClientHandler is an inner static class extending Thread. It stores the Socket for its client
//extends Thread means this class defines a run() method; when start() is called, the JVM runs run() in a new OS-level thread.
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {    // run is the thread entry
// we open try with resources(in and out); the try-with-resources ensures in and out are closed automatically at the end of the block
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                out.println("You are connected to the server. Start chatting! (Type 'exit' to disconnect)");
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        out.println("You have disconnected.");
                        break;
                    }
                    System.out.println("Client: " + message);
                    out.println("Server: " + message.toUpperCase());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {       // 'finally' executes regardless — it's used for final cleanup and resetting state.
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isClientConnected = false;
                System.out.println("Client disconnected. Waiting for next client...");
            }
        }
    }
}
