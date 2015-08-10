/*
 * NOT COMPLETE
 * WILL ASK FOR HELP!!!
 */

import java.util.*;
import java.lang.reflect.Array;
import java.sql.*;

public class Problem5
{
    public static void main(String[] args)
    {
        int paintAmount=0; //Total cups of paint splashed on the students
        Random rand = new Random(); //To create random numbers for row hits and column hits
        int row=0, col=0; //Initial row and column variables for loops
        ArrayList<Seat> seats = new ArrayList<Seat>(); //Makes and array list for Seat objects
        //ArrayList<Integer> randRow = new ArrayList<Integer>(); //Stores random row integers
        //ArrayList<Integer> randCol = new ArrayList<Integer>(); //Stores random column integers
        
        int[] randRow = new int[22]; //Stores random row integers
        int[] randCol = new int[22]; //Stores random column integers

        //for loop to generate seats
        for(row = 0; row < 15; row++)
        {
            for(col = 0; col < 10; col++)
            {
                seats.add(new Seat(row,col));
            }
        }

        for(int i = 0; i < 22; i++)
        {
            randRow[i] = rand.nextInt(15 - 1 + 1) + 1;
            randCol[i] = rand.nextInt(10 - 1 + 1) + 1;
        }
        
        for(int i = 0; i < randRow.length && i < randCol.length; i++)
        {
            for(int k = 0; k < seats.size(); k++)
            {
                if(seats.get(k).equals((new Seat(Array.getInt(randRow,i),(Array.getInt(randCol,i))))))
                {
                    seats.get(k).setPainted(true);
                    seats.get(k).setPaintAmount(2);
                    seats.get(k).getPaintAmount();
                    System.out.println("TEST");
                }
                else
                {
                }
            }
        }
    }
}