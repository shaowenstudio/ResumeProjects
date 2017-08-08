package p2;
import java.util.Iterator;

public class JobList<E> implements ListADT<E>{
		Listnode<E> head;
		int numItems;
		
		public JobList(){
			head = new Listnode<E>(null);
			numItems = 0;
		}
		
	 	/** Adds an item at the end of the list
	     * @param item
	     *              an item to add to the list
	     * @throws IllegalArgumentException
	     *              if item is null
	     */

		public void add(E item){
	    	// check parameter
			if(null == item)
	    		throw new IllegalArgumentException();
	    	
	    	// create a temp list node to traverse the list
	    	Listnode<E> lastNode = head;
	    	
	    	// add the new node to the end of the list
	    	while(lastNode.getNext() != null){
	    		lastNode = lastNode.getNext();
	    	}
	    	
	    	lastNode.setNext(new Listnode<E>(item));
	    	numItems++;
	    }

	    /** Add an item at any position in the list
	     * @param item
	     *              an item to be added to the list
	     * @param pos
	     *              position at which the item must be added. Indexing starts from 0
	     * @throws IllegalArgumentException
	     *              if item is null
	     * @throws IndexOutOfBoundsException
	     * ???             if pos is less than 0 or greater than size() - 1
	     */

		public void add(int pos, E item){
			// check parameters
			if(pos == size()) // if index is our of bound, then this will not execute, otherwise (include add at pos 0) add to the end of the list
				this.add(item);
			else if(null == item)
			   	throw new IllegalArgumentException();
			else if(pos < 0 || pos > size() - 1) // size() - 1?
				throw new IndexOutOfBoundsException();
			else{
				//find prior listnode
				Listnode<E> curNode = head;
				for (int i = 0; i < pos; i++) 
					curNode = curNode.getNext();
				curNode.setNext(new Listnode<E>(item, curNode.getNext()));
				numItems++;
			}
		}

	    /** Check if a particular item exists in the list
	     * @param item
	     *              the item to be checked for in the list
	     * @return true
	     *              if value exists, else false
	     * @throws IllegalArgumentException
	     *              if item is null
	     */
// ?????????? has to be the same, not filds, but location in memory
		public boolean contains(E item){
			if(null == item) // check parameter
	    		throw new IllegalArgumentException();
			// create a temp list node to traverse the list
	    	Listnode<E> curNode = head;
	    	// traverse the list
	    	while(curNode.getNext() != null){
	    		curNode = curNode.getNext();
	    		if(curNode.getData().equals(item))
	    			return true;
	    	}
			
			return false;
	    }

	    /** Returns the position of the item to return
	     * @param pos
	     *              position of the item to be returned
	     * @throws IndexOutOfBoundsException
	     *              if position is less than 0 or greater than size() - 1
	     */

	    public E get(int pos)throws IndexOutOfBoundsException{
	    	if(pos < 0 || pos > size() - 1) // check parameter
	    		throw new IndexOutOfBoundsException();
	    	// create a temp list node to traverse the list
	    	Listnode<E> curNode = head;
	    	// traverse the list
	    	for (int i = 0; i <= pos; i++) {
	    		curNode = curNode.getNext();
			}
	    	
	    	return curNode.getData();
	    }

	    /** Returns true if the list is empty
	     * @return value is true if the list is empty
	     *              else false
	     */

	    public boolean isEmpty(){
	    	return numItems == 0;
	    }

	    /** Removes the item at the given positions
	     * @param pos
	     *          the position of the item to be deleted from the list
	     * @return returns the item deleted
	     * @throws IndexOutOfBoundsException
	     *          if the pos value is less than 0 or greater than size() - 1
	     */

	    public E remove(int pos)throws IndexOutOfBoundsException{
	    	if(pos < 0 || pos > size() - 1) // check parameter
	    		throw new IndexOutOfBoundsException();
	    	
	    	Listnode<E> priorNode = head;
	    	for (int i = 0; i < pos; i++) 
	    		priorNode = priorNode.getNext();
	    	
	    	Listnode<E> removed = priorNode.getNext();
	    	priorNode.setNext(priorNode.getNext().getNext());
	    	numItems--;
	    	
	    	return removed.getData();
	    }

	    /** 
	     * Returns the size of the singly linked list
	     * 
	     * @return the size of the singly linked list
	     */
	    public int size(){
	    	return numItems;
	    }

		public Iterator<E> iterator() {
			return new JobListIterator<E>(this.head, numItems);
		}

}
