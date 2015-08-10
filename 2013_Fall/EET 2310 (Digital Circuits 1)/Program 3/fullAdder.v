/*

	EET 2310
	fullAdder.v
	Fall 2013
	Dave W. 

 
      
*/

module fullAdder
(
    // inputs
      cin,
      a,
      b,
    // output
      sum,
      cout
);
//`define FULL_ADDER_GATE_LEVEL
input 		a;
input 		b;
input  		cin;
output		sum;
output		cout;


`ifdef FULL_ADDER_GATE_LEVEL			

						// gate level implementation

`else

    assign sum = a ^ b ^ cin;			// dataflow style of modeling
    assign cout = (a & b)|(a & cin)|(b & cin);
    
`endif

endmodule