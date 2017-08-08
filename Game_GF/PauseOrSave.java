import greenfoot.*;

public class PauseOrSave extends Actor
{
    public void act() 
    {
        if(Greenfoot.mousePressed(this)){
			Greenfoot.setWorld(new PauseBackground());
		}
	}    
}
