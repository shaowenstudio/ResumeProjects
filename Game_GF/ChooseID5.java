import greenfoot.*;
 
public class ChooseID5 extends LoadAndDraw
{
    public void act() 
    {
		if(Greenfoot.mousePressed(this)){
			loadGame();
		}
		readDetailsFromFile(4);
		drawID(getImage(),getID(),110,6);
    }    
}