/**
 * top.v
 * 
 * Alex Howard
 * Program 5
 * 11-26-2013
 * EET 2310
 * 
 */
 
 `ifdef WIN
	`include ".\sevenSeg.v"
`else
	`include "./sevenSeg.v"
`endif

module top(

		//inputs
		HEX0,
		HEX1,
		HEX2,
		HEX3,
		CLOCK_50,
		//outputs
		lights
		
);

	input [15:0] digits;
	input [3:0]HEX0;
	input [7:4]HEX1;
	input [11:8]HEX2;
	input [15:12]HEX3;
	input CLOCK_50;
	output[1:28] lights;
	
	sevenSeg ss1 (HEX0, lights[1:7]);
	sevenSeg ss2 (HEX1, lights[8:14]);
	sevenSeg ss3 (HEX2, lights[15:21]);
	sevenSeg ss4 (HEX3, lights[22:28]);
	
endmodule