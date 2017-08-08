import greenfoot.*;
 
public class ChooseID1 extends LoadAndDraw
{	
    public void act() 
    {
		if(Greenfoot.mousePressed(this)){
			loadGame();
		}
		
		readDetailsFromFile(0);
		
		drawID(getImage(),getID(),110,6);
    }    
}