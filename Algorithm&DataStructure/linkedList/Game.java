package p2;
public class Game{

    /**
     * A list of all jobs currently in the queue.
     */
    private ListADT<Job> list;
    /**
     * Whenever a Job is completed it is added to the scoreboard
     */
    private ScoreboardADT scoreBoard;
    private int timeToPlay;
    private JobSimulator jobSimulator;

    /**
     * Constructor. Initializes all variables.
     * @param seed
     * seed used to seed the random number generator in the Jobsimulator class.
     * @param timeToPlay
     * duration used to determine the length of the game.
     */
    public Game(int seed, int timeToPlay){
        /**
         * Initializes all member variables
         */
    	list = new JobList<Job>();
    	scoreBoard = new Scoreboard(new JobList<Job>()); // only completed jobs
    	this.timeToPlay = timeToPlay;
    	jobSimulator = new JobSimulator(seed);
    }

    /**
     * Returns the amount of time currently left in the game.
     * @returns the amount of time left in the game.
     */
    public int getTimeToPlay() {
        // return the amount of time left
        return timeToPlay;
    }

    /**
     * Sets the amount of time that the game is to be executed for.
     * Can be used to update the amount of time remaining.
     * @param timeToPlay
     *        the remaining duration of the game
     */
    public void setTimeToPlay(int timeToPlay) {
        // Setter for amount of time to play
    	this.timeToPlay = timeToPlay;
    }

    /**
     * States whether or not the game is over yet.
     * @returns true if the amount of time remaining in
     * the game is less than or equal to 0,
     * else returns false
     */
    public boolean isOver(){
        // check if the game is over or not
        return this.timeToPlay <= 0;
    }
    
    /**
     * This method simply invokes the simulateJobs method
     * in the JobSimulator object.
     */
    public void createJobs(){
        // Invoke the simulator to create jobs
    	jobSimulator.simulateJobs(list, timeToPlay);
    }

    /**
     * @returns the length of the Joblist.
     */
    public int getNumberOfJobs(){
        // Get the number of jobs in the JobList
        return list.size();
    }

    /**
     * Adds a job to a given position in the joblist.
     * Also requires to calculate the time Penalty involved in
     * adding a job back into the list and update the timeToPlay
     * accordingly
     * @param pos
     *      The position that the given job is to be added to in the list.
     * @param item
     *      The job to be inserted in the list.
     */
    public void addJob(int pos, Job item){
        /**
         * Add a job in the list
         * based on position
         */
    	int timePenalty = 0;
    	int insertIndex = pos;
    	
		if(insertIndex > 0)
			timePenalty = insertIndex;
		else if(insertIndex < 0)
			timePenalty = list.size(); //  the total number of jobs in the list.
		
		// Insert the job back 
		if(insertIndex < 0 || insertIndex > list.size()){ // an invalid index
			list.add(item); // add job at the end
			timeToPlay -= timePenalty;
		}
		else{
			list.add(insertIndex, item);
			timeToPlay -= timePenalty;
		}
    }

    /**
     * Adds a job to the joblist.
     * @param item
     *      The job to be inserted in the list.
     */
    public void addJob(Job item){
        // Add a job in the joblist
    	list.add(item); // timeToPlay?
    }

    /**
     * Given a valid index and duration,
     * executes the given job for the given duration.
     *
     * This function should remove the job from the list and
     * return it after applying the duration.
     *
     * This function should set duration equal to the
     * amount of time remaining if duration exceeds it prior
     * to executing the job.
     * After executing the job for a given amount of time,
     * check if it is completed or not. If it is, then
     * it must be inserted into the scoreBoard.
     * This method should also calculate the time penalty involved in
     * executing the job and update the timeToPlay value accordingly
     * @param index
     *      The job to be inserted in the list.
     * @param duration
     *      The amount of time the given job is to be worked on for.
     */
    public Job updateJob(int index, int duration){
        //TODO: As per instructions in comments
//    	list.get(index).setSteps(duration);
//    	remove the job from the list
    	Job removedJob =  list.remove(index);
    	
//    	set duration equal to the amount of time remaining if duration 
//    	exceeds it prior to executing the job.
    	removedJob.setSteps(duration);
    	
//    	check if it is completed or not
    	if(removedJob.isCompleted())
    		scoreBoard.updateScoreBoard(removedJob);
    	
//    	calculate the time penalty and update it
    	timeToPlay -= duration;
    	
//    	Return the removedJob after applying the duration
        return removedJob;
    }

    /**
     * This method produces the output for the initial Job Listing, IE:
     * "Job Listing
     *  At position: job.toString()
     *  At position: job.toString()
     *  ..."
     *
     */
    public void displayActiveJobs(){
        // Display all the active jobs
    	int jobPos = 0;
    	String output = "Job Listing";
    	for(Job j : list)
    		output += "\n" + "At position: " + jobPos++ +  " " +j.toString();
    	System.out.println(output + "\n");
    }

    /**
     * This function simply invokes the displayScoreBoard method in the ScoreBoard class.
     */
    public void displayCompletedJobs(){
        //Display all the completed jobs
    	scoreBoard.displayScoreBoard();

    }

    /**
     * This function simply invokes the getTotalScore method of the ScoreBoard class.
     * @return the value calculated by getTotalScore
     */
    public int getTotalScore(){
        // Return the total score accumulated
        return scoreBoard.getTotalScore();
    }
}