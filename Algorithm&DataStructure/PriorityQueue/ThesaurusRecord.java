/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Memory Efficient Merging of Sorted Files (p3)
//FILE:             ThesaurusRecord.java
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
import java.util.Arrays;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used 
 * 		when merging thesaurus data.
 * The word field is the entry in the thesaurus, syn is the list of all 
 * 		associated synonyms.
 */
public class ThesaurusRecord extends Record{
	//  declare data structures required
	private final int SIZE = 50;
	private String[][] words = new String[SIZE][SIZE]; 
	private ArrayList<String> keys = new ArrayList<String>(); 
	private ArrayList<FileLine> fLines = new ArrayList<FileLine>();
	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent 
	 * 		constructor
	 * and then calling the clear method()
	 */
	public ThesaurusRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This Comparator should simply behave like the default (lexicographic) 
	 * 	compareTo() method
	 * for Strings, applied to the portions of the FileLines' 
	 * 		Strings up to the ":"
	 * The getComparator() method of the ThesaurusRecord class 
	 * 		will simply return an instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			// split lines by :
			String[] parts1 = l1.getString().split(":");
			String[] parts2 = l2.getString().split(":");
			
			if(parts1[0].compareTo( parts2[0] )  < 0)
				return -1; // order does not change, l1 is less 
			else
				return 1; // order changes, l2 is less
		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the 
	 * 		ThesaurusLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
	}

	/**
	 * This method should (1) set the word to null and (2) empty 
	 * 		the list of synonyms.
	 */
	public void clear() {
		// initialize/reset data members
		for (int i = 0; i < words.length; i++) {
			for (int j = 0; j < words[i].length; j++) {
				words[i][j] = null;
			}
		}
	}

	/**
	 * This method should parse the list of synonyms contained in the given 
	 * 		FileLine and insert any
	 * which are not already found in this ThesaurusRecord's list of synonyms.
	 */
	public void join(FileLine w) {
		String line = w.getString();
		String[] parts = line.split(":");
		String partKey = parts[0];
		String partWords = parts[1];
		
		String[] splitWords = partWords.split(",");
		
		boolean hasSame = false;
		
		// for, if the key already exist, 
				// add to words[i][] 
		for (int i = 0; i < keys.size(); i++){  
			// compare current with existing
			if(keys.get(i).equals(partKey)){ 
				for (int j = 0; j < words[i].length; j++) {
					if(words[i][j] == null){ // find insert positon
						hasSame = true;
						for (int k = 0; k < splitWords.length; k++) {
							words[i][j+k] = splitWords[k];
						} 
						break; // found same
					}
				}
				break; // found same
			}
		}
		// else, it is new, add to keys, celcius[i][0], fLines
		if(!hasSame){ // if this line is new
			for (int k = 0; k < splitWords.length; k++) {
				words[keys.size()][k] = splitWords[k];
			} 
			keys.add(partKey);
			fLines.add(w);
		}
	}

	/**
	 * See the assignment description and example runs for the exact 
	 * 		output format.
	 * @return the ordered lines
	 */
	public String toString() {
		trim();
		String output = ""; // store output lines
		FileLine tempLine = null; // store temp line from remove 
		String[] parts; // store keys
		String tempK; // temporary key
		
		FileLinePriorityQueue pq = new FileLinePriorityQueue(
				fLines.size(), getComparator()); // create priority queue
		for (int i = 0; i < fLines.size(); i++) {
			try {
				pq.insert(fLines.get(i));
			} catch (PriorityQueueFullException e) {
				System.out.println("PriorityQueueFullException while insert.");
			}
		}
		
		// remove from priority queue
		// check keys, then add to output
		for (int i = 0; i < fLines.size(); i++) {
			try {
				tempLine = pq.removeMin();
			} catch (PriorityQueueEmptyException e) {
				System.out.println("PriorityQueueEmptyException while remove");
			}
			parts = tempLine.getString().split(":");
			tempK = parts[0];
			
			// loop through all the existing lines
			for (int j = 0; j < fLines.size(); j++){  
				// compare current with existing
				if(keys.get(j).equals(tempK)){
					// if keys, add to output
					output += tempK + ":";
					for (int k = 0; k < words[j].length; k++) {
						if(k > 0)
							output += ",";
						output += words[j][k];
					}
					output += "\n";
				}
			}
		}
		return output;
	}
	
	/**
	 * A helper method, called by toString
	 * Make the words[][] field only contain string, do not contain null
	 */
	private void trim(){
		// purlyWords does not contain null
		int[] wordsNum = new int[keys.size()];
		String [][] purlyWords = new String[keys.size()][1];
		for (int i = 0; i < keys.size(); i++) { // for every line
			for (int j = 0; j < words[i].length; j++) { // get words number
				if(words[i][j] != null)
					wordsNum[i]++;
				else
					break;
			}
			String [] temp = new String[wordsNum[i]];
			for (int j = 0; j < words[i].length; j++) { // get words number
				if(words[i][j] != null)
					temp[j] = words[i][j];
				else
					break;
			}
			purlyWords[i] = temp;
		}
		// sort words
		for (int i = 0; i < purlyWords.length; i++) {
			Arrays.sort(purlyWords[i]);
		}
		words = purlyWords;
	}
}
