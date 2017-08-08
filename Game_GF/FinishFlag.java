import greenfoot.*;

/**
 * Write a description of class FinishFlag here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FinishFlag extends Actor
{
    /**
     * Act - do whatever the FinishFlag wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!getObjectsInRange(50, Arrow.class).isEmpty())
         {
            Greenfoot.setWorld(new Final_level());
		 }
    }    
}
