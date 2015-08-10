/*
 * spellcheck.c
 *
 *  Created on: Apr 12, 2014
 *      Author: Alex Howard
 * Dr. Aaron Gordon
 * CS 390-G
 * http://rowdy.msudenver.edu/~gordona/cs390G-parallel/hw/spellCheck.html
 */

#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include <strings.h>
#include <string.h>

#define WORKTAG 1

//Prints the dictionary - DEBUG BE CAREFUL! DICTIONARY FILE IS QUITE LARGE!
void printdict(FILE *f) {
    char *wd;
    while(fscanf(f, "%s", wd) != EOF) {
	printf("%s\n", wd);
    }
}

//Prints the Levenshtein array
void printarray(char *word, char arr[][64], int size) {
    int i;
    if(size == 0) {
	printf("Word \"%s\" not found. No suggestions\n", word);
    } else {
	printf("\nWord \"%s\" not found! \nDid you mean:\n", word);
	for(i = 0; i < size; i++) {
	    printf("%d: %s\n", i, arr[i]);
	}
    }
}

//Min of three numbers
int min(int a, int b, int c) {
    int m = a;
    if(m > b) m = b;
    if(m < c) m = c;
    return m;
}

//Max of two numbers
int max(int a, int b) {
    int m = a;
    if(m < b) m = b;
    return m;
}

//Computes the Levenshtein distance
int lev(char *word, int wlen, char *line, int llen) {
    int matrix[wlen + 1][llen + 1];
    int i;
    for (i = 0; i <= wlen; i++) {
	matrix[i][0] = i;
    }
    for (i = 0; i <= llen; i++) {
	matrix[0][i] = i;
    }
    for (i = 1; i <= wlen; i++) {
	int j;
	char c1;
	
	c1 = word[i-1];
	for (j = 1; j <= llen; j++) {
	    char c2;
	    
	    c2 = line[j-1];
	    if (c1 == c2) {
		matrix[i][j] = matrix[i-1][j-1];
	    }
	    else {
		int delete;
		int insert;
		int substitute;
		int minimum;
		
		delete = matrix[i-1][j] + 1;
		insert = matrix[i][j-1] + 1;
		substitute = matrix[i-1][j-1] + 1;
		minimum = delete;
		if (insert < minimum) {
		    minimum = insert;
		}
		if (substitute < minimum) {
		    minimum = substitute;
		}
		matrix[i][j] = minimum;
	    }
	}
    }
    return matrix[wlen][llen];
}

//Parallel
void findp(char *word, int MAX_COUNT, int THRESH, int THREADS, int TID) {
    char levarray[MAX_COUNT][64];
    int found = -1;
    int wordlen = strlen(word);
    char line[64];
    int linelen;
    int levcount = 0;
    int dist, prc, tid = TID, threads = THREADS;
    MPI_Status stat;
    
    FILE *dict = fopen("words", "rt");
    if(tid == 0) printf("Computing in PARALLEL\n");
    
    //NOT MASTER OR LEV THREAD but a WORKER THREAD
    if(tid != 0 && tid != (threads-1)) {
	int done;
	if(dict == NULL) {
	    printf("\n[%d]: Error:\nCannot open or find the file for scan\n", tid);
	    exit(1);
	} else {
	    //printf("Dictionary file loaded sucessfully by SCAN thread [%d]\n", tid); //DEBUG
	    int listsize = 0;
	    while(fscanf(dict, "%s", line) != EOF) {
		listsize++;
	    }
	    //printf("List Size: %d\n", listsize);
	    rewind(dict);
	    int chunk = listsize/(threads - 2);
	    //printf("[%d]: Chunk size = %d\n", tid, chunk);
	    int pos;
	    for(pos = 0; pos < (chunk*(tid-1)); pos++) {
		fscanf(dict, "%s", line);
	    }
	    //printf("[%d]: Line is \"%s\" at position %d\n", tid, line, pos);
	    int inc = 0;
	    while(fscanf(dict, "%s", line) != EOF && inc <= chunk) {
		if(strcmp(word, line) == 0) {
		    printf("\nWord \"%s\" found as \"%s\"! by thread [%d]\n", word, line, tid);
		    //printf("Found at pos: %d\n", inc); //DEBUG
		    found = 1;
		    //Send message to master thread saying the word is found
		    MPI_Send(&found, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		    break;
		}
		inc++;
	    }
	    if(found == -1) found = 0;
	    if(found == 0) { //Word not found
		//Send message to master thread saying word is not found
		MPI_Send(&found, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		//printf("[%d]: Word \"%s\" NOT FOUND!\n", tid, word); //DEBUG
	    }
	}
	//printf("[%d]: Found = %d\n", tid, found);
    }
    //MASTER THREAD
    if(tid == 0) {
	int flag = -1, find = -1;
	MPI_Request request;
	MPI_Status status;
	int count = 0;
	while(1) { //Continuously update to check the status of the worker threads
	    if(flag != 0) {
		MPI_Irecv(&find, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &request);
		flag = 0;
	    }
	    MPI_Test(&request, &flag, &status);
	    if(flag != 0) {
		//printf("[%d] Flag = %d\n", tid, flag);
		//printf("[%d] Find = %d\n", tid, find);
		//printf("[%d] Recv : %d from tid [%d]\n", tid, find, status.MPI_SOURCE);
		flag = -1;
	    }
	    if(find == 1) { //If the word is found
		//printf("[%d] Word found! Sending find (%d) to last thread\n", tid, find);
		MPI_Send(&find, 1, MPI_INT, threads-1, 0, MPI_COMM_WORLD);
		break;
	    }
	    if(find == 0) { //If the word is not found
		//printf("[%d] Count = %d\n", tid, count);
		count++;
		//printf("[%d] NOW Count = %d\n", tid, count);
		find = -1;
	    }
	    if(count == (threads-2)) { //If all workers have reported not found, word dosen't exist in file
		find = 0;
		//printf("[%d] Word NOT found! Sending find (%d) to last thread\n", tid, find);
		//printf("[%d] Final Count = %d\n", tid, count);
		MPI_Send(&find, 1, MPI_INT, threads-1, 0, MPI_COMM_WORLD);
		break;
	    }
	}
	flag = -1, count = 0;
	int fin = -1;
	while(1) {
	    if(flag != 0) {
		MPI_Irecv(&fin, 1, MPI_INT, threads-1, MPI_ANY_TAG, MPI_COMM_WORLD, &request);
		flag = 0;
	    }
	    MPI_Test(&request, &flag, &status);
	    if(flag != 0) {
		//printf("Thread [%d] says it is done!\n", status.MPI_SOURCE); //DEBUG
		flag = -1;
	    }
	    if(fin == 1) {
		break;
	    }
	}
    }
    
    //LEV THREAD
    if(tid == (threads-1)) {
	int flag = -1, find = -1, done;
	MPI_Request request;
	MPI_Status status;
	if(dict == NULL) {
	    printf("\n[%d]: Error:\nCannot open or find the file for lev\n", tid);
	    exit(1);
	} else {
	    //printf("Dictionary file sucessfully loaded by LEV thread [%d]\n", tid); //DEBUG
	    while(fscanf(dict, "%s", line) != EOF) {
		//CHECK FOR THREAD 0'S FIND UPDATE
		if(flag != 0) {
		    MPI_Irecv(&find, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &request);
		    flag = 0;
		}
		MPI_Test(&request, &flag, &status);
		if(flag != 0) {
		    //printf("[%d] Recv : %d from tid [%d]\n", tid, find, status.MPI_SOURCE); //DEBUG
		    flag = -1;
		}
		if(find == 1) {
		    break;
		}
		//IF NOT FOUND GO THROUGH THE LIST AND CONTINUE TO FIND SIMILAR WORDS
		linelen = strlen(line);
		dist = lev(word, wordlen, line, linelen);
		prc = 100 * dist / max(wordlen, linelen);
		if(THRESH >= prc) {
		    if(levcount < MAX_COUNT) {
			strcpy(levarray[levcount], line);
			levcount++;
		    }
		}
	    }
	}
	while(1) { //CHECK A SECOND TIME FOR THREAD 0's FIND UPDATE
	    if(flag != 0) {
		MPI_Irecv(&find, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &request);
		flag = 0;
	    }
	    MPI_Test(&request, &flag, &status);
	    if(flag != 0) {
		//printf("[%d] Recv : %d from tid [%d]\n", tid, find, status.MPI_SOURCE); //DEBUG
		flag = -1;
	    }
	    if(find != -1) {
		break;
	    }
	}
	if(find == 0) {
	    //If not found, print the list
	    printf("[%d] Word not found, printing list...\n", tid);
	    printarray(word, levarray, levcount);
	    done = 1;
	    //Send done message to master thread
	    MPI_Send(&done, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
	} else if(find == 1) {
	    //If not found, do not print the list
	    printf("[%d] Word is found, NOT printing list...\n", tid);
	    done = 1;
	    //Send done message to master thread
	    MPI_Send(&done, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}
    }
    fclose(dict);
}

//Serial
int find(char *word, int MAX_COUNT, int THRESH) {
    printf("Computing in SERIAL\n");
    char levarray[MAX_COUNT][64];
    int found = 0;
    int wordlen = strlen(word);
    char line[64];
    int linelen;
    int levcount = 0;
    int dist, prc;
    double btime = MPI_Wtime();
    FILE *dict = fopen("words", "rt");
    if(dict == NULL) {
	printf("\nError:\nCannot open or find the file\n");
	exit(1);
    } else {
	printf("\nDictionary file loaded sucessfully\n");
	printf("Finding word: %s\n\n", word);
	while(fscanf(dict, "%s", line) != EOF) {
	    //printf("Comparing %s to %s\n", word, line); //Debug for comparing
	    if(strcmp(word, line) == 0) {
		printf("\nWord \"%s\" found as \"%s\"!", word, line);
		found = 1;
		break;
	    }
	    linelen = strlen(line);
	    dist = lev(word, wordlen, line, linelen);
	    prc = 100 * dist / max(wordlen, linelen);
	    if(THRESH >= prc) {
		//printf("\nline: %s, dist: %d, levcount: %d\n", line, dist, levcount); //Debug
		//printarray(levarray); //Debug
		if(levcount < MAX_COUNT) {
		    //printf("Putting string: %s into array\n", line); //Debug
		    //printf("PRC: %d\n\n", prc); //Debug
		    strcpy(levarray[levcount], line);
		    levcount++;
		}
	    }
	}
    }
    if(found == 0) { printarray(word, levarray, levcount); }
    fclose(dict);
    double etime = MPI_Wtime();
    printf("\nTIME FOR SERIAL : %f [sec]", etime-btime);
    return found;
}

int main(int argc, char *argv[]) {
    int threads, tid, rc, len;
    rc = MPI_Init(NULL, NULL);
    
    if(rc != MPI_SUCCESS) {
	printf("Error starting MPI program. Terminating\n");
	MPI_Abort(MPI_COMM_WORLD, rc);
    }
    
    MPI_Comm_size(MPI_COMM_WORLD, &threads);
    MPI_Comm_rank(MPI_COMM_WORLD, &tid);
    if(tid == 0) printf("\nSpellcheck!\n");
    if(argc < 4 || argc > 5) {
	if(tid == 0 || threads == 1) {
	    printf("\nusage           : spellcheck <WORD> <MAX CORRECTIONS> <THRESHOLD> <SERIAL[optional]>\n");
	    printf("<WORD>            : The word to be entered\n");
	    printf("<MAX CORRECTIONS> : The maximum number of word corrections\n");
	    printf("<THRESHOLD>       : The threshold percent (Lower %% means closer to original word)\n");
	    printf("<CORES>           : Number of processors to use\n");
	    printf("Note: Works best on 4 threads\n");
	    printf("\nExiting.\n");
	}
	MPI_Finalize();
	exit(0);
    } else {
	if(tid == 0 && argc == 5) {
	    if((strcasecmp(argv[4], "s") == 0)) {
		//SERIAL
		char word[64];
		int maxcorrect = atoi(argv[2]);
		int thresh = atoi(argv[3]);
		strcpy(word, argv[1]);
		find(word, maxcorrect, thresh);
		printf("\n\nDone serial!\n");
	    }
	} else if(argc == 4) {
	    //PARALLEL
	    if(threads > 3) {
		char word[64];
		int maxcorrect = atoi(argv[2]);
		int thresh = atoi(argv[3]);
		strcpy(word, argv[1]);
		double btime = MPI_Wtime();
		findp(word, maxcorrect, thresh, threads, tid);
		double etime = MPI_Wtime();
		if(tid == 0) printf("Time for PARALLEL : %f [sec]\n", etime-btime);
	    } else {
		if(tid == 0) printf("Sorry, I need at least 4 threads to run!\nExiting.\n");
		MPI_Finalize();
		exit(0);
	    }
	}
    }
    if(tid == 0) { printf("\n\nDone with project!\n"); }
    MPI_Finalize();
    return 0;
}