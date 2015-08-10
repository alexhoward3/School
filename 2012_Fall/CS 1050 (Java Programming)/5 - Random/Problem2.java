/*
 * This program simulates two booklets of 40 matches a pipe smoker carries.
 * If he randomly picks one book to start with and alternates books each
 * time he needs a match, the program calculates the average number times
 * he can pull matches before one booklet is empty.
 */
import java.util.Random;

public class Problem2
{
    public static void main(String[] args)
    {
        Random rand = new Random(); //Class to generate random numbers
        int book1 = 40, book2 = 40, min=1, max=2, randomNum, ct=0, count=0, total=0;
        double average=0.0;
        for(ct = 1; ct <= 100; ct++)
        {
            for(count = 1, book1 = 40, book2 = 40; book1 != 0 && book2 != 0; count++)
            {
                randomNum = rand.nextInt(max - min + 1) + min; //Generate random number. Either 1 or 2
                if(randomNum == 1)
                {
                    book1--;
                    if(book1 == 0)
                    {
                        break; //Error checking code (IndexOutOfBoundsException)
                    }
                }
                else if(randomNum == 2)
                {
                    book2--;
                    if(book2 == 0)
                    {
                        break; //Error checking code (IndexOutOfBoundsException)
                    }
                }
            }
            total += count;
        }
        average = total/ct; //Calculates the average of number of times he can pull matches
        System.out.println("The average amount of matchsticks taken before he runs out: " + average);
    }
}