package com.company;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.util.Random;

public class Main {
    static int trialnum = 100;
    static double mxTime = 2000000;


    //Used https://www.geeksforgeeks.org/large-fibonacci-numbers-java/ for help.
    //this creates the fibloop function
    public static BigInteger fibLoop(int x)
    {
        //creates values for the BigIntegers to run off of.
        BigInteger FirstNumber = new BigInteger("0");
        BigInteger SecondNumber = new BigInteger("1");
        BigInteger sum;

        //will get the values of the first 2 numbers
        FirstNumber.valueOf(0);
        SecondNumber.valueOf(1);
        if(x == 0){
            return BigInteger.ZERO;
        }
        // This will then add the two numbers together
        for(int i = 2; i <= x; i++)
        {
            sum = FirstNumber.add(SecondNumber);
            FirstNumber = SecondNumber;
            SecondNumber = sum;
        }
        return SecondNumber;

    }
    //next will create the fibMatrix function.
    public static BigInteger fibMatrix(int x){
        BigInteger FibM[][] = new BigInteger[][]{{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE,BigInteger.ZERO}};
        //if the integer of x is zero then will return bigInteger with zero
        if(x == 0){
            return BigInteger.ZERO;
        }
        //next it will boot up the power function.
        power(FibM,x-1);

        return FibM[0][0];
    }
    public static void power(BigInteger Fib[][], int x){
        BigInteger matrix[][] = new BigInteger[][] {{BigInteger.ONE,BigInteger.ONE},{BigInteger.ONE,BigInteger.ZERO}};

        //this for loop will run for the value of x, minus the first 2 integers
        for(int i = 2; i <= x; i++){
            //which will send it to the multiply function.
            multiply(Fib,matrix);
        }
    }
    //the multiply function will create new BigInt values.
    //https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
    public static void multiply(BigInteger Fib[][], BigInteger matrix[][]){
        BigInteger a = (Fib[0][0].multiply(matrix[0][0])).add((Fib[0][1].multiply(matrix[1][0])));
        BigInteger b = (Fib[0][0].multiply(matrix[0][1])).add((Fib[0][1].multiply(matrix[1][1])));
        BigInteger c = (Fib[1][0].multiply(matrix[0][0])).add((Fib[1][1].multiply(matrix[1][0])));
        BigInteger d = (Fib[1][0].multiply(matrix[0][1])).add((Fib[1][1].multiply(matrix[1][1])));

        Fib[0][0] = a;
        Fib[0][1] = b;
        Fib[1][0] = c;
        Fib[1][1] = d;
    }

    public static void main(String[] args) {
        //creating variables

        double LoopTime[] = new double[9000];
        double fibmatrixTime[] = new double[9000];
        boolean continuerun;
        double loopDouble[] = new double[9000];

        int x = 0;
        int Xval = 1;
        double avg = 0;
        long N;
        double initial;
        double endTime;
        int count = 0;
        BigInteger num1 = BigInteger.ZERO;

        boolean Loopcontinuerun = true;
        boolean MatrixContinueRun = true;
        continuerun = true;

        double LoopExpected= 0;
        double MatExp[] = new double[900];
        double fmedr = 0;

        //while continue to run is still true the program will iterate.
        while (continuerun) {
            //will create n into the log of the int using x
            N = (int) Math.ceil(Math.log(2 * (x + 1)));
            //starts the first Fib loop for the program
            if (Loopcontinuerun ) {
                //Will be gettinth the time for the fib loop
                for (int i = 0; i < trialnum; i++) {
                    initial = getCpuTime();
                    fibLoop(x);
                    endTime = getCpuTime();
                    avg += endTime - initial;
                }
                //this will create the avaerage time Which would be the average amount of times multiplied by the
                //amount of trial numbers there are.
                avg = avg / trialnum;
                LoopTime[count] = avg;
                //once the expected time elapses then the count will close.
                if (LoopTime[count] > mxTime) {
                    Loopcontinuerun  = false;
                }
                if (x > 2) {
                    loopDouble[count] = LoopTime[count] / LoopTime[x / 10];
                    LoopExpected = loopDouble[count - 1] + loopDouble[count - 2];
                }
            }
            avg = 0;
            //will do the same as loop to the matrix
            if ( MatrixContinueRun) {
                for (int i = 0; i < trialnum; i++) {
                    initial = getCpuTime();
                    num1 = fibMatrix(x);
                    endTime = getCpuTime();
                    avg += endTime - initial;
                }
                //creating average
                avg = avg / trialnum;
                fibmatrixTime[count] = avg;
                if (fibmatrixTime[count] > mxTime) {
                    //completing program once time runs out
                    MatrixContinueRun = false;
                }
                LoopExpected = 0;
                if (x > 2) {
                    //this will create the expected matrix time needed
                    MatExp[count] = fibmatrixTime[count] / fibmatrixTime[x / 10];
                    fmedr = MatExp[count - 1] + MatExp[count - 2];

                }
            }
            //once both loop and matrix closes then the program can close.
            if (!Loopcontinuerun && !MatrixContinueRun) {
                continuerun = false;
            }
            String First = num1.toString();
            if (First.length() > Xval) {
                Xval++;
            }
            //print results.
            System.out.printf("N(size) = %d \t x(input value) = %d \t loop  = %f \t matrix  = %f \t", Xval, x, LoopTime[count], fibmatrixTime[count]);

            if (N > 1)
                fmedr = Math.log((double) N) / Math.log((double) N - 1);
            if (First.length() > 12) {
                String last = First.substring(0, 5);
                First = First.substring(First.length() - 5, First.length());
                System.out.printf("Fib(x) = %s...%s \t 10x ratio = %f \t Tx expected 10x ratio = %f \t Tn expected +1 ratio = %s\n", last, First, loopDouble[count], fmedr, 0);
            } else {
                System.out.printf("Fib(x) = %s \t 10x ratio = %f \t Tx expected 10x ratio = %f \t Tn expected +1 ratio = %s\n", First, MatExp[count], fmedr, 0);
            }
            count++;
            x++;


        }

/*
        //creating values for the testing and running of program.

        long N[] = new long[9000];
        String firstString[] = new String[9000];
        String SecondString[] = new String[9000];

        long NVal = 1;
        boolean continuerun;
        String temp;
        int val;
        int count = 0;
        double before;
        double after;


        //while continue run is true will keep iterating.
        continuerun=true;
        while(continuerun){
            //this portion is to create a random selection of ints
            N[count] = NVal;
            val = ((int) new Random().nextInt());
            //after words the value will modulate by 10
            val = val %10;
            //and then using the temp string variable convert the integers to string.
            //This temp is what will be used in part to get the value that we will be doing our
            //adding and multiplying.
            temp = Integer.toString(val);

            for(int i =1; i < NVal; i++){
                val = ((int) new Random().nextInt());
                val = val %10;
                while(val < 0){
                    val = ((int) new Random().nextInt());
                    val = val %10;
                }
                temp = temp + val;
            }

            firstString[count] = temp;
            val = ((int) new Random().nextInt());
            val = val %10;
            //next it will change the temp from an integer back to a string
            temp = Integer.toString(val);
            //will create a for loop that will iterate until the Nvalue will have run its course
            for(int i =1; i < NVal; i++){
                val = ((int) new Random().nextInt());
                val = val %10;
                while(val < 0){
                    val = ((int) new Random().nextInt());
                    val = val %10;
                }
                //finally will create use the value created from above and add it to the temp string
                temp = temp + val;
            }
            double add[] = new double[9000];
            double mult[] = new double[90000];

            //this will run the addition and multiplication aspects of the program as well as the double ration and
            //expected double ration
            SecondString[count] = temp;
            MyBigInteger num1 = new MyBigInteger(firstString[count]);
            MyBigInteger num2 = new MyBigInteger(SecondString[count]);
            //will also get time.
            before = getCpuTime();
            after = getCpuTime();
            add[count] = after - before;
            before = getCpuTime();
            after = getCpuTime();
            mult[count] = after - before;

            // so according to my results my doubling ratio is currently infinity and the expected is 0;
            // This does not seem to be accurate...
            double doubleRatio[] = new double[19000];
            double expdr[] = new double[9000];
            if(NVal == 1){
                doubleRatio[count] = 0;
                expdr[count] = 0;

            }else{
                doubleRatio[count] = add[count]/add[count-1];
            }

            if(add[count] > mxTime || mult[count] > mxTime){
                continuerun = false;
            }
            //prints the information.

            System.out.printf("N = %d \t Num 1 = %s \t Num 2 = %s \t Add = %f \t Multiply = %f \t Doubling ratio = %f \t Expected double ration = %f\n",NVal, firstString[count], SecondString[count], add[count], mult[count],doubleRatio[count],expdr[count]);
            count++;
            NVal = NVal * 2;
        }
*/


    }

    public static long getCpuTime( ) {

        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );

        return ((ThreadMXBean) bean).isCurrentThreadCpuTimeSupported( ) ?

                bean.getCurrentThreadCpuTime( ) : 0L;

    }


}