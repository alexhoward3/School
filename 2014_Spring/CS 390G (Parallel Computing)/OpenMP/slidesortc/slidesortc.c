/*
 * Alex Howard
 * slidesortc.c
 * An implementation of the slidesort alogorithm.
 * http://rowdy.msudenver.edu/~gordona/cs390G-parallel/hw/slidesort.html
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <limits.h>
#include <omp.h>

int FULL, ROWS, COLS, BUFF, START, END, SIZE;
int nThreads;
int toPr; //Print

void time(char mess[], double t) {
	printf("%s execution time: %f seconds\n", mess, t);
}

int *allocate(int length) {
	printf("Allocating...");
	double stime = omp_get_wtime();

	int *ar = (int *) malloc(sizeof(int *)*length);
	printf("Done\n");

	double etime = omp_get_wtime();
	time("Allocate", etime-stime);
	return ar;
}

void init_data(int *arr) {
	printf("Initializing...");
	int i, j;

	double stime = omp_get_wtime();

	for(i = 0; i < BUFF; i++) {
		arr[i] = 0;
	}
	for(i = START, j = 1; i < END; i++, j++) {
		arr[i] = (rand() % (9999-1000)) + 1000; //Random number between 1000 and 9999
		//arr[i] = (rand() % (10 - 1) + 1); //Random number between 1 and 10. Debug only
		//arr[i] = j; //Sucessive number, debug only
	}
	for(i = FULL; i > END - 1; i--) {
		arr[i] = 0;
	}

	double etime = omp_get_wtime();
	printf("Done\n");
	time("Initialize", etime-stime);
}

void cpBuffer(int *arr) {
	int i, j;
	for(i = 0; i < BUFF; i++) {
		arr[i] = 0;
	}
	for(i = FULL; i > END-1; i--) {
		arr[i] = 0;
	}
}

void print(int *arr) {
	int i, j, k;
	printf("\n_________________________________\n\n");

	for(i = START, k = 0; k < ROWS; i++, k++) { //Print beginning at START value
		for(j = i; j < END; j = j + ROWS) { //j = j + ROWS : print in row major order
			if(arr[j] == 0) {
				printf("%d:[%d%d%d%d]", j, arr[j], arr[j], arr[j], arr[j]); //Buffer padding
			} else {
				printf("%d:[%d]", j, arr[j]);
			}
			printf("\t"); //Tabulator for cleanliness
		}
		printf("\n");
	}

	printf("\n_________________________________\n");
}

void swap(int *ar, int num1, int num2) {
	int tmp = ar[num1];
	ar[num1] = ar[num2];
	ar[num2] = tmp;
}

int isSorted(int *arr) {
	printf("\nChecking if array is sorted...");
	int i;
	for(i = BUFF + 1; i < FULL - BUFF; i++) {
		if(arr[i - 1] > arr[i]) {
			printf("Done (failed at position: %d)\n", i);
			return 0; //False, array not sorted
		}
	}
	printf("Done\n");
	return 1; //True, array sorted
}

void insort(int *arr) {
	printf("\nSERIAL INSERTION SORT: ");
	int i, j;
	int chunk = SIZE / nThreads;
	double stime = omp_get_wtime();
	for(i = BUFF; i < FULL - BUFF; i++) {
		j = i;
		while(j > BUFF && arr[j-1] > arr[j]) {
			swap(arr, j, j-1);
			j = j-1;
		}
	}
	double etime = omp_get_wtime();
	printf("Done\n");
	time("Serial Insertion Sort", etime-stime);
}

void pinsort(int *arr, int thr) {
	#pragma omp parallel num_threads(thr)
	{
		int col, iter, pos;
		#pragma omp for
		for(col = START; col < SIZE; col = col + ROWS) {
			for(iter = col; iter < col + ROWS; iter++) {
				pos = iter;
				while(pos > col && arr[pos-1] > arr[pos]) {
					swap(arr, pos, pos-1);
					pos = pos-1;
				}
			}
		}
	}
}

void psort(int *arr, int thr) {
	printf("\nPARALLEL INSERTION SORT: ");
	double stime = omp_get_wtime();

	pinsort(arr, thr);

	double etime = omp_get_wtime();
	printf("Done\n");
	time("Parallel Insertion Sort", etime-stime);
}

int *transposeUp(int *arr) {
	printf("\nTranspose Up:\n");
	double stime = omp_get_wtime();
	int *tsp = allocate(FULL); //Create new array to store values into
	/**
	 * -iter = iterator through each row
	 * -tspPos = Position in the tsp array. Equal to the position of each columnar element.
	 * 		In a 4 x 8 mesh: (4, 12, 20, 28 then 5, 13, 21, 29...) 
	 * -arPos = Position to the arr array. Equal to each sucessive value.
	 * -colIt = Iterator through each column. Iterates for the next tspPos.
	 **/
	int iter, tspPos, arrPos, colIt;
	arrPos = BUFF; //Ignore the buffer values

	for(iter = BUFF; iter < ROWS + BUFF; iter++) { //Traverse to each row, ignoring the buffer
		//Go to each sucessive columnar value in tsp and swap it with the next position in arr
		//Do this only for the amount of columns, then move to the next row
		for(tspPos = iter, colIt = 0; colIt < COLS - 1; tspPos = tspPos + (BUFF*2), colIt++, arrPos++) {
			tsp[tspPos] = arr[arrPos];
		}
	}
	cpBuffer(tsp);
	double etime = omp_get_wtime();
	printf("Transpose done\n");
	time("Transpose Up", etime-stime);
	return tsp;
}

int *transposeDown(int *arr) {
	printf("\nTranspose Down:\n");
	double stime = omp_get_wtime();
	int *tsp = allocate(FULL);
	//REVERSE OF TRANSPOSE UP
	int iter, tspPos, arrPos, colIt;
	arrPos = BUFF; //Ignore the buffer values
	int chunk = ROWS / nThreads;
	//#pragma omp parallel
	//{
	//#pragma omp for schedule(dynamic,chunk) nowait
	for(iter = BUFF; iter < ROWS + BUFF; iter++) { //Traverse to each row, ignoring the buffer
		//Go to each sucessive columnar value in tsp and swap it with the next position in arr
		//Do this only for the amount of columns, then move to the next row
		for(tspPos = iter, colIt = 0; colIt < COLS - 1; tspPos = tspPos + (BUFF*2), colIt++, arrPos++) {
			tsp[arrPos] = arr[tspPos];
		}
	}
	//} /* END PARALLEL */
	cpBuffer(tsp);
	double etime = omp_get_wtime();
	printf("Transpose done\n");
	time("Transpose Down", etime-stime);
	return tsp;
}

void shiftUp() {
	printf("\nShift Up\n");
	START = 0;
	END = FULL;
}

void shiftDown() {
	printf("\nShift Down");
	START = BUFF;
	END = FULL - BUFF;
}

void compute(int cnum) {
	COLS = cnum;
	ROWS = 2 * (COLS*COLS);
	BUFF = ROWS / 2;
	FULL = ROWS * COLS;
	START = BUFF;
	END = FULL - BUFF;
	SIZE = END - BUFF;
}

void run(int col) {
	compute(col);
	printf("\nBUFFER   = %d\n", BUFF);
	printf("COLUMNS  = %d\n", COLS);
	printf("ROWS     = %d\n", ROWS);
	printf("FULL     = %d\n", FULL);
	printf("END      = %d\n", END);
	printf("SIZE     = %d\n\n", SIZE);

	int *array = allocate(FULL);
	printf("\n");
	init_data(array);

	if(toPr) { 
		printf("\nINITIAL LIST WITH BUFFER\n");
		shiftUp();
		print(array);
		shiftDown();
	}

	printf("\n\nSTEP 1: Sort: ");
	//STEP 1
	if(nThreads > 1) psort(array, nThreads); //Sort using parallel insertion sort
		else insort(array); //Sort using serial insertion sort
	if(toPr) print(array);

	printf("\nSTEP 2: Transpose Up: ");
	//STEP 2
	array = transposeUp(array);
	if(toPr) print(array);

	printf("\nSTEP 3: Sort: ");
	//STEP 3
	if(nThreads > 1) psort(array, nThreads); //Sort using parallel insertion sort
		else insort(array); //Sort using serial insertion sort
	if(toPr) print(array);

	printf("\nSTEP 4: Transpose Down: ");
	//STEP 4
	array = transposeDown(array);
	if(toPr) print(array);

	printf("\nSTEP 5: Sort: ");
	//STEP 5
	if(nThreads > 1) psort(array, nThreads); //Sort using parallel insertion sort
		else insort(array); //Sort using serial insertion sort
	if(toPr) print(array);

	printf("\nSTEP 6: Shift Up: ");
	//STEP 6
	shiftUp();
	if(toPr) print(array);

	printf("\nSTEP 7: Sort: ");
	//STEP 7
	if(nThreads > 1) psort(array, nThreads); //Sort using parallel insertion sort
		else insort(array); //Sort using serial insertion sort
	if(toPr) print(array);

	printf("\nSTEP 8: Shift Down: ");
	//STEP 8
	shiftDown();
	if(toPr) print(array);
	printf("\n");
	
	if(isSorted(array)){
		printf("Array is sorted!\n\n");
	} else {
		printf("Array not sorted!\n\n");
	}
	
	free(array);
}

int main(int args, const char *argc[]) {
	char p = 'p';
	if(args < 3) { //HOWTO
		printf("slidesort: <columns> <print[0 or 1]> <threads[optional]>\n");
		printf("Rows will equal 2*(Columns^2)\n");
		printf("Buffer will equal Rows/2\n");
		printf("NOTE: will not print if colums are larger than 10!\n");
		printf("exit");
		exit(1);
	}
	if(args < 4) { //SERIAL EXECUTION
		nThreads = 1;
		if(args == 4) {
			if(atoi(argc[1]) > 10) {
				printf("\nArray columns larger 10, not printing...\n\n");
			} else {
				toPr = atoi(argc[2]);
			}
		}
		printf("Slidesort : Serial");
		double stime = omp_get_wtime();
		run(atoi(argc[1]));
		double etime = omp_get_wtime();
		time("\nOVERALL SERIAL", etime-stime);
	}
	if(args == 4) { //PARALLEL EXECUTION
		if(atoi(argc[1]) > 10) {
			printf("\nArray colums larger 10, not printing...\n\n");
		} else {
			toPr = atoi(argc[2]);
		}
		nThreads = atoi(argc[3]);
		double stime = omp_get_wtime();
		printf("Slidesort : Parallel\nThreads : %d", nThreads);
		run(atoi(argc[1]));
		double etime = omp_get_wtime();
		time("\nOVERALL PARALLEL", etime-stime);
	}
	return 0;
}