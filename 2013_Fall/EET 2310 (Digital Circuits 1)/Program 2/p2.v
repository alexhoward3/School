/*
 * Alex Howard
 * 9-24-2013
 * EET 2310-001
 * Program 2
 * alex_howard_p2.v
 */

/**
 * Module: top
 * 
 * f1 = x1*x3' + x2*x3' + x3'*x4' + x1*x2 + x1*x4'
 * f2 = (x1+x3') * (x1+x2+x4') * (x2+x3'+x4')
 */
 
module top(x1, x2, x3, x4, f1, f2);
	input x1, x2 , x3, x4;
	output f1, f2;
	
	/*
	//Function 1
	and(a, x1, ~x3);
	and(b, x2, ~x3);
	and(c, ~x3, ~x4);
	and(d, x1, x2);
	and(e, x1, ~x4);
	
	or(f1, a, b, c, d, e);
	
	//Function 2
	or(f, x1, ~x3);
	or(g, x1, x2, x4);
	or(h, x2, x3, x4);
	
	and(f2, f, g, h);
	*/
	
	assign f1 = (x1 & ~x3) | (x2 & ~x3) | (~x3 & ~x4) | (x1 & ~x4);
	assign f2 = (x1 | ~x3) & (x1 | x2 | x4) & (x1 | ~x3 | ~x4);

endmodule