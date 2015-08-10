import java.util.*;
public class WordGame
{
	public static void main(String[] args)
	{
		Scanner key = new Scanner(System.in);
		
		String NAME, AGE, CITY, COLLEGE, PROF, ANIMAL, PET;
		
		System.out.println("Enter a name:");
		NAME = key.next();
		System.out.println("Enter an age:");
		AGE = key.next();
		System.out.println("Enter a city:");
		CITY = key.next();
		System.out.println("Enter a college:");
		COLLEGE = key.next();
		System.out.println("Enter a profession:");
		PROF = key.next();
		System.out.println("Enter an animal:");
		ANIMAL = key.next();
		System.out.println("Enter a pet's name:");
		PET = key.next();
		
		System.out.println("There once was a person named " + NAME + "  who lived in " + CITY + ".  At the age of " + AGE + ",  " + NAME + " went to college at " + COLLEGE + ". " + NAME + " graduated and went to work as a " + PROF + ".  Then, " + NAME + "  adopted a(n) " + ANIMAL + " named " + PET + " They both lived happily ever after!"); 
	}
}