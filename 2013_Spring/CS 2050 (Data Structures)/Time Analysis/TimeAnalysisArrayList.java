/* Alex Howard
 * CS 2050-003
 * 
 * This program builds a linked list and times operations on an ArrayList
 */

import java.util.ListIterator;
import java.util.Random;
import java.util.ArrayList;

public class TimeAnalysisArrayList
{
	public static final int buildNum = 100000; //Holds the length of the list

	public static void main(String[] args)
	{
		for(int i = 0; i <= 10; i++)
		{
			System.out.printf("%d: ArrayList Timing: (in seconds) [data size: %d]\n\n", i, buildNum);
			ArrayList<Integer> list = new ArrayList<Integer>(); //The list
			long begin = System.nanoTime(); //Checks the starting time

			//Get High to Low
			list = build(); //Calls the build method which puts values into the list
			long start = System.nanoTime(); //Checks the start time
			highLowGet(list); //Calls the method to get each element in the list from high to low
			long stop = System.nanoTime(); //Checks the ending time
			long elapsedTime = stop - start; //Gets the time elapsed
			double seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Get High to Low: %.3f \n", seconds); //Prints the time

			//Get Low to High
			list = build(); //Refills the list
			start = System.nanoTime(); //Checks the start time
			lowHighGet(list); //Calls the method to get each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Get Low to High: %.3f \n", seconds); //Prints the time

			//Remove High to Low
			list = build(); //Refills the list
			start = System.nanoTime(); //Checks the start time
			highLowRemove(list); //Calls the method to get each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Remove High to Low: %.3f \n", seconds); //Prints the time

			//Remove Low to High
			list = build(); //Refills the list
			start = System.nanoTime(); //Checks the start time
			lowHighRemove(list); //Calls the method to get each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Remove Low to High: %.3f \n", seconds); //Prints the time

			//Get High to Low Iterator
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			iterateHighLowGet(list); //Calls the method to remove each element in the list from high to low
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Get High to Low Iterate: %.3f \n", seconds); //Prints the time

			//Get Low to High Iterator
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			iterateLowHighGet(list); //Calls the method to remove each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Get Low to High Iterate: %.3f \n", seconds); //Prints the time

			//Remove High to Low Iterator
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			iterateHighLowRemove(list); //Calls the method to remove each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Remove High to Low Iterate: %.3f \n", seconds); //Prints the time

			//Remove Low to High Iterator
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			iterateLowHighRemove(list); //Calls the method to remove each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Remove Low to High Iterate: %.3f \n", seconds); //Prints the time

			//Add at Front
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			addAtFront(list); //Calls the method to remove each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Add at Front: %.3f \n", seconds); //Prints the time

			//Add at End
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			addAtEnd(list); //Calls the method to remove each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Add at End: %.3f \n", seconds); //Prints the time

			//Add at Random
			list = build(); //Rebuilds the list
			start = System.nanoTime(); //Checks the start time
			addAtRandom(list); //Calls the method to remove each element in the list from low to high
			stop = System.nanoTime(); //Checks the ending time
			elapsedTime = stop - start; //Gets the time elapsed
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("Add at Random: %.3f \n", seconds); //Prints the time

			System.out.println(); //Spacer
			long end = System.nanoTime(); //Checks the ending time
			elapsedTime = end - begin; //Gets the elapsed time
			seconds = (double)elapsedTime / 1000000000.0; //Converts the time from nanoseconds to seconds
			System.out.printf("DONE! Total time: %.3f \n\n", seconds); //Prints the total time of the operations
		}
	} //End main

	public static ArrayList<Integer> build()
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int i = 0; i < buildNum; i++)
		{
			a.add(new Random().nextInt(2)); //Randomly adds either 1 or 0 to the list
		}
		return a;
	} //End build

	public static void highLowGet(ArrayList<Integer> a)
	{
		for(int i = buildNum-1; i > 0; i--)
		{
			@SuppressWarnings("unused")
			int value = a.get(i); //Gets each element of the list from the highest position to the lowest
		}
	} //End getHighLow

	public static void lowHighGet(ArrayList<Integer> a)
	{
		for(int i = 0; i < buildNum; i++)
		{
			@SuppressWarnings("unused")
			int value = a.get(i); //Gets each element of the list from the lowest position to the highest
		}
	} //End getLowHigh

	public static void highLowRemove(ArrayList<Integer> a)
	{
		for(int i = buildNum-1; i >= 0; i--)
		{
			a.remove(i);
		}
	} //End highLowRemove

	public static void lowHighRemove(ArrayList<Integer> a)
	{
		for(int i = 0; i < buildNum; i++)
		{
			a.remove(0);
		}
	} //End lowHighRemove

	public static void addAtFront(ArrayList<Integer> a)
	{
		for(int i = 0; i <= buildNum; i++)
		{
			a.add(0, new Random().nextInt(2));
		}
	} //End addAtFront

	public static void addAtEnd(ArrayList<Integer> a)
	{
		for(int i = 0; i <= buildNum; i++)
		{
			a.add(new Random().nextInt(2));
		}
	} //End addAtEnd

	public static void addAtRandom(ArrayList<Integer> a)
	{
		for(int i = 0; i <= buildNum; i++)
		{
			int insertionPoint = new Random().nextInt(a.size());
			a.add(insertionPoint, new Random().nextInt(2));
		}
	} //End addRandom

	public static void iterateHighLowGet(ArrayList<Integer> a)
	{
		for(ListIterator<Integer> i = a.listIterator(a.size()); i.hasPrevious(); i.previous())
		{
			@SuppressWarnings("unused")
			int value = a.get(i.previousIndex());
		}
	} //End iterateHighLowGet

	public static void iterateLowHighGet(ArrayList<Integer> a)
	{
		for(ListIterator<Integer> i = a.listIterator(0); i.hasNext(); i.next())
		{
			@SuppressWarnings("unused")
			int value = a.get(i.nextIndex());
		}
	} //End iterateLowHighGet

	public static void iterateHighLowRemove(ArrayList<Integer> a)
	{
		for(ListIterator<Integer> i = a.listIterator(a.size()); i.hasPrevious();)
		{
			@SuppressWarnings("unused")
			int r = i.previous();
			i.remove();
		}
	} //End iterateHighLowRemove

	public static void iterateLowHighRemove(ArrayList<Integer> a)
	{
		for(ListIterator<Integer> i = a.listIterator(0); i.hasNext();)
		{
			@SuppressWarnings("unused")
			int r = i.next();
			i.remove();
		}
	} //End iterateLowHighRemove

}//End TimeAnalysisArrayList