import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws Exception {

        try (Socket clientSocket = new Socket("localhost", 6787)) {

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            //
            Console console = System.console();

            String ogSentence;//original sentence before server
            String modifiedSentence;//after the server makes the changes
            do{
                ogSentence = console.readLine("Enter a number: ");
                writer.println(ogSentence);
                InputStream input = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                modifiedSentence = reader.readLine();
                System.out.println(modifiedSentence);
            }while(!ogSentence .equals("00"));
            clientSocket.close();
        }catch (NumberFormatException e){
            System.out.println("INVALID INPUT...");
        }
    }
}
