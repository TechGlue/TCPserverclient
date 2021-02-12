//Luis Garcia
//Assignment 2
//CSCD 330
/*
    Problem description: The goal of this program is to implement a number to words converter utilizing
    a TCPServer and Client.
 */
import java.io.*;
import java.net.Socket;

/*
    Much like the server we create a socket that'll connect to our designated port but no functions other than
    reading sending and receiving input to the server. When 00 is sent out we'll close the client.
 */

public class TCPClient {
    public static void main(String[] args){
        try (Socket clientSocket = new Socket("localhost", 6787)) {

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Console console = System.console();

            String clientSentence;//original sentence before server
            String modifiedSentence;//after the server makes the changes

            //Sending and receiving values.
            do{
                clientSentence = console.readLine("Enter a number: ");
                writer.println(clientSentence);

                InputStream input = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                modifiedSentence = reader.readLine();
                System.out.println(modifiedSentence);
            }while(!clientSentence .equals("00"));
        }catch (Exception e){
            System.out.println("error...");
        }
    }
}
