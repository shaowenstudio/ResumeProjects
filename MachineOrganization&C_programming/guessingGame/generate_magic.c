#include <stdio.h>
#include <stdlib.h>

// Structure representing Square
// size: dimension(number of rows/columns) of the square
// array: 2D array of integers
typedef struct _Square {
        int size;
        int **array;
} Square;

Square magicSquare;//??????????????????????????????

int get_square_size();
Square * generate_magic(int size);
void write_to_file(Square * square, char *filename);

int main(int argc, char *argv[])
{
	Square *msptr;
        // Check input arguments to get filename
	int filesize = get_square_size();
	if (argc != 2){ 
		printf("Usage: ./generate_magic <filename>\n");
		exit(1);
	} 
	
		// Generate the magic square
	msptr = generate_magic(filesize);
	(*msptr).size = filesize;
	
        // Write the square to the output file
	write_to_file(msptr, argv[1]);
	// Free magic Square, since data is in the heap.
	int cols;
	for (cols = 0; cols < filesize; cols++) { //second layer allocate int size
		free(*((*msptr).array + cols));
	}
	free((*msptr).array); 
        return 0;
}

// get_square_size prompts the user for the magic square size
// checks if it is an odd number >= 3 and returns the number
int get_square_size()
{
	int size;
	printf("Enter size of magic square, must be odd \n");
	scanf("%d", &size);
	if (size < 3 || size%2 == 0){
		printf("Size must be an odd number >= 3. \n");
		exit(1);
	}
    return size;
}

// generate_magic constructs a magic square of size n
// using the Siamese algorithm and returns the Square struct
Square * generate_magic(int n)
{
	// Square magicSquare;
	Square *msp;
	msp = &magicSquare;
	int numOfElement = n*n;
	int i, j;
	// magic Square allocation
	(*msp).array = malloc(sizeof(int *) * n); // first layers, int *
	if ((*msp).array == NULL){
		printf("Malloc error. \n");
		exit(1);
	}
		
	for (i = 0; i < n; i++){
			*((*msp).array + i) = malloc(sizeof(int)* n); // second layers, int
			if (*((*msp).array + i) == NULL){
				printf("Malloc error. \n");
				exit(1);
			}
	}
	
	for (i = 0; i < n; i++) {	//assign all elements to 0
		for (j = 0; j < n; j++) {
			*(*((*msp).array + i) + j) = 0;
		}
	}

	// generate
	const int FIRST_ROW = 0;
	const int LAST_ROW = n-1;
	const int FIRST_COL = 0;
	const int LAST_COL = n-1;
	int rows = 0, cols = n/2 ; //middle element of the first row
	*(*((*msp).array + rows) + cols) = 1; //m[0][n/2] = 1 first element
	
	int count;
	for (count = 2; count <= numOfElement; count++){ //count number of element in the square
		if (rows == FIRST_ROW && cols != LAST_COL && *(*((*msp).array + LAST_ROW) + cols + 1) == 0){
			*(*((*msp).array + LAST_ROW) + cols + 1) = count;
			rows = LAST_ROW;
			cols = cols + 1;
		} else if(rows != FIRST_ROW && cols == LAST_COL && *(*((*msp).array + rows -1) + FIRST_COL) == 0){
			*(*((*msp).array + rows -1) + FIRST_COL) = count;
			rows = rows -1;
			cols = FIRST_COL;
		} else if (rows != FIRST_ROW && cols != LAST_COL && *(*((*msp).array + rows -1) + cols +1) != 0){
			*(*((*msp).array + rows + 1) + cols) = count;
			rows = rows + 1;
		} else if (rows == FIRST_ROW && cols == LAST_COL && *(*((*msp).array + LAST_ROW) + FIRST_COL) != 0){
			*(*((*msp).array + rows + 1) + cols) = count;
			rows = rows + 1;
		} else {
			*(*((*msp).array + rows - 1) + cols + 1) = count;
			--rows;
			++cols;
		}
	}

        return msp;
}

// write_to_file opens up a new file(or overwrites the existing file)
// and writes out the square in the format expected by verify_magic.c
void write_to_file(Square * square, char *filename) {
	int i, j;
	FILE *fp;
	
	fp = fopen(filename, "w+");
	
	fprintf(fp, "%d\n", (*square).size);
	
	for (i = 0; i < (*square).size; i++) {	
		for (j = 0; j < (*square).size; j++) {
			if (j == (*square).size -1){
				fprintf(fp, "%d", *(*((*square).array + i) + j));
			}	else {
				fprintf(fp, "%d,", *(*((*square).array + i) + j));
			}
		}
		fprintf(fp, "\n");
	}
	
	fclose(fp);
}
