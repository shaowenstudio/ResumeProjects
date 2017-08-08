package p2;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class JobListIterator<E> implements Iterator<E>{
	// fields
	Listnode<E> head;
	int curPos;
	int numItems;
	
	/** 
     * A constructor that accepts Listnode and numItems
     *
     * @param Listnode<E> head, represent the head of list being iterated.
     * @param int numItems, represent the number of items of list being iterated.
     */
	public JobListIterator(Listnode<E> head, int numItems){
		this.head = head;
		this.numItems = numItems;
		curPos = 0;
	}

	/** 
     * Returns true if current position is less than the size of the JobList
     * false otherwise.
     * 
     * @return true if curPos is less than the size of myList.
     */
	public boolean hasNext() {
		return curPos < numItems;
	}

	/** 
     * Return the next item and advance the curPos by one item.
     * 
     * @return return the next item and advance the curPos by one item.
     */
	public E next() {
		// if list is empty, throw exception
		if(!hasNext())
			throw new NoSuchElementException(); 
			
		// if list has at least one element, let temp equal to the first one
		Listnode<E> temp = head.getNext();
		// traverse the list
		for (int i = 0; i < curPos; i++) 
			temp = temp.getNext();
		curPos++;
		
		return temp.getData();
	}
}
