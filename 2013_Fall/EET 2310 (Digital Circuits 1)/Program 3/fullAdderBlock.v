/*

	EET 2310
	fullAdderBlock.v
	Fall 2013
	Dave W. 

      
*/

`ifdef WIN

`include ".\fullAdder.v"		// Win syntax
		

`else

`include "./fullAdder.v"		// Linux syntax
	

`endif

module fullAdderBlock
(
    //input
	cin,
	dataA,
	dataB,
    // output
	sum_out,
	carry

);
parameter DATA_SIZE_PARAMETER = 8;

input 	[DATA_SIZE_PARAMETER-1:0]	dataA;
input 	[DATA_SIZE_PARAMETER-1:0]	dataB;
input					cin;

output	[DATA_SIZE_PARAMETER-1:0]	sum_out;
output					carry;


wire	[DATA_SIZE_PARAMETER:0]	c;
wire	[DATA_SIZE_PARAMETER-1:0]	sum_out;

genvar	k;
assign c[0] 	= cin;
assign carry	= c[DATA_SIZE_PARAMETER];


generate  
    for (k = 0; k <= DATA_SIZE_PARAMETER-1 ; k = k + 1) begin:makeAddSub
    
      fullAdder 
      fullAdderInstancek
	  (
	    .cin(c[k]),
	    .a(dataA[k]),
	    .b(dataB[k]),
	    .sum(sum_out[k]),
	    .cout(c[k+1])
	  );
    end
endgenerate


endmodule