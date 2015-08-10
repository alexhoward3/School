/*

	test MuxCase

	Dave Williams
	EET 4020
	Spring 2013


*/
`include "./MuxCase.v"

// Stimulus (top level module)
module tb;

// Declare variables
reg 	[1:0] 	stim_select;
reg	[3:0]	stim_data_in;
wire	stim_data_out;

// Instantiate the module 
MuxCase DUT
( 
	.select(stim_select),
	.data_in(stim_data_in),
	.data_out(stim_data_out)
);

// Setup the monitor
initial
	$monitor($time, " stim_select= %b,  stim_data_in= %b, stim_data_out= %b\n", stim_select,stim_data_in,stim_data_out);

// Setup some VCD wave output
initial
begin
	$dumpfile("mux.vcd");
	$dumpvars;
end

initial
begin
	stim_select = 2'b00;
	stim_data_in = 4'b0110;

	#5 stim_select = 2'b01;
	stim_data_in = 4'b1111;

	#5
	stim_data_in = 4'b0000;
	
	#5 stim_select = 2'b10;
	stim_data_in = 4'b1101;

	#5 stim_select = 2'b11;
	stim_data_in = 4'b0111;


	#20 $finish;
end

endmodule

