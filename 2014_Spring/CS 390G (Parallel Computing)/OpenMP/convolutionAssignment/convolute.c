// Serial version of image convolution   
// using 1st stage of the Canny edge detector (see:  http://en.wikipedia.org/wiki/Canny_edge_detector)
//
//				2	4	5	4	2
//				4	9	12	9	4
// B =  1/159 	5	12	15	12	5	*  A
//				4	9	12	9	4
//				2	4	5	4	2
// 			
//
 
#include <stdio.h>
#include <stdlib.h> 
#include <omp.h>
  
//#define N 10   
#define VAL_MAX 19

int N = 5000;

float mask[5][5] = {
		 {2.0, 4.0, 5.0, 4.0, 2},
		 {4.0, 9.0, 12.0, 9.0, 4},
		 {5.0, 12.0, 15.0, 12.0, 5},
		 {4.0, 9.0, 12.0, 9.0, 4},
		 {2.0, 4.0, 5.0, 4.0, 2}};

float **Allocate2DArray( int nRows, int nCols) {
    //(step 1) allocate memory for array of elements of column
    float **ppi = (float **) malloc(sizeof(float *)*nRows);

    //(step 2) allocate memory for array of elements of each row
    float *curPtr = (float *) malloc(sizeof(float) * nRows * nCols);

    // Now point the pointers in the right place
	int i;
    for( i = 0; i < nRows; ++i)
    {
        *(ppi + i) = curPtr;
         curPtr += nCols;
    }
    return ppi;
}

void Free2DArray(float** Array) {
    free(*Array);
    free(Array);
}
  
void blur(float **out, float **in) {   
	// assumes "padding" to avoid messy border cases

	int i, j, r, c;
	float tmp, term;
	term = 1.0 / 157.0;
	for (i = 0; i < N-4; i++) {   
		for (j = 0; j < N-4; j++) {
			tmp = 0.0;
			for (r = 0; r < 5; r++) {
				for (c = 0; c < 5; c++) {
					tmp += in[i+r][j+c] * mask[r][c];
				}
			}
			out[i+2][j+2] = term * tmp;
		}   
	}   
}  

void init_data(float **W, float **V)   {   
	#define EDGE 5.0

	// create random, directed weight matrix
	int i,j, k;  

	for (i = 0; i < N; i++) {   
		for (j = 0; j < N; j++) {   
			W[i][j] = (rand()% (10 *VAL_MAX)) / 10.0;    
		}   
	}   

	//Set up edge values within border elements
	for (k = 0; k < N; k++){   
		W[0][k] = W[k][0] = V[0][k] = V[k][0] = EDGE;  
		W[N-1][k] = W[k][N-1] = V[N-1][k] = V[k][N-1] = EDGE;    
	}   
	
}   
  
int main(int argc, char* argv[])   {

	if (argc > 1) N = atoi(argv[1]);
	float **startImage = Allocate2DArray(N, N);   
	float **outImage = Allocate2DArray(N, N);  
	int i, j;

	init_data(startImage, outImage);   
  
	printf("\nStart values:\n");
	for (i=0; i<N; i++){   
		for (j=0; j<N; j++){   
			if (j == 0 || j == N-1 || i == 0 || i == N-1)
				printf("*** ");
			else
				printf ("%5.1f ",startImage[i][j]);   
		}   
		printf("\n");   
	}

	double start = omp_get_wtime();
	blur(outImage, startImage);   
	double end = omp_get_wtime();
  
	printf("\n\nConvoluted values:\n");
 
	for (i=0; i<N; i++){
		for (j=0; j<N; j++){   
			if (j == 0 || j == N-1 || i == 0 || i == N-1)
				printf("*** ");
			else
				printf ("%5.1f ",outImage[i][j]);   
		}   
		printf("\n");   
	}
	
	printf("Serial Execution time on size %d x %d array = %f seconds\n", N, N, end-start);

	Free2DArray(startImage);
	Free2DArray(outImage);
	return 0;   
}
