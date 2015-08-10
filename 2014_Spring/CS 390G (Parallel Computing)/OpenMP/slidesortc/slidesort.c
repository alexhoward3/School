/*
 * Alex Howard
 * slidesort.c
 * 
 * An implementation of the slidesort alogorithm.
 */

#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int R, C, buffer;

int **allocate(int rows, int cols) {
	printf("Allocating...");
	//(step 1) allocate memory for array of elements of column
	int **ar = (int **) malloc(sizeof(int *)*rows);

	//(step 2) allocate memory for array of elements of each row
	int *curPtr = (int *) malloc(sizeof(int) * rows * cols);

	// Now point the pointers in the right place
	int i;
	for( i = 0; i < rows; ++i)
	{
		*(ar + i) = curPtr;
		curPtr += cols;
	}
	printf("Done\n");
	return ar;
}

void init_data(int **A) {
	printf("Initializing...");
	// create random, directed weight matrix
	int i, j;

	for (i = 0; i < R; i++) {
		for (j = 0; j < C; j++) {
			A[i][j] = rand() % 10;
		}   
	}
	printf("Done\n");
}

void print(int **A) {
	int i, j;
	printf("********************\nPrinting...\n********************\n");
	for(i = 0; i < R; i++) {
		for(j = 0; j < C; j++) {
			printf("[%d] ", A[i][j]);
		}
		printf("\n");
	}
	printf("\n********************\n");
}

void swap(int **A, int i, int j) {
	int tmp = A[i][j-1];
	A[i][j-1] = A[i][j];
	A[i][j] = tmp;
}

void insort(int **A) {
	int i, j;
	for(i = R - 1; i > 0; i--) {
		for(j = C - 1; j > 0; j--) {
			int x = j;
			while(A[i-1][j] > A[i][j] && x > 0) {
				swap(A, i, j);
				x--;
			}
		}
	}
}

int main() {
	R = 18;
	C = 3;
	int **array = allocate(R, C);
	init_data(array);
	print(array);
	insort(array);
	print(array);
	return 0;
}
