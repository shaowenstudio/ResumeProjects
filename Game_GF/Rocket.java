/*
 *Author: Shaowen Liu
 *ID: A20343805 
 *Instructor: Matthew Bauer
 *Reference: CS116.LabKeys
 */
import greenfoot.*;

public class Rocket extends MoveObject{
	public static int x;
	public static int y;
	private boolean playHitMusic;
     
    public void act(){
		x = this.getX(); 	
		y = this.getY();
        hitArrow();
        move(8);
		
        if (atWorldEdge()){
            turn(50);
        }
		
        if (Greenfoot.getRandomNumber(100)<10){
            turn(Greenfoot.getRandomNumber(40)-20);
        }
    }

	public void hitArrow(){
		if(canSee(Arrow.class)){
			if(!playHitMusic){
				Greenfoot.playSound("Hit.wav");
			}
		}
	}    
}
