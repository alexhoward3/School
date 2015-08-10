/*
 * ALEX HOWARD
 * CS 1050-002
 * 
 * This program generates 1000 integers between 1 and 4 in a random fashion
 * within specific percentage values.
 * 
 * 1 occurs with a chance of 30%, 2 with 17%, 3 with 21%, and 4 with 32%.
 * 
 */
import java.util.*;

public class Problem1
{
    public static void main(String[] args)
    {
        Random rand = new Random(); //Class to generate random numbers
        int max = 100, min = 1; //Minimum and maximum value bound for the random number generated
        int count, randomNum; //Initialization variables
        
        //loop that generates the numbers
        for(randomNum = 0, count = 1; count <= 1000; randomNum++, count++)
        {
            randomNum = rand.nextInt(max - min + 1) + min; //Generate random number between max and min
            
            if(randomNum <= 1 && randomNum <= 30)
                randomNum = 1;
            else if(randomNum <= 31 && randomNum <= 47)
                randomNum = 2;
            else if(randomNum <= 48 && randomNum <= 68)
                randomNum = 3;
            else
                randomNum = 4;
            
            System.out.println(count + ": " + randomNum); //Print the numbers
        }
    }
}