/**
 * sevenSeg.v
 * 
 * Alex Howard
 * Program 5
 * 11-26-2013
 * EET 2310
 * 
 */
 
module sevenSeg(bcd,leds);
	input [3:0] bcd;
	output reg [1:7] leds;
	
	always @ (bcd)
		case(bcd)      //abcdefg     
			0: leds = 7'b1111110; //0
			1: leds = 7'b0110000; //1
			2: leds = 7'b1101101; //2
			3: leds = 7'b1111001; //3
			4: leds = 7'b0110011; //4
			5: leds = 7'b1011011; //5
			6: leds = 7'b1011111; //6
			7: leds = 7'b1110000; //7
			8: leds = 7'b1111111; //8
			9: leds = 7'b1111011; //9
			default: leds = 7'bx;
		endcase

endmodule