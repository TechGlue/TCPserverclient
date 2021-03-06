//Luis Garcia
//Assignment 2
//CSCD 330
/*
    Problem description: The goal of this program is to implement a number to words converter utilizing
    a TCPServer and Client.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    //Word bank for all cases of passed in numbers.
    private String[] singleNumbers = {"","one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private String[] teenNumbers = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen","sixteen", "seventeen", "eighteen", "nineteen"};
    private String[] tens = {"","", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private String[] misc = {"","thousand ", "million ", "billion "};

    /*The main function of this class is to create a server socket and be listening for incoming connections to our designated port
      and on connection we'll be listening for client input and based on whether it's 00 or a value we can convert
      we'll send out a string pertaining what was passed in from the client. Once the client has given us the command to
      end the program we close the socket and the client disconnects.
     */
    public static void main(String[] args) throws Exception {
        TCPServer ref = new TCPServer();//for calling non static methods.

        try(ServerSocket welcomeSocket = new ServerSocket(6787)) {
            System.out.println("SERVER ON....");

            while(true) {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("CLIENT SUCCESSFULLY CONNECTED");
                //setting up our readers
                InputStream input = connectionSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                //setting up writers
                OutputStream out = connectionSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);
                //misc for catching values
                String clientSentence = "";
                String wordToNum;
                //main function of the program
                do {
                    try {
                        clientSentence = reader.readLine();
                        if(!ref.isANumber(clientSentence)){
                            writer.println("FROM SERVER: The passed in value is not a number, please try again! ");
                        }
                        else if(clientSentence.equals("00")){
                            writer.println("FROM SERVER: Thank you, come again");
                        }else {
                            wordToNum = ref.partitionAndOutput(clientSentence) + '\n';//calling num to words convert
                            writer.println("FROM SERVER: " + wordToNum);
                        }
                    }
                    catch(java.lang.NumberFormatException e){
                        writer.println("FROM SERVER: The number that you passed in is too big, please try again!");
                    }
                }while (!clientSentence.equals("00"));
                System.out.println("CLIENT DISCONNECTED");
                connectionSocket.close();
            }
        }
    }//end of main

    //Breaks down our inputs and returns an output String.
    public String partitionAndOutput(String temp){
        //variables for breaking down our number into two portions, the decimal and whole number half.
        String decimalNum = "";
        String num = "";

        int index = 0;
        int numLength = temp.length();

        //grabbing the whole number
        while(index < numLength){
            if(temp.charAt(0) == '.'){
                num +=0;
                break;
            }
            if(temp.charAt(index) == '.'){
                break;
            }
            num += temp.charAt(index);
            index++;
        }
        //grabbing the remainder
        while (index < temp.length()){
            decimalNum += temp.charAt(index);
            index++;
        }
        if(num.charAt(0) == '-'){
            return "Negative value passed in, please try again!";
        }
        //printing num-words based on cases
        //if we have no decimals then we will follow this method
        else if(decimalNum.isEmpty()){
            //calling the conversion and packaging up all the string to print to the screen.
            String target = wordToNum(Integer.parseInt(num));
            target = Character.toUpperCase(target.charAt(0)) + target.substring(1,target.length());
            return target + "and zero hundredths\n";
        }
        else{//else we will go through the process of translating the decimal and whole number portion of the passed in value.
            if(num.equals("")){
                num = "0";
            }
            //rounding to the hundredths
            double rightHalf = Double.parseDouble(decimalNum);
            rightHalf = rightHalf * 100;
            rightHalf = Math.round(rightHalf);
            //

            //calling the conversion and packaging up all the string to print to the screen.
            String target = wordToNum(Integer.parseInt(num));
            target = Character.toUpperCase(target.charAt(0)) + target.substring(1,target.length());
            return target + "and " + wordToNum((int) rightHalf) + "hundredths";
        }
    }

    //driver method
    /*This method is in charge of checking over edge cases
      and breaking down our passed in value and converting broken down portions to text till we reach a num of 0.
      Once reached we return our target string that will hold all the translated values.
     */
    public String wordToNum(int num){
        if(num == 0){
            return "zero ";
        }

        String target = "";
        int i = 0;

        while(num > 0){
            int tempNum = num % 1000;

            if(tempNum != 0){
                String str = valuesLessThanThree(tempNum);
                target = str + " "+ misc[i] + target;
            }
            i++;
            num = num / 1000;
        }
        return target;
    }
    //helper method for our driver method that parses values less than three
    public String valuesLessThanThree (int num){
        String target = "";
        int tempNum = num % 100;

        if(tempNum < 10){
            target = target + singleNumbers[tempNum];
        }
        else if(tempNum < 20){
            target = target + teenNumbers[tempNum % 10];
        }else{
            if(num % 10 == 0){
                target = tens[tempNum/10] + singleNumbers[tempNum%10];
            }else {
                target = tens[tempNum / 10] + "-" + singleNumbers[tempNum % 10];
            }
        }
        if(num/100 > 0){
            target = singleNumbers[num/100] + " hundred "+ target;
        }
        return target.trim();
    }

    //method for doing the intial check on the passed in value whether its a parsable number or not.
    public boolean isANumber (String input){
        if(input == null){
            return false;
        }
        try{
            Double temp = Double.parseDouble(input);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}//end of class