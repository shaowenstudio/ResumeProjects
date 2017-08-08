/*
 *Author: Shaowen Liu
 *ID: A20343805 
 *Instructor: Matthew Bauer
 *Reference: CS116.LabKeys
 */
import greenfoot.*;

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arrow extends MoveObject
{
	public static String currentWorldName;
	public static int x;
	public static int y;
    //an attribute to the Arrow to store a reference to a Score object
	private int musicCount;
	private Score score;
	int i =1;
	private boolean playMainMusic,playBalloonMusic;
	//new constructor for the Arrow that has a Counter object reference as an attribute
	public Arrow(){	//default constructor
	}	
	
	public Arrow(Score score){
		this.score=score;
	}
	
	public void act() {
		x = this.getX(); 	
		y = this.getY();
		if(canSee(Rocket.class))
			score.add(-10);
		
		hitWall();
		if (atWorldEdge()){ 
            turn(30);
        }
		if (Greenfoot.isKeyDown("right")){
                turn(3);
				move(2);
        }
        if (Greenfoot.isKeyDown("left")){
                turn(-3);
				move(2);
        }
	
		if(getWorld() instanceof Level1)
			currentWorldName = "Level1";
		else if(getWorld() instanceof Level2)
			currentWorldName = "Level2";
		else if(getWorld() instanceof Level3)
			currentWorldName = "Level3";
		else if(getWorld() instanceof Final_level)
			currentWorldName = "Final_level";
		else if(getWorld() instanceof OverBackground)  //condition to save
			currentWorldName = "OverBackground";
    }    
	
	public void hitWall(){
		if (canSee(Wall.class)){
            turn(30);
			score.add(-1);
        }
		if (canSee(Wall2.class)){
            turn(30);
			score.add(-1);
        }
	}
}
