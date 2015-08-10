/*
 * Alex Howard
 * CS 150-002
 * 
 * This program is designed to help a user calculate their monthly bill.
 * 
 * A user enters the package they have purchased and the amount of hours of internet use
 * and the program calculates the total for the month.
 */

import java.util.*;
import javax.swing.*;

public class ISP2
{
    public static void main(String[] args)
    {
        String cont;
        
        //Prints a greeting to the user
        String greeting = "Welcome to ISPcalc.";
        JOptionPane.showMessageDialog(null, greeting);
        
        JFrame frame = new JFrame("ISPcalc");
        
        JRadioButton packA = new JRadioButton("Package A");
        JRadioButton packB = new JRadioButton("Package B");
        JRadioButton packC = new JRadioButton("Package C");

        do{
            //Initial varaibles
            double total;

            //Asks for the package and hours of the user
            String packageType = JOptionPane.showInputDialog("Please enter the package type you have(A, B, or C):");
            String hoursUsedPrompt = JOptionPane.showInputDialog("Now enter the amount of hours used:");
            int hoursUsed = Integer.parseInt(hoursUsedPrompt);

            //Checks which package is selected and calculates the total.
            if(packageType.toLowerCase().equals("a"));
            {
                if(hoursUsed <= 10)
                {
                    total = 9.95;
                }
                else
                {
                    total = 9.95 + (2.00 * (double)hoursUsed);
                }

            }

            if(packageType.toLowerCase().equals("b"))
            {
                if(hoursUsed <= 20)
                {
                    total = 13.95;
                }
                else
                {
                    total = 13.95 + (1.00*hoursUsed);
                }
            }

            if(packageType.toLowerCase().equals("c"))
            {
                total = 19.95;
            }

            //Prints out the total
            String totalMessage = "Your total this month is $" + total;
            JOptionPane.showMessageDialog(null,totalMessage);
            
            cont = JOptionPane.showInputDialog("Do another calculation? Y/N");
        
        }while(cont.toLowerCase().equals("y"));
    }
}