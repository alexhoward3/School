public class Factorial {
	public static void main(String[] args)
	{
		long start = System.nanoTime();
		factRec(1000);
		long end = System.nanoTime();
		long elapsed = end - start;
		System.out.println(elapsed);
		
		long start2 = System.nanoTime();
		factIt(1000000000);
		long end2 = System.nanoTime();
		long elapsed2 = end2 - start2;
		System.out.println(elapsed2);
	}
	
	public static int factIt(int i)
	{
		int res = 1;
		if(i <= 1)
			return 1;
		while(i > 1)
		{
			res *= i;
			i--;
		}
		return res;
	}
	
	public static int factRec(int i)
	{
		if(i <=1 ) return 1;
		else return i * factRec(i-1);
	}
}
