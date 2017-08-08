package p2;

public class Scoreboard  implements ScoreboardADT{
	// fields
	private ListADT<Job> jList;
	
	/**
     * Construct an instance that stand for this class
     * 
     * @param ListADT<Job> jl, a jobList with different jobs 
     */
	public Scoreboard(ListADT<Job> jl){
		this.jList = jl;
	}
	
    /**
     * Calculates the total combined number of points for every job in the scoreboard.
     * 
     * @return The summation of all the points for every job currently stored in the scoreboard.
     */
	public int getTotalScore() {
		int totalPoints = 0; // Combination of every job's point in job list
		
		for(Job j : this.jList) // for each job in job list
			totalPoints += j.getPoints();
		
		return totalPoints;
	}

	/**
     * Inserts the given job at the end of the scoreboard.
     * 
     * @param job 
     * 		The job that has been completed and is to be inserted into the list.
     */
	public void updateScoreBoard(Job job) {
		// not check parameter?
		// Inserts the given job at the end of the scoreboard.
		this.jList.add(job);
		
	}

	/**
     * Prints out a summary of all jobs currently stored in the scoreboard. The formatting must match the example exactly.
     */
	public void displayScoreBoard() {
		for(Job j : this.jList){
			System.out.println("Job Name: " + j.getJobName());
			System.out.println("Points earned for this job: " + j.getPoints());
			System.out.println("--------------------------------------------");
		}
		
	}

}
