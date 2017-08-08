/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Spring 2016 
//PROJECT:          Memory Efficient Merging of Sorted Files (p3)
//FILE:             WeatherRecord.java
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
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when 
 * 		merging weather data. Station and Date store the station and date 
 * 		associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which 
 * 		they came are indexed.
 */
public class WeatherRecord extends Record{
	private final int SIZE = 100;
	// store lines
	private ArrayList<FileLine> fList = new ArrayList<FileLine>(SIZE); 
	private int[] station = new int[SIZE];
	private int[] date = new int[SIZE];
	private double[][] celcius = new double[SIZE/2][SIZE/2];
	// numFiles is in super class
	/**
	 * Constructs a new WeatherRecord by passing the parameter to the 
	 * 		parent constructor and then calling the clear method()
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This comparator should first compare the stations associated with 
	 * 		the given FileLines. If they are the same, then the dates
	 *  	should be compared. 
	 */
	private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			// implement compare() functionality
			int FirstMinusSec = 0;
			// split lines by comma
			String[] parts1 = l1.getString().split(",");
			String[] parts2 = l2.getString().split(",");

			// first compare the stations
			FirstMinusSec = 
					Integer.parseInt(parts1[0]) - Integer.parseInt(parts2[0]);
			// if the result is 0 then the dates
			if(FirstMinusSec == 0)
				FirstMinusSec = 
				Integer.parseInt(parts1[1]) - Integer.parseInt(parts2[1]);

			return FirstMinusSec;
		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the 
	 * 		WeatherLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
	}

	/**
	 * This method should fill each entry in the data structure containing
	 * 		the readings with Double.MIN_VALUE
	 */
	public void clear() {
		// TODO initialize/reset data members
		// every element in celcius = Double.MIN_VALUE;
		for (int i = 0; i < celcius.length; i++) {
			for (int j = 0; j < celcius[i].length; j++) {
				celcius[i][j] = Double.MIN_VALUE;
			}
		}
	}

	/**
	 * This method should parse the String associated with the given 
	 * 		FileLine to get the station, date, and reading contained therein. 
	 * Then, in the data structure holding each reading, the entry 
	 * 		with index equal to the parameter 
	 * FileLine's index should be set to the value of the reading. 
	 * Also, so that this method will handle merging when this WeatherRecord
	 * 		is empty, the station and date associated with this WeatherRecord
	 * 		should be set to the station and date values which 
	 * 		were similarly parsed.
	 */
	public void join(FileLine li) {
		// get file number
		int fileNum = li.getFileIterator().getIndex();

		boolean hasSame = false; // whether the same line exists 

		//	    	parse the String associated with the given FileLine to get the 
		//	    		station, date, and reading contained therein. 
		String line = li.getString();
		String[] parts = line.split(",");
		int tempStation = Integer.parseInt(parts[0]);
		int tempDate = Integer.parseInt(parts[1]);
		double tempCelcius = Double.parseDouble(parts[2]);

		// for, if the station and date already exist, 
		// add to celcius[i][fileNum] 
		for (int i = 0; i < fList.size(); i++){  
			if(station[i] == tempStation) // compare current with existing
				if(date[i] == tempDate){
					celcius[i][fileNum] = tempCelcius;
					hasSame = true;
				}
		}
		// else, it is new, add to fList, station, date,celcius[i][fileNum]
		if(!hasSame){ // if this line is new
			station[fList.size()] = tempStation;
			date[fList.size()] = tempDate;
			celcius[fList.size()][fileNum] = tempCelcius; 
			fList.add(li);
		}
	}

	/**
	 * See the assignment description and example runs for the exact 
	 * 	output format.
	 */
	public String toString() {
		String output = ""; // store output lines
		FileLine tempLine = null; // store temp line from remove 
		String[] parts; // store station, date
		int tempS; // temporary station
		int tempD; // temporary date

		FileLinePriorityQueue pq = new FileLinePriorityQueue(
				fList.size(), getComparator()); // create priority queue
		for (int i = 0; i < fList.size(); i++) {
			try {
				pq.insert(fList.get(i));
			} catch (PriorityQueueFullException e) {
				System.out.println("PriorityQueueFullException while insert.");
			}
		}
		// remove from priority queue
		// check station and date, then add to output
		for (int i = 0; i < fList.size(); i++) {
			try {
				tempLine = pq.removeMin();
			} catch (PriorityQueueEmptyException e) {
				System.out.println("PriorityQueueEmptyException while remove");
			}
			parts = tempLine.getString().split(",");
			tempS = Integer.parseInt(parts[0]);
			tempD = Integer.parseInt(parts[1]);
			// loop through all the existing lines
			for (int j = 0; j < fList.size(); j++){  
				if(station[j] == tempS) // compare current with existing
					if(date[j] == tempD){
						// if station and date matches, add to output
						output += tempS + "," + tempD;
						for (int k = 0; k < 4; k++) {
							if(celcius[j][k] == Double.MIN_VALUE)
								output += ",-";
							else
								output += "," + celcius[j][k];
						}
						output += "\n";
					}
			}
		}
		return output;
	}
}
