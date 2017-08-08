import greenfoot.*;

/**
 * Write a description of class YourScoreIs here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class YourScoreIs extends LoadAndDraw
{
    /**
     * Act - do whatever the YourScoreIs wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        drawID(getImage(), Score.target, 200, 8);
    }    
}
