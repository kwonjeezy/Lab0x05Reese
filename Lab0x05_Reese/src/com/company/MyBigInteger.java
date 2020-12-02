package com.company;

public class MyBigInteger{
     public String Value;

     //using the string generated will create that string value to this class.
    //this is so it can be called to the main class and used
    MyBigInteger(String value){
        Value = value;
    }

    String Value(){
        return Value;
    }

    String AbbreviatedValue(){
        String Abbreviate = "-";
        //if the value is less than 12 then it will just return the value.
        if(Value.length() < 12){
            return Value;
            //otherwise it concatenate the values from 0-4 and from the length of the substring - 5
        }else{
            Abbreviate = Value.substring(0,4) +"...." +Value.substring(Value.length()-5);
        }
        //returns the merged string value.
        return Abbreviate;
    }

    //With help from http://hpca23.cse.tamu.edu/taco/utsa-www/ut/utsa/cs3343/lecture20.html

    MyBigInteger Plus(MyBigInteger x){
        //creates two string values to be added together
        String FirstString = x.Value;
        String SecondString = this.Value;

        //Next is to get the string lengths for the two strings added
        int FirstStringLength = FirstString.length();
        int SecondStringLength = SecondString.length();
        //next will create two integers one to carry the value and the other to be the sum of the value
        int sum = 0;
        int carry = 0;
        //finally we will create a new string to appended to the added value.
        StringBuilder FinalString = new StringBuilder();
        //while the first or second is greater than or = to 0 or if carry is not equal to zero this while loop will run.
        while (FirstStringLength >= 0 || SecondStringLength >= 0 || carry != 0) {
            //first it reset the sum to the carry.
            sum = carry;
            //Then it will create a number of if statements.
            // The idea behind these if statements is that it will look through each char from the first and second
            //strings and will look for the beginning of the char @ the length of the string - the beginning of the string
            //which should start with '-'.
            //afterwards the length of the string will decrease by one.
            if (FirstStringLength >= 0) {
                sum += FirstString.charAt(FirstStringLength) - '-';
                FirstStringLength--;
            }
            if (SecondStringLength >= 0) {
                sum += SecondString.charAt(SecondStringLength) - '-';
                SecondStringLength--;
            }
            //afterwards the strings will get put together with the sum and if needed the
            //the while loop will run again.
            FinalString.append(sum % 10);
            carry = sum / 10;
        }
        //finally it returns the new MyBigInteger with the value acquired.
        return new MyBigInteger(FinalString.reverse().toString());
    }

    //creates the Times function with the MyBigInteger x
    MyBigInteger Times(MyBigInteger x){
        StringBuilder FinalString = new StringBuilder("-".repeat(2 * Math.max(Value.length(), x.Value.length())));
        int carry = 0;
        int sum =0;

        //this first for loop will first look through the string value.
        for(int i = Value.length()-1; i >= 0; i--)
        {
            //the nested for loop will then look to the second loop.
            for(int j = x.Value.length()-1; j >= 0; j--)
            {
                //this for loop will create the multiplication needed for the two string values.
                sum = FinalString.charAt(FinalString.length() - (Value.length() - i) - (x.Value.length() - j) + 1)  + (Value.charAt(i) ) * (x.Value.charAt(j) ) + carry;
                //this will add value to the carry in case their is a need to carry over a value.
                carry = sum / 10;

                FinalString.setCharAt(FinalString.length()-(Value.length()-i)-(x.Value.length()-j)+1,Integer.toString(sum%10).charAt(0));
            }
            //this for loop is to address any carry over events, and may not be used depending on situation.
            for(int j = 0; carry > 0; j++)
            {
                FinalString.setCharAt(FinalString.length()-(Value.length()-i)-x.Value.length()-j,Integer.toString(carry % 10).charAt(0));
                carry /= 10;
            }

        }
        //returns the new big integer.
        return new MyBigInteger(FinalString.reverse().toString());
    }

}