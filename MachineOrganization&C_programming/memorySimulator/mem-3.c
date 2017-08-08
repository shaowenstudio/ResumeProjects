
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <string.h>
#include "mem.h"

/* this structure serves as the header for each block */
typedef struct block_hd{
	/* The blocks are maintained as a linked list */
	/* The blocks are ordered in the increasing order of addresses */
	struct block_hd* next;

	/* size of the block is always a multiple of 4 */
	/* ie, last two bits are always zero - can be used to store other information*/
	/* LSB = 0 => free block */
	/* LSB = 1 => allocated/busy block */

	/* So for a free block, the value stored in size_status will be the same as the block size*/
	/* And for an allocated block, the value stored in size_status will be one more than the block size*/

	/* The value stored here does not include the space required to store the header */

	/* Example: */
	/* For a block with a payload of 24 bytes (ie, 24 bytes data + an additional 8 bytes for header) */
	/* If the block is allocated, size_status should be set to 25, not 24!, not 23! not 32! not 33!, not 31! */
	/* If the block is free, size_status should be set to 24, not 25!, not 23! not 32! not 33!, not 31! */
	int size_status;

}block_header;

/* Global variable - This will always point to the first block */
/* ie, the block with the lowest address */
block_header* list_head = NULL;


/* Function used to Initialize the memory allocator */
/* Not intended to be called more than once by a program */
/* Argument - sizeOfRegion: Specifies the size of the chunk which needs to be allocated */
/* Returns 0 on success and -1 on failure */
int Mem_Init(int sizeOfRegion)
{
	int pagesize;
	int padsize;
	int fd;
	int alloc_size;
	void* space_ptr;
	static int allocated_once = 0;

	if(0 != allocated_once)
	{
		fprintf(stderr,"Error:mem.c: Mem_Init has allocated space during a previous call\n");
		return -1;
	}
	if(sizeOfRegion <= 0)
	{
		fprintf(stderr,"Error:mem.c: Requested block size is not positive\n");
		return -1;
	}

	/* Get the pagesize */
	pagesize = getpagesize();

	/* Calculate padsize as the padding required to round up sizeOfRegio to a multiple of pagesize */
	padsize = sizeOfRegion % pagesize;
	padsize = (pagesize - padsize) % pagesize;

	alloc_size = sizeOfRegion + padsize;

	/* Using mmap to allocate memory */
	fd = open("/dev/zero", O_RDWR);
	if(-1 == fd)
	{
		fprintf(stderr,"Error:mem.c: Cannot open /dev/zero\n");
		return -1;
	}
	space_ptr = mmap(NULL, alloc_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
	if (MAP_FAILED == space_ptr)
	{
		fprintf(stderr,"Error:mem.c: mmap cannot allocate space\n");
		allocated_once = 0;
		return -1;
	}

	allocated_once = 1;

	/* To begin with, there is only one big, free block */
	list_head = (block_header*)space_ptr;
	list_head->next = NULL;
	/* Remember that the 'size' stored in block size excludes the space for the header */
	list_head->size_status = alloc_size - (int)sizeof(block_header);

	return 0;
}


/* Function for allocating 'size' bytes. */
/* Returns address of allocated block on success */
/* Returns NULL on failure */
/* Here is what this function should accomplish */
/* - Check for sanity of size - Return NULL when appropriate */
/* - Round up size to a multiple of 4 */
/* - Traverse the list of blocks and allocate the best free block which can accommodate the requested size */
/* -- Also, when allocating a block - split it into two blocks when possible */
/* Tips: Be careful with pointer arithmetic */
void* Mem_Alloc(int size)
{
	
	void* new_block_ptr = NULL;
	block_header* current = NULL;
	block_header* new_block = NULL;
	int paddingsize = 0;
	int alloc_size = 0;
	const int PAGE_SIZE = 4;
	const int MIN_BLOCK_SIZE = PAGE_SIZE + sizeof(block_header);
	char* block_pointer = NULL;
	int split_remaining_size = 0;
	// Check size and round it up to a multiple of 4
	if(size <= 0){
		return NULL;
	} 
	
	paddingsize = size % PAGE_SIZE;
	paddingsize = (PAGE_SIZE - paddingsize) % PAGE_SIZE;
	alloc_size = size + paddingsize;
	//check whether the free blocks in the list are big enough, or whether the last is big enough
	int enough_space = 0;
	current = list_head;
	while(NULL != current->next){ //is free and size enough
		current = current->next;	//find the next allocated block before a free block
		if (!(current->size_status & 1) && current->size_status >= alloc_size){ // have and big enough
			enough_space = 1;
		}
	}
	if(!enough_space && list_head->size_status < alloc_size)
		return NULL;
	
	// Search for the best fit block in the free list 
	block_header* best_fit = NULL;
	
	current = list_head; 
	while (NULL != current){
		while(!(current->size_status & 1) && current->size_status >= alloc_size){ //is free and size enough
			if (NULL == list_head->next){ //APPEND; empty list
				new_block_ptr = (char*)list_head + (int)sizeof(block_header); // Jump over list_head
				new_block = (block_header*)new_block_ptr; //current point the start of the new block in the space
				list_head->next = new_block;
				best_fit = new_block; 
				best_fit->size_status = alloc_size + 1; // Mark (change status) the allocated block and return it
				best_fit->next = NULL;
				block_pointer = (char*)best_fit;
				list_head->size_status -= (alloc_size + (int)sizeof(block_header));
				return block_pointer;
			} 
			else if (alloc_size == current->size_status){ // in list; exactly equal 
				best_fit = current; 
				best_fit->size_status = alloc_size + 1; // Mark (change status) the allocated block and return it
				block_pointer = (char*)best_fit;
				list_head->size_status -= alloc_size;
				return block_pointer;
			} 
			else if (NULL == best_fit){ //assign an initial value
				best_fit = current; 
				break;
			} 
			else if (best_fit->size_status - current->size_status > 0){ //INSERT; current is better than present size
				best_fit = current;	//then update best_fit	
			}
			
			if (NULL == current->next){ //if true, jump out of the loop
				break;
			} else {
				current = current->next;	//find the next allocated block before the next free block
			}
		}
		current = current->next;
	}
	
	if (best_fit == list_head){ //not suitable free block IN the list
		//situation 2: Append
		current = list_head;
		while(NULL != current->next){ 
			current = current->next;	//find the last allocated block
		}
		
		new_block_ptr = (char*)current + (int)sizeof(block_header) + current->size_status - 1;//non-empty list allocation
		new_block = (block_header*)new_block_ptr; //current point the start of the new block in the space
		current->next = new_block;	
		new_block->size_status = alloc_size + 1; // Mark (change status) the allocated block and return it
		new_block->next = NULL;
		block_pointer = (char*)new_block;
		list_head->size_status -= alloc_size + (int)sizeof(block_header);
		return block_pointer;
	} 
	
	//situation: insert. split and not
	// If a block is found, check to see if we can split it, i.e it has space leftover for a new block(header + payload)
	split_remaining_size = best_fit->size_status - (sizeof(block_header) + alloc_size);
	if (split_remaining_size >= MIN_BLOCK_SIZE){
		// If split, update the size of the resulting blocks
		new_block_ptr = (char*)best_fit + (int)sizeof(block_header) + alloc_size;
		new_block = (block_header*)new_block_ptr;		
		new_block->next = best_fit->next; // make a successful link
		best_fit->next = new_block;
		new_block->size_status = split_remaining_size - (int)sizeof(block_header);
		
		best_fit->size_status = alloc_size + 1;

		block_pointer = (char*)best_fit;
		
		list_head->size_status -= alloc_size + (int)sizeof(block_header);
		return block_pointer;	
	} 
	else{ // not split
		// Mark the allocated block and return it 
		best_fit->size_status += 1; //if not split, no need to update size_status
		block_pointer = (char*)best_fit;
		list_head->size_status -= alloc_size;
		return block_pointer;	
	}
	
	return NULL;
}

/* Function for freeing up a previously allocated block */
/* Argument - ptr: Address of the block to be freed up */
/* Returns 0 on success */
/* Returns -1 on failure */
/* Here is what this function should accomplish */
/* - Return -1 if ptr is NULL */
/* - Return -1 if ptr is not pointing to the first byte of a busy block */
/* - Mark the block as free */
/* - Coalesce if one or both of the immediate neighbours are free */
int Mem_Free(void *ptr)
{
	block_header* current = NULL;
	block_header* former = NULL;
	int free = 0;
	// Check if the pointer is pointing to the start of the payload of an allocated block
	current = list_head;
	while (NULL != current->next){
		former = current;
		current = current->next;
		if ((char*)current == ptr && (current->size_status & 1)){
			free = 1;
			break;
		}
	}
	// If so, free it.
	if (free){
		// Check the blocks to the left and right to see if the block can be coalesced
		// with either or both of them
		if (current->next != NULL){
			if (former == list_head){ //former head
				if ((current->next)->size_status & 1){ // next busy
					current->size_status -= 1;
					list_head->size_status += current->size_status - 4;
				}
				else {// next free
					list_head->size_status += current->size_status - 1 - sizeof(block_header);
					current->next = (current->next)->next;
					current->size_status += ((current->next)->size_status + sizeof(block_header));
					
				}
			}
			else if ((former->size_status & 1) && !((current->next)->size_status & 1)){ //former busy, next free
				list_head->size_status += current->size_status - 1 - sizeof(block_header); // update remaining list size
				current->next = (current->next)->next;
				current->size_status += ((current->next)->size_status + sizeof(block_header));
			}
			else if (!(former->size_status & 1) && ((current->next)->size_status & 1)){ //former free, next busy
				list_head->size_status += current->size_status - 1 - sizeof(block_header);
				former->next = current->next;
				former->size_status += (current->size_status - 1 + sizeof(block_header));
			}
			else if (!(former->size_status & 1) && !((current->next)->size_status & 1)){ //former free, next free
				list_head->size_status += current->size_status - 1 - sizeof(block_header) + 4;
				int current_size = current->size_status - 1 + sizeof(block_header);
				int third_size = (current->next)->size_status + sizeof(block_header);
				former->next = (current->next)->next;
				former->size_status += (current_size + third_size);
			}
			else { //former busy, next busy
				list_head->size_status += current->size_status - 1 - sizeof(block_header);
				current->size_status -= 1;
			}
		}
		else {//current->next = NULL
			list_head->size_status += current->size_status - 1 - sizeof(block_header);
			former->next = NULL;
		}
		return 0;
	}
	
	return -1;
}

/* Function to be used for debug */
/* Prints out a list of all the blocks along with the following information for each block */
/* No.      : Serial number of the block */
/* Status   : free/busy */
/* Begin    : Address of the first useful byte in the block */
/* End      : Address of the last byte in the block */
/* Size     : Size of the block (excluding the header) */
/* t_Size   : Size of the block (including the header) */
/* t_Begin  : Address of the first byte in the block (this is where the header starts) */
void Mem_Dump()
{
	int counter;
	block_header* current = NULL;
	char* t_Begin = NULL;
	char* Begin = NULL;
	int Size;
	int t_Size;
	char* End = NULL;
	int free_size;
	int busy_size;
	int total_size;
	char status[5];

	free_size = 0;
	busy_size = 0;
	total_size = 0;
	current = list_head;
	counter = 1;
	fprintf(stdout,"************************************Block list***********************************\n");
	fprintf(stdout,"No.\tStatus\tBegin\t\tEnd\t\tSize\tt_Size\tt_Begin\n");
	fprintf(stdout,"---------------------------------------------------------------------------------\n");
	while(NULL != current)
	{
		t_Begin = (char*)current;
		Begin = t_Begin + (int)sizeof(block_header);
		Size = current->size_status;
		strcpy(status,"Free");
		if(Size & 1) /*LSB = 1 => busy block*/
		{
			strcpy(status,"Busy");
			Size = Size - 1; /*Minus one for ignoring status in busy block*/
			t_Size = Size + (int)sizeof(block_header);
			busy_size = busy_size + t_Size;
		}
		else
		{
			t_Size = Size + (int)sizeof(block_header);
			free_size = free_size + t_Size;
		}
		End = Begin + Size;
		fprintf(stdout,"%d\t%s\t0x%08lx\t0x%08lx\t%d\t%d\t0x%08lx\n",counter,status,(unsigned long int)Begin,(unsigned long int)End,Size,t_Size,(unsigned long int)t_Begin);
		total_size = total_size + t_Size;
		current = current->next;
		counter = counter + 1;
	}
	fprintf(stdout,"---------------------------------------------------------------------------------\n");
	fprintf(stdout,"*********************************************************************************\n");

	fprintf(stdout,"Total busy size = %d\n",busy_size);
	fprintf(stdout,"Total free size = %d\n",free_size);
	fprintf(stdout,"Total size = %d\n",busy_size+free_size);
	fprintf(stdout,"*********************************************************************************\n");
	fflush(stdout);
	return;
}
