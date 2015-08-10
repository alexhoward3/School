/*
 * Alex Howard
 * CS 1050-002
 * 
 * This program has a user enter a temperature for a specific day and prints out
 * the number of days each specific temperature occurs in groups of:
 * Below 60, 60s, 70s, 80s, 90s, and above 100.
 * 
 * When the user enters a negative number the program is signaled to stop input
 * and calculate the number of days each temperature range occurs.
 * 
 * The output is then a table that prints the temperature ranges, the days
 * of occurrence of each range, and the temperature values in each group.
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Weather_Statistics
{
    public static void main(String[] args)
    {
        //Initializing variables
        Scanner key = new Scanner(System.in); //Scan user input from variable "key"
        double enteredTemp; //The temperature the user enters
        int tempCount = 1; //Holds the count of how many temperatures the user has entered
        String numberOfDaysMess = "The number of days "; //Message for the number of days

        ArrayList<Double> tempGT100 = new ArrayList<Double>(); //Sizable array for values greater than 100
        ArrayList<Double> temp90s = new ArrayList<Double>(); //Sizeable array for values in the 90s
        ArrayList<Double> temp80s = new ArrayList<Double>(); //Sizeable array for values in the 80s
        ArrayList<Double> temp70s = new ArrayList<Double>(); //Sizeable array for values in the 70s
        ArrayList<Double> temp60s = new ArrayList<Double>(); //Sizeable array for values in the 60s
        ArrayList<Double> tempLT60 = new ArrayList<Double>(); //Sizable array for values less than 60

        //Greeting message
        System.out.println("Welcome! Please enter all of the temperature values.");
        System.out.println("(Enter a negative value to calculate)\n");
        
        //Loop that asks user to enter numbers and calculates amount of days per temperature
        do{
            
            //User input
            System.out.print("Temperature " + tempCount + ": ");
            enteredTemp = key.nextDouble();
            double tempVal = new Double(enteredTemp);
            tempCount++;

            if(enteredTemp >= 100) //For temperatures greater than 100
            {
                tempGT100.add(tempVal);
            } 
            else if(enteredTemp < 100 && enteredTemp >= 90) //For temperatures in the 90s
            {
                temp90s.add(tempVal);
            }
            else if(enteredTemp < 90 && enteredTemp >= 80) //For temperatures in the 80s
            {
                temp80s.add(tempVal);
            }
            else if(enteredTemp < 80 && enteredTemp >= 70) //For temperatures in the 70s
            {
                temp70s.add(tempVal);
            }
            else if(enteredTemp < 70 && enteredTemp >= 60) //For temperatures in the 60s
            {
                temp60s.add(tempVal);
            }
            else if(enteredTemp < 70 && enteredTemp >= 0) //For temperatures below 60
            {
                tempLT60.add(tempVal);
            }
            else{System.out.println("\n");} //If the input isn't within the ranges, the program prints a "\n"
        }while(enteredTemp >= 0);
        
        //Prints the table to show the user how many days each temperature range occurs
        System.out.println(numberOfDaysMess + "100+ : " + tempGT100.size() + " " + tempGT100);
        System.out.println(numberOfDaysMess + "90s  : " + temp90s.size()+ " " + temp90s);
        System.out.println(numberOfDaysMess + "80s  : " + temp80s.size() + " " + temp80s);
        System.out.println(numberOfDaysMess + "70s  : " + temp70s.size() + " " + temp70s);
        System.out.println(numberOfDaysMess + "60s  : " + temp60s.size() + " " + temp60s);
        System.out.println(numberOfDaysMess + "-60s : " + tempLT60.size() + " " + tempLT60);
    }
}