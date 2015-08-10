/*

	EET 2310
	addSubControl.v
	Fall 2013
	Dave W. 

 
      
*/

module addSubControl
(
    // inputs
      din,
      addSubStrobe,
      
    // output
      dout
);
parameter DATA_WIDTH = 8;

input 	[DATA_WIDTH -1:0]	din;
input 				addSubStrobe;
output 	[DATA_WIDTH -1:0]	dout;


assign dout 	= {DATA_WIDTH{addSubStrobe}} ^ din;		// concatenation to create many control signals	
    

endmodule