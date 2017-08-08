import greenfoot.*;

/**
 * Write a description of class NextLevel3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NextLevel3 extends Actor
{
    /**
     * Act - do whatever the NextLevel3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!getObjectsInRange(50, Arrow.class).isEmpty())
        {
            Greenfoot.setWorld(new Level3());
			Score.target += 1000;//pass this level and gain 1000 scores
		}
    }    
}
