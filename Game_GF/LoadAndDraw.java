import greenfoot.*;
import java.awt.Color;
import java.io.File;
import java.util.Scanner;

public class LoadAndDraw extends Actor
{
    private int idNumber;
	private int arrowX;
	private int arrowY;
	private int rocketX;
	private int rocketY;
	private int numberOfScore;
	private String worldName;
	private static final Color TRANSPARENT = new Color(0,0,0,0);
    private GreenfootImage background;
     
	public int getID(){
		return idNumber;
	}
	public int getScore(){
		return numberOfScore;
	}
	// draw picture 
	public void drawID(GreenfootImage myImage,int number, int x, int y){
		background = myImage;
		GreenfootImage image = new GreenfootImage(background);
        GreenfootImage text = new GreenfootImage("" + number, 30, Color.BLACK, TRANSPARENT);

        image.drawImage(text, x, y);
        setImage(image);
	}
	// if this image clicked load the game
	public void loadGame(){  //we can improve this method use ArrayList
		if(worldName.equals("Level1")){  //enter the game
			Greenfoot.setWorld(new Level1(arrowX, arrowY, rocketX, rocketY, numberOfScore));
		}		
		if(worldName.equals("Level2")){
			Greenfoot.setWorld(new Level2(arrowX, arrowY, rocketX, rocketY, numberOfScore));
		}
		if(worldName.equals("Level3")){
			if(arrowX>701&&arrowY<63){
				Greenfoot.setWorld(new Level3(arrowX-60, arrowY, rocketX, rocketY, numberOfScore));
				System.out.println("Sorry, this is an completed game. "+
				"\nPlease choose another game. \nYou can only load the most recent 6 games ");
			}
			else
				Greenfoot.setWorld(new Level3(arrowX, arrowY, rocketX, rocketY, numberOfScore));
		}
	}
	
	public void readDetailsFromFile(int rencentLevel){
		int lineNumber = 0;
		String thisLine="";
		try{
			File readFile = new File("objects_world.txt");
			Scanner scan = new Scanner(readFile);
			while(scan.hasNextLine()){	//check how many lines in this file so that we can know number of games
				thisLine = scan.nextLine();
				lineNumber++;
			}	
		}catch(Exception e){
			System.out.println("Something is wrong with chooseID1");
		}
		
		int gamesNumber = lineNumber/7;  // data of one game = 7 lines
		if(gamesNumber>rencentLevel){
			try{		//read details of data from file
				File readFile = new File("objects_world.txt");
				Scanner scan = new Scanner(readFile);
				for(int i=0; i<gamesNumber-rencentLevel;i++){	//ensure ChooseID1 is the most recently game
					if(scan.hasNextLine()){	//get the biggest ID number in this file
						thisLine = scan.nextLine();
						idNumber = Integer.parseInt(thisLine.substring(thisLine.indexOf(':')+1));
						arrowX = Integer.parseInt(scan.nextLine());
						arrowY = Integer.parseInt(scan.nextLine());
						rocketX = Integer.parseInt(scan.nextLine());
						rocketY = Integer.parseInt(scan.nextLine());
						numberOfScore = Integer.parseInt(scan.nextLine());
						worldName = scan.nextLine();
					}
				}				
			}catch(Exception e){
				System.out.println("Something is wrong with getUniqueID()");
			}
		}
	}   
}