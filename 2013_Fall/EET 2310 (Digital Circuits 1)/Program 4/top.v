/*
 * top.v
 * 
 * Alex Howard
 * 11-8-2013
 * EET 2310
 * Program 4
 * 
 */
 
`ifdef WIN
	`include ".\dec3to8.v"
`else
	`include "./dec3to8.v"
`endif
 
module top();

	reg [2:0] stimIn;
	reg stimEn;
	wire stimOut;
	
	dec3to8 DEC
	(
		.in(stimIn),
		.en(stimEn),
		.out(stimOut)
	);
	
	//Monitor
	initial
		$monitor($time, "stimIn = %b, stimEn = % b, stimOut = %b\n\n", stimIn, stimEn, stimOut);

	//VCD Wave output
	initial
	begin
		$dumpfile("dec.vcd");
		$dumpvars;
	end
	
	initial
	begin
		//Enable = 1
		stimIn = 3'b000;
		stimEn = 1'b1;
		
		stimIn = 3'b010;
		stimEn = 1'b1;
		
		stimIn = 3'b101;
		stimEn = 1'b1;
		
		stimIn = 3'b111;
		stimEn = 1'b1;
		
		
		//Enable = 0
		stimIn = 3'b000;
		stimEn = 1'b0;
		
		stimIn = 3'b010;
		stimEn = 1'b0;
		
		stimIn = 3'b101;
		stimEn = 1'b0;
		
		stimIn = 3'b111;
		stimEn = 1'b0;
	end
endmodule