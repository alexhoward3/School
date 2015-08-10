public class Item
{
    private String name = "";
    private int amount = 0;
    private double cost = 0.0;
    
    public Item() //Default constructor
    {
    }
    
    public Item(String s, int i, double d) //Constructor to build Items
    {
        name = s;
        amount = i;
        cost = d;
    }
    
    public String getItemName() //Gets the Item's name
    {
        return name;
    }
    
    public int getItemAmount() //Gets the Item's amount
    {
        return amount;
    }
    
    public double getItemCost() //Gets the Item's cost
    {
        return cost;
    }
    
    public String toString() //Returns the Items
    {
        return amount + " " + name + "(s) for $" + cost + " a piece.";
    }
    
}