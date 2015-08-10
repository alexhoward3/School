/**
 * Module: top
 * 
 * Alex Howard
 * EET 2310-001
 * 10-19-2013
 * top.v
 * 
 */

//`ifdef WIN
`include "full_adder.v"
//`endif

module top(CarryIn, X, Y, F, CarryOut);
	
	input CarryIn;
	input [7:0] X, Y;
	output [8:0] F;
	output CarryOut;
	wire [3:1] C;
	
	full_adder add0 (CarryIn, X, Y, F, CarryOut);

endmodule