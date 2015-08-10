/*
 * Alex Howard
 * slidesort.c
 * 
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
		arr[i] = (rand() % (10-1)) + 1; //Random number between 10000 and 99999
		//arr[i] = (rand() % (999 - 100) + 100); //Random number between 999 and 100, debug only
		//arr[i] = j; //Sucessive number, debug only
	}
	for(i = FULL; i > END - 1; i--) {
		arr[i] = 0;
	}

	double etime = omp_get_wtime();
	printf("Done\n");
	time("Initialize", etime-stime);
}

void unallocate(int* arr) {
	free(arr);
}

void print(int *arr) {
	int i, j, k;
	printf("\n_________________________________\n\n");

	for(i = START, k = 0; k < ROWS; i++, k++) { //Print beginning at START value
		for(j = i; j < END; j = j + ROWS) { //j = j + ROWS : print in row major order
			if(arr[j] == 0) {
				printf("[%d%d]", arr[j], arr[j]); //Buffer padding
			} else {
				printf("[%d]", arr[j]);
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
	int i;
	for(i = BUFF + 1; i < FULL - BUFF; i++) {
		if(arr[i - 1] > arr[i]) {
			return 0; //False, array not sorted
		}
	}
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
	if(isSorted(arr)) printf("ARRAY IS SORTED!\n");
	time("Serial Insertion Sort", etime-stime);
}

void pinsort(int *arr, int nThr) {
	int i, j, k, TID, begin;
	int chunk = SIZE / nThr;
#pragma omp parallel private (TID) num_threads(nThr)
	{
		TID = omp_get_thread_num();
		begin = (TID * chunk) + BUFF;
		for(i = begin; i < begin + chunk; i++)
		{
			j = i;
			while(j > begin && arr[j-1] > arr[j]) {
				swap(arr, j, j-1);
				j = j-1;
			}
		}
		if(isSorted(arr) == 0){
			pinsort(arr, 1);
		}
	}
}

void psort(int *arr, int thr) {
	printf("\nPARALLEL INSERTION SORT: ");
	double stime = omp_get_wtime();

	pinsort(arr, thr);

	double etime = omp_get_wtime();
	printf("Done\n");
	if(isSorted) printf("ARRAY IS SORTED!\n");
	time("Parallel Insertion Sort", etime-stime);
}

void compute(int bnum, int cnum) {
	BUFF = bnum;
	COLS = cnum;
	ROWS = BUFF * 2;
	FULL = ROWS * COLS;
	START = BUFF;
	END = FULL - BUFF;
	SIZE = END - BUFF;
}

void pinsortc(int *arr, int thr) {
	#pragma omp parallel num_threads(thr)
	{
		int col, iter, pos;
		#pragma omp for
		for(col = BUFF; col < SIZE; col = col + ROWS) {
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


void run(int buf, int col) {
	compute(buf, col);
	printf("\nBUFFER   = %d\n", BUFF);
	printf("COLUMNS  = %d\n", COLS);
	printf("ROWS     = %d\n", ROWS);
	printf("FULL     = %d\n", FULL);
	printf("END      = %d\n", END);
	printf("SIZE     = %d\n\n", SIZE);

	int *par = allocate(FULL);
	init_data(par);
	printf("\nINITIAL\n");
	print(par);
	printf("\nSORTED\n");
	pinsortc(par, nThreads);
	print(par);

	unallocate(par);
}

int main(int args, const char *argc[]) {
	if(args > 3) printf("sort : [buff] [cols] [threads]");
	if(args == 5) toPr = 1;
	nThreads = atoi(argc[3]);
	double stime = omp_get_wtime();
	printf("Slidesort : Parallel\nThreads : %d", nThreads);
	run(atoi(argc[1]), atoi(argc[2]));
	double etime = omp_get_wtime();
	time("\nOVERALL", etime-stime);
	return 0;
}