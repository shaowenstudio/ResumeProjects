/*
 * Main File:        cache2Drows.c
 * Semester:         CS354-001 Fall 2016
 * 
 * Author:           Shaowen Liu
 * Email:            sliu455@wisc.edu
 * CS Login:         shaowen
 */
 
#include <stdio.h>

int intArray[3000][500];

/*
 * main - Main routine 
 */
int main(){
	int row, col;
	
	for(row = 0; row < 3000; row++){
		for (col = 0; col < 500; col++){
			intArray[row][col] = row + col;
		}
	}
	
	return 0;
}