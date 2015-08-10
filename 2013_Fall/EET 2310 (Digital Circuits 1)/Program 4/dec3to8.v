/*	
 * 	A Verilog implementation of a 3 to 8 decoder
 * 	
 *	dec3to8.v
 *
 *	Alex Howard
 *	11-8-2013
 *	EET 2310
 *	Program 4
 *	
 */

module dec3to8
(
	// inputs
		in, en,
	// output
		out
);

	input [2:0]  in;
	input en;
	output reg [0:7] out;


	// 3 to 8 Decoder
	always@ (in, en)	
	begin
		if(en == 0)
			out = 8'b00000000;
		else
			case(in)	
   				0: out = 8'b10000000;
   				1: out = 8'b01000000;
   				2: out = 8'b00100000;
   				3: out = 8'b00010000;
   				4: out = 8'b00001000;
   				5: out = 8'b00000100;
   				6: out = 8'b00000010;
   				7: out = 8'b00000001;
		endcase
   
	end
endmodule