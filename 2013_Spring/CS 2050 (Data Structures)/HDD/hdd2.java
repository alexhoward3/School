/*
 * Alex Howard
 * CS-2050-003
 * 
 * Program 1: NOAA HDD values
 * 
 * This program reads the NOAA data for early 2013 and totals the values for the HDD (heating degree days)
 * column and finds and prints the maximum and minimum values of HDD along with their corresponding cities.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class hdd2 {
	public static void main(String[] args) {
		File file = new File("src/data.txt"); //Gets the data file
		try {
			Scanner scanFile = new Scanner(file); //Opens a Scanner object on the file
			scanFile.useDelimiter("STATION:"); //Sets the scanner's delimiter to STATION:
			String tempCity = ""; //The current city in the file
			String highCity = ""; //Stores the city with the highest HDD
			String lowCity = ""; //Stores the city with the lowest HDD
			String num = ""; //Temporarily stores the current HDD value
			int total = 0; //Stores the total HDD value
			int max = Integer.MIN_VALUE; //Maximum value integer for finding max HDD value
			int min = Integer.MAX_VALUE; //Minimum value integer for finding min HDD value

			while (scanFile.hasNext()) {
				tempCity = scanFile.nextLine(); //Gets the first line (the city name)
				System.out.println(tempCity); //Prints the city name
				scanFile.reset(); //Resets the scanner's delimiter to whitespace

				for (int x = 0; x < 7; x++) {
					scanFile.nextLine(); //Skips the next 7 lines (to the number data)
				}

				for (int y = 0; y < 16; y++) {
					for (int x = 0; x < 6; x++) {
						num = scanFile.next(); //Grabs each HDD data number
					}
					total += Integer.parseInt(num); //Adds each HDD number to a sum
					scanFile.nextLine(); //Gets the next line of the file
				}

				if (total >= max) { //Finds the max HDD value and its corresponding city
					max = total;
					highCity = tempCity;
				}
				else if (total <= min) { //Finds the min HDD value and its corresponding city
					lowCity = tempCity;
					min = total;
				}

				System.out.println("HDD TOTAL: " + total + "\n"); //Prints the total for the current city
				total = 0; //Resets the total for the next city's total data block
				scanFile.useDelimiter("STATION:"); //Sets the delimiter back to the value STATION:
				scanFile.next(); //Skips the scanner to the next portion of data
			} //Closes while

			System.out.println("\nMAX: " + highCity + " " + max); //Prints the max HDD value and its city
			System.out.println("MIN: " + lowCity + " " + min); //Prints the min HDD value and its city

			scanFile.close(); //Closes the scanner

		} catch (IOException e) { //Error handling for the Scanner
			if (!(file.exists()))
				System.out.println("ERROR: FILE DOES NOT EXIST!"); //Prints if the file does not exist
			else
				System.out.println(e); //If some other IOException is encountered, the stack trace is printed
		}
	} //Closes main
} //Closes hdd2