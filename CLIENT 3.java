import java.io.BufferedReader;          // reads text line by line
import java.io.IOException;             // error handling
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);          // connect to server to local host 1234
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));          // 'keyboard' to use user input from console
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));        // 'in' to receive server messages
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {                       // sends messages to server immediately using auto flash
  
            System.out.println("Connected to server. Type messages (bye to exit):");

            String msg;
            while ((msg = keyboard.readLine()) != null) {
                out.println(msg); // send to server
                String response = in.readLine(); // read from server
                System.out.println(response);

                if (msg.equalsIgnoreCase("bye")) {
                    System.out.println("Disconnected.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
