import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) throws Exception { // throws exception: keeps the code simple by ignoring the detailed error handling.
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));// let user type on console.

        System.out.print("Enter server IP (e.g., 127.0.0.1): ");
        String serverIP = userInput.readLine();

        Socket socket = new Socket(serverIP, port:1234);// create connection b/w server IP & port 1234.
        System.out.println("Connected to server!");

        PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush:true);// sets output channel to send the message to server.
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//sets input channel to receive message from server.

        out.println("Hello from client!");
        
        String response = in.readLine();// waits to receive a reply from server.
        System.out.println("Server says: " + response);

        socket.close();
    }
}
