import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
    public static void main(String[] args) throws Exception {

        try(ServerSocket welcomeSocket = new ServerSocket(6787)) {
            System.out.println("SERVER ON....");

            while(true) {
                Socket connectionSocket = welcomeSocket.accept();

                System.out.println("CLIENT SUCCESSFULLY CONNECTED");

                InputStream input = connectionSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream out = connectionSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);

                String clientSentence = "";
                String capitalizedSentence;

                do {
                    try {
                        numWords ref = new numWords();//for calling numToWords

                        clientSentence = reader.readLine();
                        capitalizedSentence = ref.partitionAndOutput(clientSentence) + '\n';//calling num to words convert
                       writer.println("FROM SERVER: " + capitalizedSentence);
                    }
                    catch(java.lang.NumberFormatException e){
                      writer.println("THE NUMBER THAT YOU PASSED IN IS TOO BIG OR NOT A NUMBER PLZ TRY AGAIN!!!!");
                    }
                }while (!clientSentence.equals("00"));
                System.out.println("CLIENT DISCONNECTED");
                connectionSocket.close();
            }
        }
    }
}
