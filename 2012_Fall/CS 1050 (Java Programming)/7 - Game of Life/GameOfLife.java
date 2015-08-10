/* Alex Howard
 * CS 1050-002
 * 
 * This is an implementation of John Conway's
 * famous Game of Life programming problem.
 * 
 * The program will run infinitely if an infinite shape
 * is created such as a square, blinker (3 of same row or col),
 * toad, pulsar, or beacon etc...
 * 
 * However, if the matrix has no values in it the program will terminate.
 */

import java.util.Random; //Used to generate random filled cells

public class GameOfLife
{
    static int rows = 30, cols = 30; //rows/columns
    static boolean[][] grid; //primary grid
    static boolean[][] nextgrid; //secondary grid
    static final String live = ("[O]"), dead = ("[ ]"); //cells

    public static void main(String[] args)
    {
        grid = new boolean[rows][cols]; //creates primary grid
        nextgrid = new boolean[rows][cols]; //creates secondary grid

        makeGrid();//Populates the primary grid
        toPrint(grid);//Prints the grid

        do{
            try{
                Thread.sleep(300L);//Pauses the screen 
                for(int r = 0; r < rows; r++)//Through the rows
                {
                    for(int c = 0; c < cols; c++)//Through the columns
                    {
                        setNextState(r,c);//Finds the next state for the secondary grid
                    }
                }
                clearScreen();//Clears the screen of the current grid
                updateGrid();//Updates the initial grid to the value of the secondary grid
                toPrint(nextgrid);//Prints the secondary grid

            }catch(Exception e)
            {
                System.out.println("ERROR! YOU BROKES IT!");//Error for you breaksing it...
            }
        }while(!isEmpty());//While the matrix is still full, continue updating/printing
    }

    //Makes an initial population of the primary grid
    public static void makeGrid()
    {
        for(int r = 0; r < rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                Random rand = new Random();
                int randNum = rand.nextInt(20);//Generates a random number

                if(randNum == 19 || randNum == 0 || randNum == 10)
                    grid[r][c] = true;//Random numbers 19, 0, 10 get true
                else
                    grid[r][c] = false;//Otherwise false
            }
        }
    }
    
    //Updates the primary grid to hold the secondary grid's values
    public static void updateGrid()
    {
        for(int r = 0; r < rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                grid[r][c] = nextgrid[r][c];//Puts secondary grid's values into the primary's
            }
        }
    }
    
    //Prints a boolean matrix
    public static void toPrint(boolean[][] b)
    {
        for(int r=0; r < rows; r++)
        {
            for(int c=0; c < cols; c++)
            {
                if(b[r][c])
                    System.out.print(live);//Converts true to Unicode "Black Square"
                else
                    System.out.print(dead);//Converts false to Unicode "White Square"
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Finds the state (dead/alive) for that cell
    public static boolean getState(int r, int c)
    {
        /*Gets the state of a current cell. If the
        cell is out of range it passes as false.
        This is a way to handle out of bounds exceptions*/
        if (r >= 0 && r < rows && c >= 0 && c < cols) {
            return grid[r][c];
        }
        return false;
    }
    
    //Checks all of the current neighbors of that cell
    public static int checkNeighbors(int r, int c)
    {
        int count = 0;

        if (getState(r-1,c)) count++; //Neighbor above
        if (getState(r+1,c)) count++; //Neighbor below
        if (getState(r,c-1)) count++; //Neighbor left
        if (getState(r,c+1)) count++; //Neighbor right
        if (getState(r-1,c-1)) count++; //Neighbor top left
        if (getState(r+1,c+1)) count++; //Neighbor bottom right
        if (getState(r-1,c+1)) count++; //Neighbor top right 
        if (getState(r+1,c-1)) count++; //Neighbor bottom left

        return count;

    }

    public static void setNextState(int r, int c)
    {
        if(checkNeighbors(r,c) == 3)
            nextgrid[r][c] = true; //Births a cell
        else if(checkNeighbors(r,c) == 2)
            nextgrid[r][c] = grid[r][c]; //Sets a cell to the same state it was
        else
            nextgrid[r][c] = false; //Kills a cell
    }

    //Checks if the grid is empty
    public static boolean isEmpty()
    {
        int count = 0;
        for(int r = 0; r < rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                if(grid[r][c]) return false;//If not isEmpty
            }
        }
        return true;//If isEmpty
    }

    //Clears the terminal
    public static void clearScreen()
    {
        System.out.print('\u000C'); //BlueJ command for clearing screen
    }
}