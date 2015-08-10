import java.util.Scanner;
import java.util.ArrayList;

public class Weather_Statistics2
{
    public static void main(String[] args)
    {
        //Initializing variables
        Scanner key = new Scanner(System.in); //Scan user input from variable "key"
        double enteredTemp; //The temperature the user enters
        int tempCount = 1; //Holds the count of how many temperatures the user has entered
        String numberOfDaysMess = "The number of days "; //Message for the number of days
        ArrayList<int> temps = new ArrayList<int>(6);
        
        System.out.println("Welcome! Please enter all of the temperature values.");
        System.out.println("(Enter a negative value to calculate)\n");
        
        do{

            System.out.print("Temperature " + tempCount + ": ");
            enteredTemp = key.nextDouble();
            double tempVal = new Double(enteredTemp);
            tempCount++;

            if(enteredTemp >= 100) //For temperatures greater than 100
            {
                temps[0] += 1;
            } 
            else if(enteredTemp < 100 && enteredTemp >= 90) //For temperatures in the 90s
            {
                temps[1] += 1;
            }
            else if(enteredTemp < 90 && enteredTemp >= 80) //For temperatures in the 80s
            {
                temps[2] += 1;
            }
            else if(enteredTemp < 80 && enteredTemp >= 70) //For temperatures in the 70s
            {
                temps[3] += 1;
            }
            else if(enteredTemp < 70 && enteredTemp >= 60) //For temperatures in the 60s
            {
                temps[4] += 1;
            }
            else if(enteredTemp < 70 && enteredTemp >= 0) //For temperatures below 60
            {
                temps[5] += 1;
            }
            else{System.out.println("\n");}
        }while(enteredTemp >= 0);

        System.out.println(numberOfDaysMess + "100+ : " + temps[0]);
        System.out.println(numberOfDaysMess + "90s  : " + temps[1]);
        System.out.println(numberOfDaysMess + "80s  : " + temps[2]);
        System.out.println(numberOfDaysMess + "70s  : " + temps[3]);
        System.out.println(numberOfDaysMess + "60s  : " + temps[4]);
        System.out.println(numberOfDaysMess + "-60s : " + temps[5]);
    }
}