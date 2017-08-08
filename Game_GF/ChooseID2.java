import greenfoot.*;
 
public class ChooseID2 extends LoadAndDraw
{
    public void act() 
    {
		if(Greenfoot.mousePressed(this)){
			loadGame();
		}
		readDetailsFromFile(1);
		drawID(getImage(),getID(),110,6);
    }       
}