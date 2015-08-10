/**
 * Module: full_adder
 * 
 * Alex Howard
 * EET 2310-001
 * 10-19-2013
 *full_adder.v
 * 
 */


`ifndef _full_adder_v_
`define _full_adder_v_

`define N 4

`define M (`N << 2)

`endif

module full_adder(Cin, x, y, s, Cout);
	
	input Cin, x, y;
	output s, Cout;
	
	assign s = x ^ y ^ Cin;
	assign Cout = (x & y) | (x & Cin) | (y & Cin);

endmodule