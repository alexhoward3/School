#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/* Demo program for OpenMP: computes trapezoidal approximation to an integral*/
double pi = 3.141592653589793238462643383079;
int main(int argc, char** argv) {
	/* Variables */
	double a = 0.0, b = pi; 		/* limits of integration */
	//int n = 1048576; 				/* number of subdivisions = 2^20 */
	int n = 1073741824;
	double h = (b - a) / n; 		/* width of subdivision */
	double integral; 				/* accumulates answer */
	int threadct = 1; 				/* number of threads to use */

	/* parse command-line arg for number of threads */
	if (argc > 1)
		threadct = atoi(argv[1]);

	double f(double x);

	integral = (f(a) + f(b))/2.0;
	int i;
	 #pragma omp parallel for num_threads(threadct) shared (a, n, h) private (i) reduction(+: integral)
	for(i = 1; i < n; i++) {
		integral += f(a+i*h);
	}
	integral = integral * h;
	printf("With n = %d trapezoids, our estimate of the integral from %5.2f to %5.2f is %12.10f\n", n,a,b,integral );

	return 0;
}

double f(double x) {
	return sin(x);
}












	/* #pragma omp parallel for num_threads(threadct) shared (a, n, h, integral) private (i)  */
	/* #pragma omp parallel for num_threads(threadct) shared (a, n, h) private (i) reduction(+: integral) */
