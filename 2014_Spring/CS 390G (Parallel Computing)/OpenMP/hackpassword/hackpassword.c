//
//  main.c
//  HackPassword
//
//  Created by Aaron Gordon on 9/6/11.
//  Copyright 2012  All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <omp.h>

#define MAXSIZE 32

void solveOrig(char *);
void solve(void);
void showCharSet(void);
int test(char *, int);

char password[MAXSIZE];
char BASE_PAT[MAXSIZE] = "00000000000000000000000000000";	//yech - FIX
int found;
int nThreads;			//number of threads

void showCharSet() {
    char ch;
    for (ch='0'; ch<='z'; ch++) {
		printf("%c",ch);
        printf("\n");
    }
}

int test(char  *pattern, int k) {	//try all possibilities for this subcase
    char pat[MAXSIZE];
    strcpy(pat, pattern);
	int rip = k - 1;
        while (rip > 0  && !found) {
            rip = k - 1;
            char ch;
            for (ch='0'; ch<='z'; ch++) {	//try all possible final characters
                pat[rip] = ch;
                if (strcmp(pat, password) == 0) {
                    found = 1;
                    printf("\nFound by thread %d, the password, %s is %s\n",
											omp_get_thread_num(), password,pat);
                    return 1;
                }
            }
            pat[rip] = '0';  //reset position rip
			//propagate to earlier positions
            rip--;
            while (rip >= 0) {
                if (pat[rip] < 'z') {
                    pat[rip] += 1;
                    break;
                }
                pat[rip] = '0';  //reset position rip
                rip--;                
            }
        } //while rip
    return 0;
}

void solve() {
	int k;	//length of password to try
    for (k=1; k<MAXSIZE; k++) {        //try all passwords of size k
        char pat[MAXSIZE];
		strcpy(pat,BASE_PAT);
        pat[k] = '\0';
        found = 0;
        char ch, oldch;
 
		#pragma omp parallel private (oldch) num_threads (nThreads)
        {
			printf("Thread %d, nthreads %d\n", omp_get_thread_num(),
									omp_get_num_threads());
			#pragma omp for private (ch) nowait
			for (ch='0'; ch<='z'; ch++) {
				oldch = ch;
				pat[0] = ch;
				if (test(pat, k)) {
					found = 1;
				}
				if (found) {
					ch='z';
				}
			} //for ch - end of parallel
            printf("Thread %d:  oldch is [%c]\n",omp_get_thread_num(), oldch);
        }  //end of OMP Parallel block
        
        if (found) {
            return;
        }
        printf("\nPassword not found yet - %d\n",k);
    } //for k
    printf("\nPassword not found.\n");
}

void solveOrig(char *target) {
	int k;
    for (k=1; k<MAXSIZE; k++) {
        //try all passwords of size k
        char pat[MAXSIZE];
		strcpy(pat,BASE_PAT);
        int rip = k - 1;
		pat[k] = '\0';
        while (rip >= 0) {
            rip = k - 1;
            char ch;
            for (ch='0'; ch<='z'; ch++) {
                pat[rip] = ch;
                if (strcmp(pat, target) == 0) {
                    printf("\nFound the password, %s is %s\n",target,pat);
                    return;
                }
            }
            pat[rip] = '0';  //reset position rip
            rip--;
            while (rip >= 0) {
                if (pat[rip] < 'z') {
                    pat[rip] += 1;
                    break;
                }
                pat[rip] = '0';  //reset position rip
                rip--;                
            }
        }

        printf("\nPassword not found yet - %d\n",k);
    }
    printf("\nPassword not found.\n");
}


int main (int argc, const char * argv[])
{
    if (argc < 3)	{
        printf("usage:  hackpassword <thePassword> <number threads>\n");
        exit(1);
    } 
    strcpy(password,argv[1]);
	if (strcmp(argv[2],"s") == 0) {
		printf("using sequential version\n");
		double c0 = omp_get_wtime();
		solveOrig(password);
		double c1 = omp_get_wtime();
		printf ("\nSequential elapsed CPU time:        %f\n",(c1 - c0));
	} else {
		nThreads = atoi(argv[2]);
		
		//strcpy(password, "a8Btr");  //Bt"); //ru92JJ01Za");
		// insert code here...
		printf("Hello, World! - password is [%s] - solving with %d threads\n",
									password,nThreads);
		//showCharSet();
		double c0 = omp_get_wtime();
		solve();
		double c1 = omp_get_wtime();
		printf ("\nParallel elapsed CPU time:        %f\n",(c1 - c0));
	}
    return 0;
}