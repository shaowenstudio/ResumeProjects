import greenfoot.*;
 
public class ChooseID3 extends LoadAndDraw
{
    public void act() 
    {
		if(Greenfoot.mousePressed(this)){
			loadGame();
		}
		readDetailsFromFile(2);
		drawID(getImage(),getID(),110,6);
    }       
}