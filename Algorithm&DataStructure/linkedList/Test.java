package p2;
import java.util.Iterator;

public class Test {

	public static void main(String[] args) {
		String name = "abcde";
		Game game = new Game(1, 10);
		
		// add to the list in the end
		for (int i = 0; i < name.length(); i++) 
			game.createJobs();
		
		game.displayActiveJobs();
		game.displayCompletedJobs();
		
		
//		JobSimulator jobSi = new JobSimulator(1); // test JobSimulator
//		
//		JobList<Job> jList = new JobList<Job>(); // create a job list
//		
//		ScoreboardADT scoreBoard = new Scoreboard(jList); // score board
//		
//		
//
//		// add to the list in the end
//		for (int i = 0; i < name.length(); i++) 
//			jList.add(new Job(name.charAt(i) + "", i, i));
//		
//		jobSi.simulateJobs(jList, 5);
//		System.out.println("Total number is: " + scoreBoard.getTotalScore());
//
//		System.out.println("----------Jobs----------");
//		
//		scoreBoard.displayScoreBoard();

/*	
 * Test Scoreboard
	String name = "abcde";
	JobList<Job> jList = new JobList<Job>();
	Job newj = new Job("f", 6, 6);

	// add to the list at position 0
	for (int i = 0; i < name.length(); i++) 
		jList.add(new Job(name.charAt(i) + "", i, i));
	
	ScoreboardADT scoreBoard = new Scoreboard(jList); 
	scoreBoard.updateScoreBoard(newj);
	System.out.println("Total number is" + scoreBoard.getTotalScore());
	scoreBoard.displayScoreBoard();

	System.out.println("----------Finish----------");

 * Test contain, faild
	String name = "abcde";
	JobList<Job> jList = new JobList<Job>();
	Job j = new Job("a", 0, 0);

	// add to the list at position 0
	for (int i = 0; i < name.length(); i++) 
		jList.add(new Job(name.charAt(i) + "", i, i));
	System.out.println("--1:" + jList.contains(j));
	
	for (int i = 0; i < jList.size(); i++) 
			System.out.println(jList.get(i).getJobName());



 * Test add, remove	
	// add to the first
	for (int i = 0; i < name2.length(); i++) 
		jList.add(0, new Job(name2.charAt(i) + "", i, i));
	for (int i = 0; i < name2.length(); i++) 
		jList.remove(0);

	for(Job a : jList)
		System.out.println("2:" + a.getJobName());

	//add others
	for (int i = 0; i < name3.length(); i++) 
		jList.add(new Job(name3.charAt(i) + "", i, i));

	// print list.name from the last
//	for (int i = 0; i < jList.size(); i++) 
//		System.out.println(jList.get(i).getJobName());

	// print user iterator
	for(Job a : jList)
		System.out.println("3:" + a.getJobName());*/
	}

}
