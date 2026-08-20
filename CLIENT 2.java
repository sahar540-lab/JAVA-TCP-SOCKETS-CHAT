import java.io.BufferedReader;                          //reads text from input stream (client messages)
import java.io.IOException;                            // handles input/output errors
import java.io.InputStreamReader;                     //convert raw byte stream to text stream
import java.io.PrintWriter;                          // sends text data to the client
import java.net.Socket;                             // represent the client's connnection once accepted

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String serverMsg;
            // Read initial messages (like "busy" or "connected")
            while ((serverMsg = input.readLine()) != null) {
                System.out.println("Server says: " + serverMsg);

                // If server says busy, don't let client chat
                if (serverMsg.contains("busy")) {
                    return;
                }

                // Otherwise, chat loop starts
                break;
            }

            String userInput;
            while ((userInput = console.readLine()) != null) {
                out.println(userInput);
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
                String reply = input.readLine();
                System.out.println(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
