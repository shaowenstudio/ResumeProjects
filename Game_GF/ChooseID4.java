import greenfoot.*;
 
public class ChooseID4 extends LoadAndDraw
{
    public void act() 
    {
		if(Greenfoot.mousePressed(this)){
			loadGame();
		}
		readDetailsFromFile(3);
		drawID(getImage(),getID(),110,6);
    }     
}