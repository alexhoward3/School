/*

	Example Combo Logic
	MuxCase.v

	Dave Williams
	EET 4020
	Spring 2013


*/

module MuxCase
(
	// inputs
		select,
		data_in,
	// output
		data_out
);

input 	[1:0]	select;
input 	[3:0]	data_in;
output 		data_out;

wire	[3:0]	data_in;
reg	data_out;		// NOTICE use of reg type for output.  


// 4 to 1 MUX using CASE
always@ (select or data_in)	
begin

   	case(select)
		2'b00:	data_out = data_in[3];
		2'b01:	data_out = data_in[2];
		2'b10:	data_out = data_in[1];
		2'b11:	data_out = data_in[0];
       	default: 
		data_out = data_in[0];
	endcase
   
end
endmodule
