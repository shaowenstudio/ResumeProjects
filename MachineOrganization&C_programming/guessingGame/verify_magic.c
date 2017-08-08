#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE_LENGTH 50

// Structure representing Square
// size: dimension(number of rows/columns) of the square
// array: 2D array of integers
typedef struct _Square {
        int size;
        int **array;
} Square;

Square magicSquare;

Square * construct_square(char *filename);
int verify_magic(Square * square);

int main(int argc, char *argv[]) {
	// Check input arguments to get filename
	if (argc != 2){ 
		printf("Usage: ./generate_magic <filename>\n");
		exit(1);
	} 
	// Construct square
	Square *msptr;
	msptr = construct_square(argv[1]);	
		
    // Verify if it's a magic square and print true or false
	int check = verify_magic(msptr);
	if (!check){
		printf("true\n");
	}	else {
		printf("false\n");
	}
		
	// Free magic Square, since data is in the heap, is it wiped out after execution? why should we do it?
	int cols;
	for (cols = 0; cols < (*msptr).size; cols++) { //second layer allocate int size
		free(*((*msptr).array + cols));
	}
	free((*msptr).array); 

    return 0;
		
}

// construct_square reads the input file to initialize a square struct
// from the contents of the file and returns the square.
// The format of the file is defined in the assignment specifications
Square * construct_square(char *filename)
{
	// Square magicSquare;
	Square *msp;
	msp = &magicSquare;
	// Open and read the file
	FILE * ifp;
	
	ifp = fopen(filename, "r");
	if (ifp == NULL){
		fprintf(stderr, "Can't open input file %s\n", filename);
		exit(1);
	}
    // Read the first line to get the square size
	int iSize;
	fscanf(ifp, "%d", &iSize);
	
    // Initialize a new Square struct of that size
	(*msp).size = iSize;
	
    // Read the rest of the file to fill up the square
	char *token; //tokenizer
	char line[MAX_LINE_LENGTH]; //why does "char *line" not work??????
	
	(*msp).array = malloc(sizeof(int *) * (*msp).size); //first layer allocate int * size
	if ((*msp).array == NULL){
		printf("Malloc error. \n");
		exit(1);
	}
	
	int cols, rows;
	for (cols = 0; cols < (*msp).size; cols++) { //second layer allocate int size
		*((*msp).array + cols) = malloc(sizeof(int) * (*msp).size);
		if (*((*msp).array + cols) == NULL){
			printf("Malloc error. \n");
			exit(1);
		}
	}
	
	for (rows = 0; rows < (*msp).size; rows++) {
		fscanf(ifp, "%s", line); // read line
		
		token = strtok(line, ","); // first token， then go through
		for (cols = 0; cols < (*msp).size; cols++) {
			*(*((*msp).array + rows) + cols) = atoi(token);
			token = strtok(NULL, ",");//其实是第一次就token完了，这个只是为了一步一步输出, 但为什么呢
		}
	}
	
	fclose(ifp);
	return msp;
}

// verify_magic verifies if the square is a magic square
// returns 1(true) or 0(false)
int verify_magic(Square * square)
{
    int standard = 0;
	int currentNum = 0;
	int cols = 0, rows = 0;
	
	for (cols = 0; cols < (*square).size; cols++){
		currentNum += *(*((*square).array + 0) + cols); // add numbers of the first row
	}
	standard = currentNum;
	
		// Check all rows sum to same number
	for (rows = 0; rows < (*square).size; rows++){  
		currentNum = 0;
		for (cols = 0; cols < (*square).size; cols++){
			currentNum += *(*((*square).array + rows) + cols); //current row addition
		}
		if (standard != currentNum){
			return 1;
		}
	}
        // Check all cols sum to same number
	for (cols = 0; cols < (*square).size; cols++){ 
		currentNum = 0;
		for (rows = 0; rows < (*square).size; rows++){
			currentNum += *(*((*square).array + rows) + cols); //current col addition 
		}
		if (standard != currentNum){
			return 1;
		} 
	}
        // Check main diagonal
	currentNum = 0;
	int i;
	for (i = 0; i < (*square).size; i++){
		currentNum += *(*((*square).array + i) + i); //main diagonal addition 
	}
	if (standard != currentNum){
		return 1;
	}
	
        // Check secondary diagonal
	currentNum = 0;
	int colNum = (*square).size - 1;
	int rowNum;
	for (rowNum = 0; rowNum < (*square).size; rowNum++){
		currentNum += *(*((*square).array + rowNum) + colNum); //secondary diagonal addition
	}
	if (standard != currentNum){
		return 1;
	} 
	
    return 0;
}
