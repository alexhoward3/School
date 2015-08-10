/*
 * 	A Verilog implementation of a canonical SOP function
 * 	f(x1,x2,x3) = SUM m(0,2,4,5,6)
 * 	
 *	sop.v
 *
 *	Alex Howard
 *	11-8-2013
 *	EET 2310
 *	Program 4
 *	
 */

 
module sop(

	//Input
		in,
	//Output
		out
);

	input [2:0] in;
	output reg out;
	
	always@ (in)
	begin
		case(in)
			0: out = 1;
			1: out = 0;
			2: out = 1;
			3: out = 0;
			4: out = 1;
			5: out = 1;
			6: out = 1;
			7: out = 0;
		endcase
	end

endmodule