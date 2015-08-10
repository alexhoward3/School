/*

	EET 2310
	topAdder.v
	Fall 2013
	Dave W. 
	
	
	Hierarchial Verilog design of a parameterized ripple-carry adder
	per section 3.3.3 in the text. 

      
*/

/*	 note on using include files
	the path description uses a backslash for WIN 
	and a forward slash for Linux
	
	the command line syntax is > inverilog -DWIN topGate.v
	or > iverilog topGate.v
	
*/


`ifdef WIN

`include ".\fullAddeBlockr.v"		// Win syntax
`include ".\addSubControl.v"		

`else

`include "./fullAdderBlock.v"		// Linux syntax
`include "./addSubControl.v"		

`endif


module topAdder();


`define DATA_SIZE 32

reg	[`DATA_SIZE-1:0]	data_in1;
reg	[`DATA_SIZE-1:0]	data_in2;
wire	[`DATA_SIZE-1:0]	data_out;
wire	[`DATA_SIZE-1:0]	sum_out;

wire				c_out;
reg				addSub;

/*
     module that performs a bit-to-bit inversion based
     on a control signal input

*/

addSubControl #(.DATA_WIDTH(`DATA_SIZE))
addSubControl_instance
(
    .din(data_in2),
    .addSubStrobe(addSub),				// control signal input 0 = addition 1 = subtraction
    .dout(data_out)

);


fullAdderBlock #(.DATA_SIZE_PARAMETER(`DATA_SIZE))	// name of the submodule
fullAdderBlock_instance					// instance name
(
    .cin(addSub),
    .dataA(data_in1),					// port connections
    .dataB(data_out),
    .sum_out(sum_out),
    .carry(c_out)

);


initial
begin:main_stimulus

      data_in1 = 32'd1505;
      data_in2 = 32'd1900;
      addSub  = 0;		// add
      
    #1;
      $display("-------------add ------------------");
      $display("data_in1 = %d , data_in2 = %d \n",data_in1,data_in2);
      $display("sum_out = %d , c_out = %b \n",sum_out,c_out);
      $display("----------------------------------");
      addSub  = 1;		// subtract
      
    #1;
      $display("------------subtract--------------");
      $display("data_in1 = %d , data_in2 = %d \n",data_in1,data_in2);
      $display("sum_out = %h , c_out = %b \n",sum_out,c_out);
      $display("----------------------------------");


end



endmodule