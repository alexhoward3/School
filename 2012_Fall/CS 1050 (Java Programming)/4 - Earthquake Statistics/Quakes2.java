/*
 * Alex Howard
 * CS 1050-002
 * 
 * This program reads a file that has various amounts of data on earthquakes
 * across the world between September 26th and October 3rd 2012.
 * 
 * The program attemps to read in the file, get the sum of all the magnitudes,
 * the average magnitude, and the maximum and magnitude values.
 */

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Quakes2
{
    //Variable to define the Text document that holds the data
    static final String fileName = "earthquakedata.txt";

    public static void main(String[] args)
    {
        //Print statements that output the returns of each method
        System.out.println("LINE COUNT: " + getLineCount());
        System.out.printf("SUM OF MAGNITUDES: %.2f \n", getSumOfMagnitudes());
        System.out.printf("AVERAGE MAGNITUDE: %.2f \n",averageMagnitude());
        System.out.println("MAX VALUE: " + maxValue());
        System.out.println("MIN VALUE: " + minValue());

        //System.out.println("\n\nEXTRA CREDIT");
        //System.out.println("BEST CASE: " + bestCase());
        //System.out.println("WORST CASE: " + worstCase());
        //System.out.println("...");

    }

    //Method to read in the data from the file
    public static String readIn()
    {
        String getLines = "";//String to store each line of the file

        try //Tries to get the file, scan through it, and store each line in variable getLines
        {   
            File earthQuakeData = new File(fileName);
            Scanner inputFromFile = new Scanner(earthQuakeData);

            while(inputFromFile.hasNext())
            {
                getLines += inputFromFile.nextLine() + "\n";
            }
        }
        catch(IOException e) //error messaging is file is not correct
        {
            System.out.println("FILENAME NOT CORRECT!");
            System.exit(0);
        }

        return getLines; //returns the lines of the scanned file
    }

    public static int getLineCount()
    {
        int lineCount = 0; //Inital variable for the line count
        Scanner scanLines = new Scanner(readIn());

        //reads each line of the file
        while(scanLines.hasNextLine())
        {
            lineCount++;
            scanLines.nextLine();
        }

        return lineCount; //returns the count of the lines
    }

    public static double getSumOfMagnitudes()
    {
        double sumOfMagnitudes = 0.0; //initial variable for the sum of the magnitudes
        Scanner scanLines = new Scanner(readIn()); //scanner to read in the file
        scanLines.useDelimiter(","); //sets the scanner's delimiter to , to get the next double

        //reads through each line of the file
        while(scanLines.hasNext())
        {
            //if the file has another double...
            if(scanLines.hasNextDouble())
            {
                //...the next double is added to the sum
                sumOfMagnitudes += Double.parseDouble(scanLines.next());
                scanLines.nextLine(); //gets the next line of the file
            }
        }

        return sumOfMagnitudes; //returns the magnitude sum
    }

    //Calculates the average magnitude
    public static double averageMagnitude()
    {
        double average = 0.0; //inital average variable
        int lineCount = getLineCount(); //inital line count variable
        double magnitudeSum = getSumOfMagnitudes(); //inital magnitude sum variable

        average = magnitudeSum / lineCount; //calculation

        return average; //returns the average of the magnitudes
    }

    //Attemps to find the maximum magnitude value and its place of origin
    public static String maxValue()
    {
        double maxval = 0.0; //initial maximum value
        double value = 0.0; //temporary value for each double scanned in to find magnitude
        String whereAndWhen = ""; //sets the initial value for a string to get when and where the earthquake was

        Scanner scanLines = new Scanner(readIn()); //scanner to read in the file
        scanLines.useDelimiter(","); //sets the scanner's delimiter to , to get the next double

        double[] dataArray = new double[getLineCount()]; //array to store data from file(size is the number of lines in the file)

        //parses the file line by line to store each double into an array
        for(int counter = 0; counter < dataArray.length-1; counter++)
        {
            while(scanLines.hasNext())
            {
                if(scanLines.hasNextDouble())
                {
                    value = Double.parseDouble(scanLines.next());
                    scanLines.nextLine();
                    dataArray[counter] = value; //adds the value to each position in the array
                    Arrays.sort(dataArray); //sorts the array
                }
            }
        }

        Arrays.sort(dataArray); //sorts the array filled with doubles from lowest to highest

        maxval = dataArray[dataArray.length-1]; //sets maxval variable to the largest double in the array

        value = 0.0; //resets value to 0
        Scanner scanLines2 = new Scanner(readIn()); //new scanner to parse the file
        scanLines2.useDelimiter(","); //sets the scanner's delimiter to , to find the next double

        //parses the file and finds where the max value was located. Then uses this number to get when and where the quake happened
        while(scanLines2.hasNext())
        {
            if(scanLines2.hasNextDouble())
            {
                value = Double.parseDouble(scanLines2.next());
                if(value == maxval && scanLines2.hasNext())
                {
                    whereAndWhen = scanLines2.nextLine();
                    scanLines2.nextLine();
                    break;
                }
                scanLines2.nextLine();
            }
        }

        return maxval + " " + whereAndWhen; //returns the maximum magnitude value and its place, date, and time of origin
    }

    //Calculates in the same fashion as the maxValue() method only this finds the minimum magnitude value and its place of origin
    public static String minValue()
    {
        double minval = 0.0; //variable to set the minimum value
        double value = 0.0;
        String whereAndWhen = "";
        Scanner scanLines = new Scanner(readIn());

        scanLines.useDelimiter(",");

        double[] dataArray = new double[getLineCount()];

        for(int counter = 0; counter < dataArray.length-1; counter++)
        {
            while(scanLines.hasNext())
            {
                if(scanLines.hasNextDouble())
                {
                    value = Double.parseDouble(scanLines.next());
                    scanLines.nextLine();
                    dataArray[counter] = value;
                    Arrays.sort(dataArray);
                }
            }
        }

        Arrays.sort(dataArray);

        minval = dataArray[0]; //sets minval to the first position to the array

        value = 0.0;
        Scanner scanLines2 = new Scanner(readIn());
        scanLines2.useDelimiter(",");
        while(scanLines2.hasNext())
        {
            if(scanLines2.hasNextDouble())
            {
                value = Double.parseDouble(scanLines2.next());
                if(value == minval && scanLines2.hasNext())
                {
                    whereAndWhen = scanLines2.nextLine();
                    scanLines2.nextLine();
                    break;
                }
                scanLines2.nextLine();
            }
        }

        return minval + " " + whereAndWhen; //returns the minimum value and its place, date and time of origin
    }
}