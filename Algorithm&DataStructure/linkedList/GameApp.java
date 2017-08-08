package p2;
import java.util.Scanner;

public class GameApp{
    /**
     * Scanner instance for reading input from console
     */
    private static final Scanner STDIN = new Scanner(System.in);
    Game game; // ??
    
    /**
     * Constructor for instantiating game class
     * @param seed: Seed value as processed in command line
     * @param timeToPlay: Total time to play from command line
     */
    public GameApp(int seed, int timeToPlay){
        // Create a new instance of Game class
    	game = new Game(seed, timeToPlay);
    }

    /**
     * Main function which takes the command line arguments and instantiate the
     * GameApp class.
     * The main function terminates when the game ends.
     * Use the getIntegerInput function to read inputs from console
     *
     * @param args: Command line arguments <seed> <timeToPlay>
     */
    public static void main(String[] args){
    	
        System.out.println("Welcome to the Job Market!");

        //Take input from command line, process it and add error checking
        if(args.length != 2)
        	throw new IllegalArgumentException();
        int firstArg = 0;
        int secondArg = 0;
        // if either entry is not a positive integer the program should simply terminate.
        try{
        	firstArg = Integer.parseInt(args[0]);
        	secondArg = Integer.parseInt(args[1]);
        } catch (Exception e){
        	System.out.println("At least one argument is not an integer.");
        }
        if(firstArg < 0 || secondArg < 0)
        	throw new IndexOutOfBoundsException();
        
        GameApp gameApp = new GameApp(firstArg, secondArg);  
        
        //TODO: Call the start() method to start playing the game
        gameApp.start();
    }

    /**
     * 1.	Display how much time remains in the game
     * 2.	Use the Game object to create new jobs (This should only be done on the first iteration, or if a job is successfully executed either to completion or reinsertion into the list, otherwise skip this step)
	 * 3.	Use the Game object to display all jobs
	 * 4.	Prompt the user for an index of a job to work on. There is a time penalty for picking a job that is not at index 0.
	 * 		1.	The time penalty is equal to the job index selected. 
	 * 		2.	Deduct this time penalty from the total game time remaining.  
	 * 5.	Prompt the user for an amount of time to work on this job
	 * 6.	Get the job stored at the given index and attempt to work on it for the given amount of time (error checking)
	 * 7.	Update the job for the specified amount of time
	 * 8.	If the job has not been completed yet
	 * 		1.	Prompt the user for an index to insert the element back into the list.
	 * 		2.	Calculate the time penalty. There is a time penalty for not inserting at position 0.   The time penalty is equal to the index chosen or the total number of items on the list if the index is invalid.  
	 * 		3.	Deduct the time penalty from the total game time remaining.
	 * 			For example: If you choose to place the job at position 0, there is no time penalty, but if you choose to place job at index 5 there is a time penalty of 5 that must be subtracted from the total game time remaining.  If you choose index -1, the time penalty is equal to the total number of jobs in the list.
	 * 		4.	Insert the job back in the list at the specified location if it’s a valid index, else at the end of the list in case of invalid index. 
	 * 9.	Else
	 * 		1.	Display a success message to the user and update them on their current score.
	 *
     */
    private void start(){
    	while(!game.isOver()){
	//    	boolean createNewJob = true;
	    	// 1
	    	System.out.println("You have " + game.getTimeToPlay() + " left in the game!");
	    	// 2 a job is successfully executed either to completion or reinsertion into the list
	//    	if(createNewJob){
	            game.createJobs();
	//            createNewJob = false;
	//    	}
	            
	    	// 3 Use the Game object to display all jobs
	    	game.displayActiveJobs();
	//    	game.displayCompletedJobs();
	    	// 4
	    	int index = getIntegerInput("Select a job to work on: ");
	    	// The time penalty is equal to the job index selected. 
	    	// Deduct this time penalty from the total game time remaining.  
	    	game.setTimeToPlay(game.getTimeToPlay() - index); // index is equal to time penalty
	    	// 5
	    	int amountOfTime = getIntegerInput("For how long would you like to work on this job?: ");
	    	// 6 7 ??
	    	Job removedJob = null;
	    	if( amountOfTime >= game.getTimeToPlay()) // time left cannot finish job
	    		removedJob = game.updateJob(index, game.getTimeToPlay());
	    	else
	    		removedJob = game.updateJob(index, amountOfTime);
	    		
	    	// 8 
	//    	if(the job has not been completed yet)
	    	if(!removedJob.isCompleted()){
	    		int reinsertIndex = getIntegerInput("At what position would you like to insert the job back into the list?\n");
	    		game.addJob(reinsertIndex, removedJob);	
	    	}
	    		
	
	    	// 9
	    	else{
	    		System.out.println("Job completed! Current Score: " + game.getTotalScore());
	    		System.out.println("Total Score: " + game.getTotalScore());
	    		System.out.println("The jobs completed:");
	    		game.displayCompletedJobs();
	    	}
    	}
	    	
    	// After the time left in the game runs out the program should print one 
    	// 	final message displaying their final score.
    	System.out.println("Game Over!\nYour final score: " + game.getTotalScore());
    }

    /**
     * Displays the prompt and returns the integer entered by the user
     * to the standard input stream.
     *
     * Does not return until the user enters an integer.
     * Does not check the integer value in any way.
     * @param prompt The user prompt to display before waiting for this integer.
     */
    public static int getIntegerInput(String prompt) {
        System.out.print(prompt);
        while ( ! STDIN.hasNextInt() ) {
            System.out.print(STDIN.next()+" is not an int.  Please enter an integer.");
        }
        return STDIN.nextInt();
    }
}