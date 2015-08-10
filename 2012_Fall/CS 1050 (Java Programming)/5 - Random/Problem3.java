/*
 * This program simulates 600 rolls of a die and attempts to count the
 * number of 1's,2's,3's,4's,5's,and 6's generated.
 * 
 * The program also tries to determine how many times a run of each
 * number has occured and how many times a run of all numbers have
 * occured numerically and consecutively.
 */
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Problem3
{

    public static void main(String[] args)
    {
        Scanner scan, scan2;
        for(int i = 1; i <= 4; i++)
        {
            ArrayList<Integer> generatedArray = sim(); //Copies new array from the simulation method 
            scan = new Scanner(countNums(generatedArray)); //Scans the array to count the numbers
            scan.useDelimiter(","); //Format for countNums() return
            System.out.println("\nSimulation " + i);
            System.out.println("Number of 1's: " + scan.next());
            System.out.println("Number of 2's: " + scan.next());
            System.out.println("Number of 3's: " + scan.next());
            System.out.println("Number of 4's: " + scan.next());
            System.out.println("Number of 5's: " + scan.next());
            System.out.println("Number of 6's: " + scan.next());
            System.out.println();

            scan2 = new Scanner(countRuns(generatedArray)); //Scans the array to count the runs
            scan2.useDelimiter(","); //Format for countRuns() return
            System.out.println("\nNumber of 1's runs: " + scan2.next());
            System.out.println("Number of 2's runs: " + scan2.next());
            System.out.println("Number of 3's runs: " + scan2.next());
            System.out.println("Number of 4's runs: " + scan2.next());
            System.out.println("Number of 5's runs: " + scan2.next());
            System.out.println("Number of 6's runs: " + scan2.next());
            System.out.println("Number of full runs: " + scan2.next());
            System.out.println("Number of consecutive runs: " + scan2.next());
            System.out.println("\n");

        }

    }

    public static ArrayList<Integer> sim() //Simulates 600 random numbers and stores them into an array
    {
        ArrayList<Integer> simulation = new ArrayList<Integer>();
        Random rand = new Random();
        int max=6, min=1;
        int randomNum;

        for(int i = 1; i <= 600; i++)
        {
            randomNum = rand.nextInt(max - min + 1) + min;
            simulation.add(randomNum);
        }

        return simulation;
    }

    public static String countNums(ArrayList<Integer> al) //Counts each number generated
    {
        int one=0, two=0, three=0, four=0, five=0, six=0;
        for(int i = 0; i < al.size(); i++)
        {

            if(al.get(i) == 1)
                one++;
            else if(al.get(i) == 2)
                two++;
            else if(al.get(i) == 3)
                three++;
            else if(al.get(i) == 4)
                four++;
            else if(al.get(i) == 5)
                five++;
            else
                six++;
        }

        return one + "," + two + "," + three + "," + four + "," + five + "," + six;
    }

    public static String countRuns(ArrayList<Integer> al) //Count the number of runs generated
    {
        int onerun=0,tworun=0,threerun=0,fourrun=0,fiverun=0,sixrun=0,fullrun=0,consecutiverun=0;
        int a = 0, b = 1;
        //Loops through the length of the array
        for(a = 0; a < al.size()-2 && b < al.size()-1; a++,b++)
        {
            //Finds the run of each number
            if(al.get(a) == 1 && al.get(b) == 1)
            {
                onerun++;
            }
            else if(al.get(a) == 2 && al.get(b) == 2)
            {
                tworun++;
            }
            else if(al.get(a) == 3 && al.get(b) == 3)
            {
                threerun++;
            }
            else if(al.get(a) == 4 && al.get(b) == 4)
            {
                fourrun++;
            }
            else if(al.get(a) == 5 && al.get(b) == 5)
            {
                fiverun++;
            }
            else if(al.get(a) == 6 && al.get(b) == 6)
            {
                sixrun++;
            }
            
            //Finds a run of 1-6
            if(al.get(a) == 1)
            {
                if(al.get(a+1) == 2)
                {
                    if(al.get(a+2) == 3)
                    {
                        if(al.get(a+3) == 4)
                        {
                            if(al.get(a+4) == 5)
                            {
                                if(al.get(a+5) == 6)
                                {
                                    fullrun++;
                                }
                            }
                        }
                    }
                }
            }
            //Finds the number of consecutive runs
            if(al.get(a) == al.get(b))
            {
                consecutiverun++;
            }
        }
        return onerun + "," + tworun + "," + threerun + "," + fourrun + "," + fiverun + "," + sixrun + "," + fullrun + "," + consecutiverun;
    }
}