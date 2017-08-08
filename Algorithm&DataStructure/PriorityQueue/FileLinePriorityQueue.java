/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Memory Efficient Merging of Sorted Files (p3)
//FILE:             FileLinePriorityQueue.java
//
//Authors:
//Author1: (Name: Shaowen Liu, 	Email: sliu455@wisc.edu, 	
//NetID: sliu455, 		Lecture number: 002)
//Author2: (Name: Charles Ricchio, Email: cricchio@wisc.edu, 	
//NetID: cricchio, 		Lecture number: 002)
//Author3: (Name: Kevin Reed, 		Email: kreed6@wisc.edu, 	
//NetID: kreed6, 		Lecture number: 002)
//Author4: (Name: Rob Millikin,	Email: rmillikin@wisc.edu, 	
//NetID: rmillikin, 		Lecture number: 002)
//
//---------------- OTHER ASSISTANCE CREDITS 
//Persons: No
//
//Online sources: No
////////////////////////////80 columns wide //////////////////////////////////

import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation 
 * 		stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
	private int maxSize; // the max size of queue
	private int numItems; // number of elements in the queue
    private FileLine [] fLines; 
    private Comparator<FileLine> cmp;

    public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize; // why implement this instruction?
		numItems = 0;
		fLines = new FileLine[initialSize + 1]; // start from position 1
    }
    /**
     * Removes the minimum element from the Priority Queue, and returns it.
     *
     * @return the minimum element in the queue, according to the compareTo()
     * method of FileLine.
     * @throws PriorityQueueEmptyException if the priority queue has no elements
     * in it
     */
    public FileLine removeMin() throws PriorityQueueEmptyException {
//    	PriorityQueueEmptyException if the priority queue has no elements in it
    	if(this.isEmpty())
    		throw new PriorityQueueEmptyException(); // not caught yet
    	FileLine retLine = fLines[1];
    	int parent = 1;
    	int child = parent * 2; // compare two child and choose the smaller one
    	if(child <= numItems &&
				cmp.compare(fLines[child+1], fLines[child]) < 0)
    		child++;
    	
    	if (numItems > 1){
    		fLines[1] = fLines[numItems]; // replace first with last
    		while(child <= numItems && 
    				cmp.compare(fLines[parent], fLines[child]) > 0){
    			// swap them, small item go ahead
        		FileLine temp = fLines[child];
        		fLines[child] = fLines[parent];
        		fLines[parent] = temp;
        		// update index of child and parent
	    		parent = child;
	    		child = parent * 2; // default right is smaller, then compare
	    		// if left child is smaller
	    		if(child <= numItems &&
	    				cmp.compare(fLines[child+1], fLines[child]) < 0)
	        		child++;
    		}
    	}
    	
    	numItems--;
		return retLine;
    }
    /**
     * Inserts a FileLine into the queue, making sure to keep the shape and
     * order properties intact.
     *
     * @param fl the FileLine to insert
     * @throws PriorityQueueFullException if the priority queue is full.
     */
    public void insert(FileLine fl) throws PriorityQueueFullException {
    	// throw IllegalArgumentException if fl is null
    	 if(fl == null)
    		throw new IllegalArgumentException(); // not caught yet
//    	PriorityQueueFullException if the priority queue is full
    	if(numItems == maxSize)
    		throw new PriorityQueueFullException(); // not caught yet

    	// insert at the end, then numItems++
    	int child = numItems + 1; // child = insert postion
    	fLines[child] = fl;  
    	numItems++;

   		int parent = child/2; // integer division 
    	
    	//reheapify
    	while(parent > 0 && cmp.compare(fLines[parent], fLines[child]) > 0){
    		// swap them, small item go ahead
    		FileLine temp = fLines[child];
    		fLines[child] = fLines[parent];
    		fLines[parent] = temp;
    		// update index of child and parent	
    		child = parent;
    		parent = child/2;
    	}
    }
   
    /**
     * Checks if the queue is empty.
     *
     * @return true, if it is empty; false otherwise
     */
    public boolean isEmpty() {
		return numItems == 0;
    }
}
