/*
 * Alex Howard
 * CS 1050-002
 * 
 * CASH REGISTER
 * 
 * This program simulates a cash register for the Metro State Roadrunner store.
 * An employee enters the name of each item, amount of each item, and the cost
 * of each item.
 * 
 * The program then calculates the total cost, the total cost plus tax, and asks
 * if the customer gets an employee discount. If the customer is an employee, they
 * get 15% off the total of the purchase.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    static ArrayList<Item> order = new ArrayList<Item>(); //ArrayList of the order
    
    public static void main(String[] args)
    {
        final double tax = 0.082; //Tax
        final double empDisc = 0.015; //Employee Discount
        Main m = new Main();
        System.out.println("WELCOME TO THE ROADRUNNER STORE CASH REGISTER!\n");
        
        //Builds the Order
        m.buildOrder();
        
        /*order.add(new Item("orange",2,1.30));
        order.add(new Item("bread",1,2.50));
        order.add(new Item("banana",3,0.75));      //TESTING CODE. EXAMPLE.
        order.add(new Item("pencil",6,0.12));
        order.add(new Item("harpoon",1,6258.14));*/
        
        //Prints the Order
        System.out.println("Order is:\n");
        m.printOrder();
        
        //Calculates the Total
        double orderTotal = m.computeCost();
        System.out.printf("\nTotal is: $%.2f",orderTotal);
        
        //Total with Tax
        orderTotal = orderTotal + (orderTotal*tax);
        System.out.printf("\nTotal with tax (8.2) is: $%.2f",orderTotal);
        
        //Employee Discount
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\nIs the customer a Roadrunner Store employee?(Y/N)");
        String response = scan.nextLine();
        if(response.equals("y") || response.equals("Y"))
        {
            orderTotal = orderTotal - (orderTotal*empDisc);
            System.out.printf("Welcome employee! Total is now: $%.2f",orderTotal);
            System.out.println("\nHave a nice day!");
        }
        else
        {
            System.out.printf("\nTotal is still: $%.2f",orderTotal);
            System.out.println("\nHave a nice day!");
        }
        
        
    }
    
    //Method that builds an ArrayList of the customer's Items
    public ArrayList<Item> buildOrder()
    {

        Scanner scanInput = new Scanner(System.in); //Gets user input
        boolean exit = false; //Error check

        System.out.println("Hello! Welcome to the Metro State Roadrunner Store Register Program.\n");
        do{
            try
            {
                System.out.println("\nPlease enter the item name (Enter 'quit' to exit): ");
                String name = scanInput.nextLine(); //Gets the item name
                if(name.equals("quit")) //Exit the program
                {
                    exit = true;
                    break;
                }
                System.out.println("Please enter how many " + name + "s are being purchased: ");
                int amount = scanInput.nextInt(); //Gets the item amount
                System.out.println("Please enter the cost of a(n)" + name + ": ");
                double cost = scanInput.nextDouble(); //Gets the item cost

                order.add(new Item(name,amount,cost));
                scanInput.nextLine(); //Clears scanner for next input
            }
            catch(Exception e) //Catch for InputMismatchException
            {
                System.out.println("Error! Input invalid! Do you want to try again?(Y/N)");
                Scanner scan = new Scanner(System.in);
                String s = scan.nextLine();
                if(s.equals("y") || s.equals("Y"))
                {
                    exit = false;
                    scanInput.nextLine(); //Clears scanner for next input
                }
                else 
                    exit = true;
            }
        }while(exit == false);

        return order; //Returns the ArrayList of built Items
    }
    
    //Method that prints out the customer's order
    public void printOrder()
    {
        for(int i = 0; i < order.size(); i++)
        {
            System.out.println(order.get(i));
        }
    }
    
    //Method that computes the cost of the customer's order
    public double computeCost()
    {
        double total=0.0; //Total
        for(int b = 0; b < order.size(); b++)
        {
        int howMany = order.get(b).getItemAmount(); //Finds how many of that particular item
        double theCost = order.get(b).getItemCost(); //Finds the cost of that particular item
        total += theCost * howMany; //Calculates the total cost of each item
        } 
        return total; //Return the total
    }
}