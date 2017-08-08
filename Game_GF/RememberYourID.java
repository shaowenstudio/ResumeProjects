import greenfoot.*;

/**
 * Write a description of class RememberYourID here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RememberYourID extends LoadAndDraw
{
    /**
     * Act - do whatever the RememberYourID wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        drawID(getImage(), PauseBackground.uniqueID, 258, 17);
    }    
}
