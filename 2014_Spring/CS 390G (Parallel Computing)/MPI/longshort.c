/*
 * Longest shortest word in words
 */

#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>
#include <strings.h>
#include <string.h>

int main(int argc, char *argv[]) {
    char line[64];
    char maxword[64];
    char minword[64];
    FILE *dict = fopen("words", "rt");
    if(dict == NULL) {
	printf("Error\n");
	exit(1);
    } else {
	printf("Dict loaded\n");
	while(fscanf(dict, "%s", line) != EOF) {
	    if(strlen(line) > strlen(maxword)) {strcpy(maxword, line);}
	    if(strlen(line) < strlen(minword)) {strcpy(minword, line);}
	}
    }
    
    printf("Maxword = %s (%d)\n", maxword, strlen(maxword));
    printf("Minword = %s (%d)\n", minword, strlen(minword));
    return 0;
}