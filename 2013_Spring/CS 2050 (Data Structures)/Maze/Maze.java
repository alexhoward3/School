/* Alex Howard
 * CS 2050-003
 * 
 * This program prompts a user to open a specified maze file and attempts to traverse
 * through the maze recursively to find the goal.
 */
import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Maze
{
	static String[][] maze = new String[9][20]; //Builds the array to house the maze
	static int height = maze.length; //The maze's height
	static int width = maze[0].length; //The maze's width
	static String goal = "G"; //The end of the maze
	static String wall = "*"; //A wall of the maze
	static String empty = "-"; //An "empty" space in the maze
	static int startX = 1; //The starting X position
	static int startY = 1; //The starting Y position

	public static void main(String[] args)
	{
		read(); //Reads in the maze
		start(startX,startY); //Gives the starting position an "s"
		print(); //Prints out the maze
		findG(startX,startY); //Finds the goal starting at the X and Y positions given
	}

	public static void read()
	{
		String filename = ""; //A String to hold the name of the selected maze file
		JFileChooser openfile = new JFileChooser(); //Open dialog
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze File", "maze"); //Filter for maze files
		openfile.setFileFilter(filter);
		int returnVal = openfile.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			filename = openfile.getSelectedFile().toString(); //On the approve option, save the path to filename
			System.out.println("File: " + filename); //Prints out the file's path
		}
		else
		{
			System.exit(0); //If any action is taken besides opening the file, exit
		}

		File file = new File(filename); //Creates the file
		final Scanner scan; //A scanner to scan the file
		try
		{
			scan = new Scanner(file, "UNICODE"); //Creates the scanner and scans in the file in Unicode format
			while(scan.hasNext())
			{
				for(int row = 0; row < height; row++)
				{
					for(int col = 0; col < width; col++)
					{
						maze[row][col] = scan.next(); //Traverses the file and puts each character into the maze array
					}
				}
			}
			scan.close(); //Closes the scanner
		}
		catch(Exception e)
		{
			System.out.println("Error \n\n " + e); //Error handling, prints the stack trace

			//The rest of this is so the program can be exited when running in a terminal
			Scanner scanIn = new Scanner(System.in);
			System.out.println("\n\nType anything and press enter to exit.");
			@SuppressWarnings("unused")
			String in = scanIn.next();
			scanIn.close();
			System.exit(0);
		}
	}

	public static void start(int x, int y)
	{
		maze[x][y] = "s"; //Sets the starting position to "s"
	}

	public static void mark(int x, int y)
	{
		maze[x][y] = "+"; //Marks a traversed position to "+"
	}

	public static void unmark(int x,int y)
	{
		maze[x][y] = "x"; //Marks a dead end path with "x"
	}

	public static void end()
	{
		start(startX,startY); //Resets the starting position to "s" in case it has been overwritten
		System.out.println("DONE!"); //End message
		print(); //Prints the maze with the solved path

		//The rest of this is so the program can be exited when running in a terminal
		Scanner scanIn = new Scanner(System.in);
		System.out.println("Type anything and press enter to exit.");
		@SuppressWarnings("unused")
		String in = scanIn.next();
		scanIn.close();
		System.exit(0);
	}

	public static void findG(int x, int y)
	{
		if(maze[x][y].equals("*"))
		{
			System.out.println("INVALID STARTING POSITION"); //In case the starting position is inside a wall
		}

		//Looks for an empty place or the goal to the North of the position
		if(maze[x-1][y].equals(empty) || maze[x-1][y].equals(goal))
		{
			if(maze[x-1][y].equals(goal))//If it is the goal
			{
				maze[x-1][y] = "F"; //Marks the goal with "F" to signify it has been found
				end(); //Runs the ending method
			}
			
			mark(x-1,y); //Otherwise mark that position
			findG(x-1,y); //Move to that next position
		}

		//Looks for an empty place or the goal to the East of the position
		if(maze[x][y+1].equals(empty) || maze[x][y+1].equals(goal))
		{
			if(maze[x][y+1].equals(goal))//If it is the goal
			{
				maze[x][y+1] = "F"; //Marks the goal with "F" to signify it has been found
				end(); //Runs the ending method
			}
			
			mark(x,y+1); //Otherwise mark that position
			findG(x, y+1); //Move to that next position
		}

		//Looks for an empty place or the goal to the South of the position
		if(maze[x+1][y].equals(empty) || maze[x+1][y].equals(goal))
		{
			if(maze[x+1][y].equals(goal))//If it is the goal
			{
				maze[x+1][y] = "F"; //Marks the goal with "F" to signify it has been found
				end(); //Runs the ending method
			}
			
			mark(x+1,y); //Otherwise mark that position
			findG(x+1,y); //Move to that next position
		}

		//Looks for an empty place or the goal to the West of the position
		if(maze[x][y-1].equals(empty) || maze[x][y-1].equals(goal))
		{
			if(maze[x][y-1].equals(goal))//If it is the goal
			{
				maze[x][y-1] = "F"; //Marks the goal with "F" to signify it has been found
				end(); //Runs the ending method
			}
			
			mark(x,y-1); //Otherwise mark that position
			findG(x,y-1); //Move to that next position
		}

		unmark(x,y); //If no conditions are met, unmark the position and recurse backwards
	}

	public static void print() //Prints out the maze
	{
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				System.out.print(maze[row][col]); //Prints each element in the maze array
			}
			System.out.println("\n");
		}
		System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$\n"); //Separation line for cleanliness and aesthetics
	}
}