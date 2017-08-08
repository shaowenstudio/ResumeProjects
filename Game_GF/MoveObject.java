/*
 *Author: Shaowen Liu
 *ID: A20343805 
 *Instructor: Matthew Bauer
 *Reference: CS116.LabKeys
 */
import greenfoot.*;

public class MoveObject extends Actor
{
   
    public boolean atWorldEdge()
    {
        if(getX() < 10 || getX() > getWorld().getWidth() - 160)
            return true;
        if(getY() < 10 || getY() > getWorld().getHeight() - 10)
            return true;
        else
            return false;
    }
   
    public boolean canSee(Class clss)
    {
        Actor actor = getOneObjectAtOffset(0, 0, clss);
        return actor != null;        
    }
 
}
