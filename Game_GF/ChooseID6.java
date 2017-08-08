import greenfoot.*;
 
public class ChooseID6 extends LoadAndDraw
{
    public void act() 
    {
		if(Greenfoot.mousePressed(this)){
			loadGame();
		}
		readDetailsFromFile(5);
		drawID(getImage(),getID(),110,6);
    }    
	
}