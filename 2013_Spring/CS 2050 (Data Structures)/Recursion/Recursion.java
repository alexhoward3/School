/* Alex Howard
 * CS 2050-003
 * 
 * This program has three methods that are all computed recursively.
 * The first method finds the sum series up to k number.
 * The second program finds if a String is a palindrome or not.
 * The third finds the greatest common divisor of two numbers using Euclid's Algorithm.
 */
public class Recursion
{	
	public static void main(String[] args)
	{
		try
		{
			//Test cases
			
			//////////SUM////////////////
			System.out.println("Sum series of 5: " + sum(5));
			System.out.println("Sum series of 120: " + sum(120));
			System.out.println("Sum series of 12: " + sum(12));
			System.out.println("Sum series of 7: " + sum(7));
			
			System.out.println("\n\n");
			/////////PALINDROME/////////////
			String pd = "On a clover, if alive erupts a vast pure evil, a fire volcano.";
			System.out.println("\"" + pd + "\"" + " is a palindrome?: " + palindrome(pd,0));
			System.out.println("\"A Toyota\" is a palindrome?: " + palindrome("A Toyota",0));
			System.out.println("\"Blargh!\" is a palindrome?: " + palindrome("Blargh!",0));
			System.out.println("\"ABCDEDCBA\" is a palindrome?: " + palindrome("ABCDEDCBA",0));
			System.out.println("\"ABCDDCBA\" is a palindrome?: " + palindrome("ABCDDCBA",0));
			System.out.println("\"ABCDECBA\" is a palindrome?: " + palindrome("ABCDECBA",0));
			System.out.println("\"AB\" is a palindrome?: " + palindrome("AB",0));
			System.out.println("\"A\" is a palindrome?: " + palindrome("A",0));
			
			
			System.out.println("\n\n");
			/////////GCD////////////////
			System.out.println("Greatest Common Divisor of 27 and 9: " + GCD(27,9));
			System.out.println("Greatest Common Divisor of 8 and 12: " + GCD(8,12));
			System.out.println("Greatest Common Divisor of 256 and 8: " + GCD(256,16));
			System.out.println("Greatest Common Divisor of 6 and 42: " + GCD(7,42));
			System.out.println("Greatest Common Divisor of 48 and 18: " + GCD(48,18));
		}
		catch(StackOverflowError e) //Error handling for stack overflows.
		{
			System.out.println("STACK OVERFLOW!");
		}
		catch(Exception e) //Misc error handling (NumberFormatException, etc.)
		{
			System.out.println(e);
		}
	} //End main

	public static double sum(int k) //Recursively computes the sum series of a number
	{
		if(k == 1) return 1; //If the number 'k' is equal to 1, return 1;
		if(k%2 == 0) return sum(k-1)+(1.0/k); //If k is even: (k - 1) + (1/k)
		return sum(k-1)-(1.0/k); //Otherwise k will be odd: (k - 1) - (1/k)
	} //End sum

	public static boolean palindrome(String s, int place) //Checks a String if it is a palindrome
	{
		s = s.replaceAll("\\W", ""); //Replaces all whitespace or non charaters to an empty character
		s = s.toUpperCase(); //Converts the string to upper case
		if(place >= s.length()-place-1) return true; //If the place is at or beyond halfway in the string return true
		//If the character at the position 'place' is equal to the last position-place-1, check the next place
		if(s.charAt(place) == s.charAt(s.length()-place-1)) return palindrome(s,place+1);
		return false; //Otherwise, it is not a palindrome
	} //End palindrome

	public static int GCD(int n, int m) //Computes the greatest common divisor of two numbers
	{
		if(m == 0) return n; //m is equal to zero return n
		if(n == 0) return m; //n is equal to zero return m
		if(m > n) return GCD(n,m%n); //If m is greater than n, return n and the modulus of m%n
		return GCD(m,n%m); //Otherwise n is greater than m, return m and the modulus of n%m
	} //End GCD
} //End Recursion