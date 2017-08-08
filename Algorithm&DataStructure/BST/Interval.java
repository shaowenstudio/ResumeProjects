package p4;
/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Schedule Planner (p4)
//FILE:             Interval.java
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

/**
 * This type describes the required methods of your required
 * Interval type. Note: that it implements Comparator
 * so that two intervals can be compared with each other.
 * 
 * This interval can be used to represent various things. For example, in
 * scheduling problem, this will represent the start and end dates for an
 * assignment. This Interval will be stored as a data member inside our
 * IntervalTree.
 *  
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	private T start;
	private T end;
	private String label;
	
	/** Constructor for constructing an interval
	 *
	 * @param start - the start of the interval
	 * @param end - the end of the interval
	 * @param label - the name of the interval
	 *  
	*/
    public Interval(T start, T end, String label) {
    	this.start = start;
    	this.end = end;
    	this.label = label;
    }

    /** Returns the start value (must be Comparable<T>) of the interval. */
    public T getStart() {
    	return start;
    	
    }

    /** Returns the end value (must be Comparable<T>) of the interval. */
    public T getEnd() {
    	return end;
    }

    /** Returns the label for the interval. */
    public String getLabel() {
    	return label;
    }

    /**
	 * Return true if this interval overlaps with the other interval. 
	 * 
	 * @param other target interval to compare for overlap
	 * @return true if it overlaps, false otherwise.
	 * @throws IllegalArgumentException
	 *             if the other interval is null.
	 */
    public boolean overlaps(IntervalADT<T> other) {
    	return (other.getStart().compareTo(start) >= 0 && 
    				other.getStart().compareTo(end) <= 0) 
    		|| (other.getEnd().compareTo(start) >= 0 && 
    				other.getEnd().compareTo(end) <= 0);
    }

    /**
	 * Returns true if given point lies inside the interval.
	 * 
	 * @param point
	 *            to search
	 * @return true if it contains the point
	 */
    public boolean contains(T point) {
    	return point.compareTo(start) >= 0 && 
    		  	point.compareTo(end) <= 0; 
    }

    /**
	 * Compares this interval with the other and return a negative value 
	 * if this interval comes before the "other" interval.  Intervals 
	 * are compared first on their start time.  The end time is only considered
	 * if the start time is the same.

	 * @param other
	 *            the second interval to which compare this interval with
	 *            
	 * @return negative if this interval's comes before the other interval, 
	 * positive if this interval comes after the other interval,
	 * and 0 if the intervals are the same.  See above for details.
	 */
    public int compareTo(IntervalADT<T> other) {
    	int result = 0;
    	if(start.compareTo(other.getStart()) > 0) // other is after this 
    		result = 1;
    	else if(start.compareTo(other.getStart()) < 0) // other is befor this
    		result = -1;
    	else // start value are the same
    		if(end.compareTo(other.getEnd()) > 0) // other is after this 
        		result = 1;
        	else if(end.compareTo(other.getEnd()) < 0) // other is befor this
        		result = -1;
        	else // end are the same
        		result = 0;
    	
    	return result;
    	
    }
    
    public String toString(){
    	return label + " [" + start + ", " + end + "]";
    }
}
