package p4;
/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Schedule Planner (p4)
//FILE:             IntervalTree.java
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
import java.util.ArrayList;
import java.util.List;

/**
 * defines the structure of an IntervalTree type. It supports modifying and 
 * inserting intervals as data items into nodes of this tree
 *
 */
public class IntervalTree<T extends Comparable<T>> 
								implements IntervalTreeADT<T> {
	private IntervalNode<T> root;
	
	public IntervalTree(){
		this.root = null;
	}
	/**
	 * A constructor that constructs a tree the given node as its root node.
	 *  
	 * @param lt - IntervalNode<T>.
	 * @param gt - T.
	 * @param root - IntervalNode<T>.          
	 */
	public IntervalTree(IntervalNode<T> lt, T gt, IntervalNode<T> root){
		this.root = root; 
	}

	/** @return the root node of this IntervalTree. */
	public IntervalNode<T> getRoot() {
		return root;
	}

	/**
	 * Inserts an Interval in the tree.
	 *  
	 * @param interval the interval (item) to insert in the tree.
	 * @throws IllegalArgumentException if interval is null or is found 
	 * to be a duplicate of an existing interval in this tree.            
	 */
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		if( contains(interval) ) // find duplicate
			throw new IllegalArgumentException();

		root = insertHelper(interval, root);
	}
	/**
	 * A recursive helper method for insert
	 * @param interval - IntervalADT<T>
	 * @param node - IntervalADT<T>
	 */
	private IntervalNode<T> insertHelper(IntervalADT<T> interval, 
						IntervalNode<T> node){
		if(node == null){
			return new IntervalNode<T>(interval);
		}
		
		if(interval.compareTo(node.getInterval()) > 0){
			if(interval.getEnd().compareTo( node.getMaxEnd() ) > 0) 
				// update maxEnd
				node.setMaxEnd(interval.getEnd());
			node.setRightNode( insertHelper(interval, node.getRightNode()) );
			return node;
		}
		else{ // interval.compareTo(node.getInterval()) < 0
			if(interval.getEnd().compareTo( node.getMaxEnd() ) > 0) 
				// update maxEnd
				node.setMaxEnd(interval.getEnd());
			node.setLeftNode( insertHelper(interval, node.getLeftNode()) );
			return node;
		}
	}

	/**
	 * Delete the node containing the specified interval in the tree.
	 * Delete operations must also update the maxEnd of interval nodes
	 * that change as a result of deletion.  
	 *  
	 * <p>Tip: call <code>deleteHelper(root)</code> with the root node.</p>
	 * 
	 * @throws IllegalArgumentException if interval is null
	 * @throws IntervalNotFoundException if the interval does not exist.
	 */
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException{
		// TODO 
		if(interval == null)
			throw new IllegalArgumentException();
		if(!contains(interval))
			throw new IntervalNotFoundException(interval.toString());
		
		root = deleteHelper(root, interval);
	}
	/** 
	 * Recursive helper method for the delete operation.  
	 * 
	 * @param node the interval node that is currently being checked.
	 * @param interval the interval to delete.
	 * @throws IllegalArgumentException if the interval is null.
	 * @throws IntervalNotFoundException
	 *             if the interval is not null, but is not found in the tree.
	 * @return Root of the tree after deleting the specified interval.
	 */
	public IntervalNode<T> deleteHelper(IntervalNode<T> node, 
										IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException{
		// TODO 
//		If node is null, throw IntervalNotFoundException
		if(node == null)
			throw new IntervalNotFoundException(interval.toString());
//		If interval is found in this node, delete it and replace it with 
//			leftMost in right subtree
		if(interval.compareTo(node.getInterval()) == 0){
//			If right child exists
			if(node.getRightNode() != null){
				node.setInterval(node.getSuccessor().getInterval());
				node.setRightNode( deleteHelper(node.getRightNode(), 
						node.getSuccessor().getInterval()) );
				// update the new maxEnd.  
				node.setMaxEnd(recalculateMaxEnd(node.getInterval(), node));
			}
			else{ // If right child doesn't exist, return the left child
				node = node.getLeftNode();
			}
		}
//		If interval is in the right subtree
		else if(interval.compareTo(node.getInterval()) > 0){ // > ??
			node.setRightNode(deleteHelper(node.getRightNode(), interval));
			//Update the maxEnd if necessary.  
			node.setMaxEnd(recalculateMaxEnd(node.getInterval(), node));
		}
		else{ // If interval is in the left subtree.
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
			//Update the maxEnd if necessary.  
			node.setMaxEnd(recalculateMaxEnd(node.getInterval(), node));
		}
		
		return node;		
	}
	/**
	 * A non-recursive helper method that recalculates maxEnd for 
	 * 			any node based on the maxEnd of its child nodes
	 * 
	 * @param interval for compare
	 * @param node to re-calculate
	 * @return the max end of the interval
	 */
	private T recalculateMaxEnd(IntervalADT<T> interval, IntervalNode<T> node){
		T biggerChildEnd = interval.getEnd(); // randomly initialize
		
//		check the biggerChildEnd of left node and right node, 
//			update if find bigger one
		if(node.getLeftNode() == null || node.getRightNode() == null){
			if (node.getLeftNode() == null && node.getRightNode() == null)
				biggerChildEnd = interval.getEnd();
			else if (node.getLeftNode() == null)
				biggerChildEnd = node.getRightNode().getMaxEnd();
			else if (node.getRightNode() == null)
				biggerChildEnd = node.getLeftNode().getMaxEnd();
		}
		else if(node.getLeftNode().getMaxEnd().compareTo(
							node.getRightNode().getMaxEnd()) > 0)
			biggerChildEnd = node.getLeftNode().getMaxEnd();
		else // left < right
			biggerChildEnd = node.getRightNode().getMaxEnd();
			
		if (biggerChildEnd.compareTo(interval.getEnd()) > 0)
			return biggerChildEnd;
		else 
			return interval.getEnd();
	}
	
	/**
	 * Find and return a list of all intervals that overlap 
	 * 		with the given interval. 
	 * 
	 * @param interval the interval to search for overlapping
	 * @return list of intervals that overlap with the input interval.
	 */
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		// TODO
		List<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, result);
		
		return result;
	}
	private void findOverlappingHelper(IntervalNode<T> node, 
				IntervalADT<T> interval, List<IntervalADT<T>> result){
//		if node is null, return</li>
		if(node == null)
			return;
//		if node interval overlaps with the given input interval, 
//		add it to the result.
		if(interval.overlaps(node.getInterval()))
			result.add(node.getInterval());
//		if left subtree's max is greater than or equal to the interval's start,
//				call findOverlappingHelper in the left subtree.</li>
		if(node.getLeftNode() != null && 
				node.getLeftNode().getMaxEnd().compareTo( 
						interval.getStart()) >= 0)
			findOverlappingHelper(node.getLeftNode(), interval, result);
			
//		if right subtree's max is greater than or equal to the interval's start 
//				call call findOverlappingHelper in the rightSubtree.</li>
		if(node.getRightNode() != null && 
				node.getRightNode().getMaxEnd().compareTo( 
						interval.getStart()) >= 0)
			findOverlappingHelper(node.getRightNode(), interval, result);
	}
	/**
	 * Search and return a list of all intervals containing a given point. 
	 * This method may return an empty list. 

	 * @throws IllegalArgumentException if point is null
	 * @param point
	 *            input point to search for.
	 * @return List of intervals containing the point.
	 */
	public List<IntervalADT<T>> searchPoint(T point) {
		if(point == null)
				throw new IllegalArgumentException();
		List<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		searchPointHealper(point, root, result);
		
		return result; 
	}
	/**
	 * Helper method for searchPoint

	 * @throws IllegalArgumentException if point is null
	 * @param point
	 */
	private void searchPointHealper(T point, IntervalNode<T> node, 
									List<IntervalADT<T>> result){
		if(node == null) // if node is null, return
			return;
		if(node.getInterval().contains(point))
			result.add(node.getInterval());
		searchPointHealper(point, node.getLeftNode(), result);
		searchPointHealper(point, node.getRightNode(), result);
	}

	/**
	 * Get the size of the interval tree. The size is the total number of
	 * nodes present in the tree. 
	 * 
	 * @return int number of nodes in the tree.
	 */
	public int getSize() {
		return getSizeHelper(root);
	}
	
	/**
	 * Define and call a recursive helper function to calculate size
	 * 
	 * @return int number of nodes in the tree.
	 */
	private int getSizeHelper(IntervalNode<T> node){
		if(node == null)
			return 0;
		else
			return 1 + getSizeHelper(node.getRightNode()) + 
					getSizeHelper(node.getLeftNode());
	}

	/**
	 * Return the height of the interval tree at the root of the tree. 
	 * 
	 * @return the height of the interval tree
	 */
	public int getHeight() {
		System.out.println("in height");
		return getHeightHelper(root);
	}
	/**
	 * Define and call a recursive helper function to calculate this 
	 * for a given node.
	 * 
	 * @return the height of the interval tree
	 */
	private int getHeightHelper(IntervalNode<T> node){
		if(node == null)
			return 0;
		else 
			return 1 + Math.max(getHeightHelper(node.getLeftNode()), 
							getHeightHelper(node.getRightNode()));
				
	}

	/**
	 * Returns true if the tree contains an exact match for the start 
	 * and end of the given interval.
	 * The label is not considered for this operation.
	 *  
	 * @param interval
	 * 				target interval for which to search the tree for. 
	 * @return boolean 
	 * 			   	representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             	if interval is null.
	 * 
	 */
	public boolean contains(IntervalADT<T> interval) {
		return containsHelper(interval, root);
	}
	/**
	 * Define and call a recursive helper function to call with root node 
	 * and the target interval.
	 * @param interval
	 * 				target interval for which to search the tree for. 
	 * @return boolean 
	 * 			   	representing if the tree contains the interval.
	 */
	private boolean containsHelper(IntervalADT<T> interval, 
			IntervalNode<T> node) {
		if(node == null)
			return false;
		if(interval.compareTo(node.getInterval()) == 0)
			return true;
		if(interval.compareTo(node.getInterval()) > 0)
			return containsHelper(interval,  node.getRightNode());
		else // interval.compareTo(node.getInterval()) < 0
			return containsHelper(interval,  node.getLeftNode());
	}

	/**
	 * Print the statistics of the tree in the below format
	 */
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");
	}
}
