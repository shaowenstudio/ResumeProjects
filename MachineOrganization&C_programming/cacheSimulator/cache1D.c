/*
 * Main File:        cache1D.c
 * Semester:         CS354-001 Fall 2016
 * 
 * Author:           Shaowen Liu
 * Email:            sliu455@wisc.edu
 * CS Login:         shaowen
 */
#include <stdio.h>

int intArray[100000];

/*
 * main - Main routine 
 */
int main(){
	int i = 0;
	
	for(i = 0; i < 100000; i++){
		intArray[i] = i;
	}
	
	return 0;
}