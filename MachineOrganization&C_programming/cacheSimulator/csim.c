/* Name: Shaowen Liu
 * CS login: Shaowen
 * Section: 001
 * 
 * Main File:        csim.c
 * Semester:         CS354-001 Fall 2016
 * Email:            sliu455@wisc.edu
 * 
 *
 * csim.c - A cache simulator that can replay traces from Valgrind
 *     and output statistics such as number of hits, misses, and
 *     evictions.  The replacement policy is LRU.
 *
 * Implementation and assumptions:
 *  1. Each load/store can cause at most one cache miss. (I examined the trace,
 *  the largest request I saw was for 8 bytes).
 *  2. Instruction loads (I) are ignored, since we are interested in evaluating
 *  trans.c in terms of its data cache performance.
 *  3. data modify (M) is treated as a load followed by a store to the same
 *  address. Hence, an M operation can result in two cache hits, or a miss and a
 *  hit plus an possible eviction.
 *
 * The function printSummary() is given to print output.
 * Please use this function to print the number of hits, misses and evictions.
 * This is crucial for the driver to evaluate your work. 
 */
#include <getopt.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>
#include <limits.h>
#include <string.h>
#include <errno.h>
#include<stdbool.h>

#include "cachelab.h"

// #define DEBUG_ON 
#define ADDRESS_LENGTH 64

/****************************************************************************/
/***** DO NOT MODIFY THESE VARIABLE NAMES ***********************************/

/* Globals set by command line args */
int verbosity = 0; /* print trace if set */
int s = 0; /* set index bits */
int b = 0; /* block offset bits */
int E = 0; /* associativity */
char* trace_file = NULL;

/* Derived from command line args */
int S; /* number of sets S = 2^s In C, you can use "pow" function*/
int B; /* block size (bytes) B = 2^b In C, you can use "pow" function*/

/* Counters used to record cache statistics */
int miss_count = 0;
int hit_count = 0;
int eviction_count = 0;
/*****************************************************************************/


/* Type: Memory address 
 * Use this type whenever dealing with addresses or address masks
  */
typedef unsigned long long int mem_addr_t;

/* Type: Cache line
 * TODO 
 * 
 * NOTE: 
 * You will need to add an extra field to this struct
 * depending on your implementation of the LRU policy for evicting a cache line
 * 
 * For example, to use a linked list based LRU,
 * you might want to have a field "struct cache_line * next" in the struct 
 */
typedef struct cache_line {
	int counter;
    char valid;
    mem_addr_t tag;
} cache_line_t;

typedef cache_line_t* cache_set_t;
typedef cache_set_t* cache_t;


/* The cache we are simulating */
cache_t cache;  

/* TODO - COMPLETE THIS FUNCTION
 * initCache - 
 * Allocate data structures to hold info regrading the sets and cache lines
 * use struct "cache_line_t" here
 * Initialize valid and tag field with 0s.
 * use S (= 2^s) and E while allocating the data structures here
 */
void initCache()
{
	int i = 0;
	int j = 0;
	
	S = (int)pow((double)2 ,(double)s);
	B = (int)pow((double)2 ,(double)b);

	cache = malloc(sizeof(cache_line_t*) * S); //first layer, creat set
	if (cache == NULL){
		printf("Malloc error. \n");
		exit(1);
	}
	for (i = 0; i < S; i++){ //second layer, include line
		*(cache + i) =  malloc(sizeof(cache_line_t) * E);
		if (*(cache + i) == NULL){
			printf("Malloc error. \n");
			exit(1);
		}
	}
	
	for (i = 0; i < S; i++){ //create the cache
		for (j = 0; j < E; j++){
			cache_line_t new_line;
			new_line.valid = 0;
			new_line.tag = 0;
			new_line.counter = 0;
			
			*(*(cache + i) + j) = new_line;
		}
	}

}

/* TODO - COMPLETE THIS FUNCTION 
 * freeCache - free each piece of memory you allocated using malloc 
 * inside initCache() function
 */
void freeCache()
{
	//free() memory in initCache
	int i;
	for (i = 0; i < S; i++){ 
		free(*(cache + i)) ;
	}
	free(cache);
}


/* TODO - COMPLETE THIS FUNCTION 
 * accessData - Access data at memory address addr.
 *   If it is already in cache, increase hit_count
 *   If it is not in cache, bring it in cache, increase miss count.
 *   Also increase eviction_count if a line is evicted.
 *   you will manipulate data structures allocated in initCache() here
 */
void accessData(mem_addr_t addr)
{
	int i = 0;
	int j = 0;
	
	char *s_addr = malloc(8);
	char *addr_binary = calloc(4, 16);
	
	//convert addr to string
	sprintf(s_addr, "%llx", addr); 
	int len = strlen(s_addr);
	
	// convert hex to binary
	for (i = 0; i < len; i++ ){ 
		char num = *(s_addr + len - 1 - i);
		switch(num)
        {
            case '0':
				*(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '1':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '2':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '3':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '4':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '5':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '6':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '7':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 0;
                break;
            case '8':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case '9':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case 'a':
            case 'A':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case 'b':
            case 'B':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 0;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case 'c':
            case 'C':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case 'd':
            case 'D':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 0;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case 'e':
            case 'E':
                *(addr_binary + 63 - 4*i - 0) = 0;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            case 'f':
            case 'F':
                *(addr_binary + 63 - 4*i - 0) = 1;
				*(addr_binary + 63 - 4*i - 1) = 1;
				*(addr_binary + 63 - 4*i - 2) = 1;
				*(addr_binary + 63 - 4*i - 3) = 1;
                break;
            default:
                printf("Invalid hexadecimal input.\n");
        }
	}
	
	//extract set and tag, check v
	int read_offset = 0;
	int read_set = 0;
	int read_tag = 0;
	int current_value = 0; 
	
	for (i = 0; i < b; i++){
		int the_bit = *(addr_binary + 63 - i);
		current_value = the_bit * (int)pow((double)2, (double)(i));
		read_offset += current_value;
	}
	
	for (i = b; i < b + s; i++){
		int the_bit = *(addr_binary + 63 - i);
		current_value = the_bit * (int)pow((double)2, (double)(i-b));
		read_set += current_value;
	} 
	
	for (i = b+s; i < 63-s-b; i++){
		int the_bit = *(addr_binary + 63 - i);
		current_value = the_bit * (int)pow((double)2, (double)(i-b-s));
		read_tag += current_value;
	}
	
	//find that line and update information (hit, miss, eviction, v)
	if (read_set > S){
		printf("No such set. \n");
		exit(1);
	}
	
	//find max_counter in the cache
	int max_counter = 0;
	for (i = 0; i < S; i++){
		for (j = 0; j < E; j++){
			if ((*(*(cache + i) + j)).counter > max_counter)
				max_counter = (*(*(cache + i) + j)).counter;
		}
	}
	
	//LRU
	int find = 0; //!find = 1
	for (i = 0; i < E && find == 0; i++){ //loop lines in that set
		if ((*(*(cache + read_set) + i)).tag == read_tag){ //tag matches
			if((*(*(cache + read_set) + i)).valid == 1){ // valid bit matches
				find = 1;
				hit_count++;
				(*(*(cache + read_set) + i)).counter = max_counter + 1;
			}
		} //no such tag, not find
		
	}
	int evict = 1;
	int set_is_full = 0; //!set_is_full = 1
	if (!find){ // not find that tag in the set or valid bit = 0 
		for (i = 0; i < E && set_is_full== 0; i++){
			if((*(*(cache + read_set) + i)).valid == 0){ //set is not full
				miss_count++;
				// load that line;
				(*(*(cache + read_set) + i)).valid = 1;
				(*(*(cache + read_set) + i)).tag = read_tag;
				(*(*(cache + read_set) + i)).counter = max_counter + 1;
				evict = 0;
				break;
			} 
		}
		if(evict){ //set is full
				eviction_count++;
				miss_count++;
				
				int least_recent_use_index = 0;
				int least_counter = (*(*(cache + read_set) + 0)).counter;
				for (i = 0; i < E; i++){ //loop lines to find the least recent use one and replace
					if ((*(*(cache + read_set) + i)).counter < least_counter){
						least_counter = (*(*(cache + read_set) + i)).counter;
						least_recent_use_index = i;
					}
				}
				// load that line;
				(*(*(cache + read_set) + least_recent_use_index)).valid = 1;
				(*(*(cache + read_set) + least_recent_use_index)).tag = read_tag;
				(*(*(cache + read_set) + least_recent_use_index)).counter = max_counter + 1;
		}
	}
	
	
}


/* TODO - FILL IN THE MISSING CODE
 * replayTrace - replays the given trace file against the cache 
 * reads the input trace file line by line
 * extracts the type of each memory access : L/S/M
 * YOU MUST TRANSLATE one "L" as a load i.e. 1 memory access
 * YOU MUST TRANSLATE one "S" as a store i.e. 1 memory access
 * YOU MUST TRANSLATE one "M" as a load followed by a store i.e. 2 memory accesses 
 */
void replayTrace(char* trace_fn)
{
    char buf[1000];
    mem_addr_t addr=0;
    unsigned int len=0;
    FILE* trace_fp = fopen(trace_fn, "r");
	
    if(!trace_fp){
        fprintf(stderr, "%s: %s\n", trace_fn, strerror(errno));
        exit(1);
    }

    while( fgets(buf, 1000, trace_fp) != NULL) {
        if(buf[1]=='S' || buf[1]=='L' || buf[1]=='M') {
            sscanf(buf+3, "%llx,%u", &addr, &len);
      
            if(verbosity)
                printf("%c %llx,%u ", buf[1], addr, len);

           // TODO - MISSING CODE
           // now you have: 
           // 1. address accessed in variable - addr 
           // 2. type of acccess(S/L/M)  in variable - buf[1] 
           // call accessData function here depending on type of access
			if (buf[1]=='S' || buf[1]=='L')
				accessData(addr);
			else{
				accessData(addr);
				accessData(addr);
			}
			
            if (verbosity)
                printf("\n");
        }
    }

    fclose(trace_fp);
}

/*
 * printUsage - Print usage info
 */
void printUsage(char* argv[])
{
    printf("Usage: %s [-hv] -s <num> -E <num> -b <num> -t <file>\n", argv[0]);
    printf("Options:\n");
    printf("  -h         Print this help message.\n");
    printf("  -v         Optional verbose flag.\n");
    printf("  -s <num>   Number of set index bits.\n");
    printf("  -E <num>   Number of lines per set.\n");
    printf("  -b <num>   Number of block offset bits.\n");
    printf("  -t <file>  Trace file.\n");
    printf("\nExamples:\n");
    printf("  linux>  %s -s 4 -E 1 -b 4 -t traces/yi.trace\n", argv[0]);
    printf("  linux>  %s -v -s 8 -E 2 -b 4 -t traces/yi.trace\n", argv[0]);
    exit(0);
}

/*
 * main - Main routine 
 */
int main(int argc, char* argv[])
{
    char c;
    
    // Parse the command line arguments: -h, -v, -s, -E, -b, -t 
    while( (c=getopt(argc,argv,"s:E:b:t:vh")) != -1){
        switch(c){
        case 's':
            s = atoi(optarg);
            break;
        case 'E':
            E = atoi(optarg);
            break;
        case 'b':
            b = atoi(optarg);
            break;
        case 't':
            trace_file = optarg;
            break;
        case 'v':
            verbosity = 1;
            break;
        case 'h':
            printUsage(argv);
            exit(0);
        default:
            printUsage(argv);
            exit(1);
        }
    }

    /* Make sure that all required command line args were specified */
    if (s == 0 || E == 0 || b == 0 || trace_file == NULL) {
        printf("%s: Missing required command line argument\n", argv[0]);
        printUsage(argv);
        exit(1);
    }


    /* Initialize cache */
    initCache();

#ifdef DEBUG_ON
    printf("DEBUG: S:%u E:%u B:%u trace:%s\n", S, E, B, trace_file);
#endif
 
    replayTrace(trace_file);

    /* Free allocated memory */
    freeCache();

    /* Output the hit and miss statistics for the autograder */
    printSummary(hit_count, miss_count, eviction_count);
    return 0;
}
