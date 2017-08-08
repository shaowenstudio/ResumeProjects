import greenfoot.*;

/**
 * Write a description of class NextLevel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NextLevel2 extends Actor
{
    /**
     * Act - do whatever the NextLevel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!getObjectsInRange(50, Arrow.class).isEmpty())
         {
            Greenfoot.setWorld(new Level2());
			Score.target += 1000;//pass this level and gain 1000 scores
		 }
    }    
}
