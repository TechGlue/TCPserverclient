//Luis Garcia
//CSCD 330
public class numWords {

    //Word bank for all cases of passed in numbers.
    private String[] singleNumbers = {"","one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private String[] teenNumbers = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen","sixteen", "seventeen", "eighteen", "nineteen"};
    private String[] tens = {"","", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private String[] misc = {"","thousand ", "million ", "billion "};

    public static void main (String[] args){
        numWords ref = new numWords();
        System.out.println("Number passed in .699" +
                "");
        System.out.println(ref.partitionAndOutput(".699"));
        System.out.println(ref.isANumber("21371293872139878291371923"));
        System.out.println(ref.isANumber("."));

    }

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
            return "Negative value passed in error...";
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
    //helper method that parses values less than three
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
            System.out.println("The passed in value is not a number.");
            return false;
        }
        return true;
    }
}
