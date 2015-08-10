/*
 * Alex Howard
 * CS 390-G
 * Dr. Aaron Gordon
 * 
 * slidesortchapel.chpl
 * 
 * An implementation of the slidesort alogorithm.
 * http://rowdy.msudenver.edu/~gordona/cs390G-parallel/hw/slidesort.html
 */

use Random;
use Time;
writeln("Slidesort Chapel:\nAn Implementation of the slidesort algorithm in Chapel\n\n");

var COLS, ROWS, BUFF, START, END, SIZE, FULL:  int;
var toPr:  bool;
var rs = new RandomStream(SeedGenerator.currentTime, parSafe=false);
var number: int;

proc timing(mes: string, st: real, en: real) {
    writeln(mes + " : " + (en-st) + " seconds");
}

proc init(arr: []int) {
    for i in 0..(BUFF-1) {
	arr[i] = 0;
    }
    for i in START..END {
	number = ((100.0 * rs.getNext()) + 1) : int; //Random number
	arr[i] = number;
	//number = number + 1; //Sequential iterator
    }
    for i in (END+1)..FULL {
	arr[i] = 0;
    }
}

proc cpBuffer(arr: []int) {
    for i in 0..BUFF {
	arr[i] = 0;
    }
    for i in (END+1)..FULL {
	arr[i] = 0;
    }
}

proc print(arr: []int) {
    writeln("\n_________________________________\n\n");
    var i: int = START;
    for k in 1..ROWS {
	for j in i..END by ROWS {
		write(j + ":[" + arr[j] + "]");
		if(arr[j] >= 100) { write("\t"); }
		if(arr[j] < 100) { write("\t\t"); }
	}
	i = i + 1;
	write("\n");
    }
    
    writeln("\n_________________________________\n");
}

proc swap(arr: []int, num1: int, num2: int) {
    var tmp: int;
    tmp = arr[num1];
    arr[num1] = arr[num2];
    arr[num2] = tmp;
}

proc isSorted(arr: []int) {
    write("\n\nChecking if array is sorted...");
    for i in (BUFF+1)..(FULL-BUFF) {
	if(arr[i-1] > arr[i]) {
	    write("Done \nArray NOT sorted (failed at position: " + i + ")");
	}
    }
    writeln("Done \nArray is sorted!");
}

proc insort(arr: []int) {
    write("Serial Insertion Sort: ");
    for col in START..SIZE by ROWS {
	for pos in col..((col+ROWS)-1) {
	    while(pos > col && arr[pos-1] > arr[pos]) {
		swap(arr, pos, pos-1);
		pos = pos-1;
	    }
	}
    }
    write("Done\n");
}

proc pinsort(arr: []int) {
    write("Parallel Insertion Sort: ");
    forall col in START..SIZE by ROWS {
	for pos in col..((col+ROWS)-1) {
	    while(pos > col && arr[pos-1] > arr[pos]) {
		swap(arr, pos, pos-1);
		pos = pos-1;
	    }
	}
    }
    write("Done\n");
}

proc transposeUp(arr: []int) {
    write("Transpose Up: ");
    var rowIt, colIt, colPos, rowPos: int;
    var tsp = arr;
    rowPos = BUFF;
    for rowIt in BUFF..((BUFF+ROWS)-1) {
	colPos = rowIt;
	for colIt in 1..(COLS-1) {
	    tsp[colPos] = arr[rowPos];
	    colPos = colPos + ROWS;
	    rowPos = rowPos + 1;
	}
    }
    write("Done\n");
    return tsp;
}

proc transposeDown(arr: []int) {
    write("Transpose Down: ");
    var rowIt, colIt, colPos, rowPos: int;
    var tsp = arr;
    rowPos = BUFF;
    for rowIt in BUFF..((BUFF+ROWS)-1) {
	colPos = rowIt;
	for colIt in 1..(COLS-1) {
	    tsp[rowPos] = arr[colPos];
	    colPos = colPos + ROWS;
	    rowPos = rowPos + 1;
	}
    }
    write("Done\n");
    return tsp;
}

proc shiftUp() {
    write("Shift Up\n");
    START = 0;
    END = FULL;
}

proc shiftDown() {
    write("Shift Down\n");
    START = BUFF;
    END = FULL - BUFF;
}

proc compute(cnum: int) {
    COLS = cnum;
    ROWS = 2 * (COLS*COLS);
    BUFF = ROWS / 2;
    FULL = (ROWS * COLS) - 1;
    START = BUFF;
    END = FULL - BUFF;
    SIZE = END - BUFF;
}

proc main() {
    compute(100);
    writeln("SLIDESORT:");
    writeln("BUFFER = " + BUFF);
    writeln("COLUMNS  = " + COLS);
    writeln("ROWS     = " + ROWS);
    writeln("FULL     = " + FULL);
    writeln("START    = " + START);
    writeln("END      = " + END);
    writeln("SIZE     = " + SIZE);
    
    var ARRAY: [0..FULL]int;
    init(ARRAY);
    
    //writeln("FULL ARRAY WITH BUFFER: \n_______________________\n");
    //shiftUp();
    //print(ARRAY);
    //shiftDown();
    
    var start = Time.getCurrentTime(TimeUnits.seconds);
    
    write("\nSTEP 1: ");
    pinsort(ARRAY);
    
    write("\nSTEP 2: ");
    ARRAY = transposeUp(ARRAY);
    
    write("\nSTEP 3: ");
    pinsort(ARRAY);
    
    write("\nSTEP 4: ");
    ARRAY = transposeDown(ARRAY);
    
    write("\nSTEP 5: ");
    pinsort(ARRAY);
    
    write("\nSTEP 6: ");
    shiftUp();
    
    write("\nSTEP 7: ");
    pinsort(ARRAY);
    
    write("\nSTEP 8: ");
    shiftDown();
    
    isSorted(ARRAY);
    
    var end = Time.getCurrentTime(TimeUnits.seconds);
    timing("\nOverall Time",start, end);
}