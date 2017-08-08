import greenfoot.*;
import java.io.FileOutputStream;
import java.io.PrintWriter; // append - true   
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class PauseBackground extends World
{
    private BackToGame backToGame = new BackToGame();
    private SaveAndExit saveAndExit = new SaveAndExit();
	public static int uniqueID=1;
	
    public PauseBackground()
    {    
        super(600, 400, 1); 
        prepare();
    }

    public void act(){   //click backToGame and back to the game which user play before.  LOAD
            if(Greenfoot.mousePressed(backToGame)){
				if(Arrow.currentWorldName.equals("Level1")){
					Greenfoot.setWorld(new Level1(Arrow.x, Arrow.y, Rocket.x, Rocket.y, Score.target));
				}				
				else if(Arrow.currentWorldName.equals("Level2")){
					Greenfoot.setWorld(new Level2(Arrow.x, Arrow.y, Rocket.x, Rocket.y, Score.target));
				}				
				else if(Arrow.currentWorldName.equals("Level3")){
					Greenfoot.setWorld(new Level3(Arrow.x, Arrow.y, Rocket.x, Rocket.y, Score.target));
				}
			}
            else if(Greenfoot.mousePressed(saveAndExit)){
				Greenfoot.setWorld(new OverBackground());
			}
    }
	
    private void prepare() { //prepare elements for the world 
        addObject(backToGame, 290, 115);
        addObject(saveAndExit, 289, 219);
    }
		
	public static void save(){ // use static so that other class can use it too
		//save objects&world to file when the saveAndExit key is pressed
		//arrowX, arrowY, rocketX, rocketY, score & whichWorl
		PauseBackground.generateUniqueID();
		try{
			FileOutputStream fos = new FileOutputStream("objects_world.txt",true);//appending
			PrintWriter pw = new PrintWriter(fos);
			pw.print("ID:");	//the first line is "ID:123"  use indexOf&parseInt to read the number
			pw.println(PauseBackground.uniqueID);
			pw.println(Arrow.x);
			pw.println(Arrow.y);
			pw.println(Rocket.x);
			pw.println(Rocket.y);
			pw.println(Score.target);
			pw.println(Arrow.currentWorldName);
			
			pw.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Fail to write a .txt");
		}
	}
	public static void generateUniqueID(){	//every time it call the save method, ID++
		//check EMPTY    YES->go out of the loop, NO->check biggest number and let uniqueID=biggest+1
		String thisLine="";
		String idString="";
		int idNumber= 1;
		int biggestNumber = 0;
		
		try{
			File readFile = new File("objects_world.txt");
			Scanner scan = new Scanner(readFile);
			while(scan.hasNextLine()){	//get the biggest ID number in this file
				thisLine = scan.nextLine();
				if(thisLine.charAt(0)=='I'){
					idString = thisLine.substring(thisLine.indexOf(':')+1);
					idNumber = Integer.parseInt(idString);  //current ID number in the file
					if(idNumber>biggestNumber){	//compare the bigger number
						biggestNumber = idNumber; 
					}
				}
			}	
		}catch(Exception e){
			System.out.println("Something is wrong with getUniqueID()");
		}
		uniqueID = biggestNumber+1;  //ID is currently biggest number in this file 
	}
}
