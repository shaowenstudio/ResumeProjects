/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Navigation App using Graphs (p5)
//FILE:             PriorityQueue_P5.java
//
//Authors:
//Author1: (Name: Shaowen Liu, 	Email: sliu455@wisc.edu, 	
//NetID: sliu455, 		Lecture number: 002)
//Author2: (Name: Yuqi Wei, Email: wei56@wisc.edu, 	
//NetID: wei56, 		Lecture number: 001)
//Author3: (Name: Shiwei Cao, Email: scao46@wisc.edu, 	
//NetID: scao46, 		Lecture number: 001)
//Author4: (Name: John Zhang ,	Email: hzhang473@wisc.edu, 	
//NetID: hzhang473, 	Lecture number: 003)
//
//---------------- OTHER ASSISTANCE CREDITS 
//Persons: No
//
//Online sources: No
////////////////////////////80 columns wide //////////////////////////////////

/**
 * This class defines the PriorityQueue_P5 for the project
 * It will be used while constructing the graph.
 *
 * @author Liu, Wei, Cao, John
 */
class PriorityQueue_P5 {
	int size;
	int numItems;
	CombineNode [] cNodes;
	
	/**
	 * Constructor which constructs a priority queue
	 * 
	 * @param size
	 *            size of the priority queue
	 */
	PriorityQueue_P5(int size){
		this.size = size;
		numItems = 0;
		cNodes = new CombineNode[size+100];
	}
	
	/**
	 * Remove minimum value in the queue
	 * 
	 * @throws IndexOutOfBoundsException
	 * 
	 * @return Remove minimum value in the queue
	 */
	CombineNode removeMin(){
		if(this.isEmpty())
    		throw new IndexOutOfBoundsException();
		
		CombineNode retNode = cNodes[1];
    	int parent = 1;
    	int child = parent * 2; // compare two child and choose the smaller one
    	if(child < numItems &&	
    			cNodes[child+1].getWeight() < cNodes[child].getWeight())
    		child++;
    	
    	if (numItems > 1){
    		cNodes[1] = cNodes[numItems]; // replace first with last
    		while(child < numItems && 
    				cNodes[child+1].getWeight() > cNodes[child].getWeight()){
    			// swap them, small item go ahead
    			CombineNode temp = cNodes[child];
    			cNodes[child] = cNodes[parent];
    			cNodes[parent] = temp;
        		// update index of child and parent
	    		parent = child;
	    		child = parent * 2; // default right is smaller, then compare
	    		// if left child is smaller
	    		if(child < numItems &&
	        		   cNodes[child+1].getWeight() < cNodes[child].getWeight())
	        		child++; // questions about the if condition
    		}
    	}
    	
		numItems--;
		
		return retNode;
	}
	
	/**
	 * Insert the item in the priority queue 
	 * 
	 * @param edge
	 *            edge between src and dest
	 *            
	 * @return List of vertices of type V
	 */
	void insert(CombineNode cbN){
		if(cbN == null)
    		throw new IllegalArgumentException();
		if(this.size == numItems)
			System.out.println("PQ is full");
		
		int childIndex = numItems + 1; // insert postion
		cNodes[childIndex] = cbN;
		numItems++;
		
		int parentIndex = childIndex/2; // integer division 
    	
    	//reheapify
    	while(parentIndex > 0 && 
    		cNodes[parentIndex].getWeight() > cNodes[childIndex].getWeight()){
    		
    		// swap them, small item go ahead
    		CombineNode temp = cNodes[childIndex];
    		cNodes[childIndex] = cNodes[parentIndex];
    		cNodes[parentIndex] = temp;
    		// update index of child and parent	
    		childIndex = parentIndex;
    		parentIndex = childIndex/2;
    	}
		
	}
	
	/**
	 * @return true is numItems is 0
	 */
	boolean isEmpty(){
		return numItems == 0;
	}
	
	/**
	 * check whether node is in the pq
	 * 
	 * @param node
	 *            node to check
	 *            
	 * @return true is the node is in the pq
	 */
	boolean contains(CombineNode node){
		boolean contains = false;
		for (int i = 1; i <= numItems; i++) {
			if(cNodes[i].getCurrentNode().getVertexData().equals(
					node.getCurrentNode().getVertexData()))
				contains = true;
		}
		return contains;
	}
	
}
