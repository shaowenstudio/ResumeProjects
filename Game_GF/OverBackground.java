import greenfoot.*;

public class OverBackground extends World
{
    private Restart restart = new Restart();
	
    public OverBackground()
    {   
        super(600, 400, 1);  
        prepare();
		saveScore();
    }
	public void act(){
		if(Greenfoot.mousePressed(restart)){
			Greenfoot.setWorld(new StartBackground());
			Score.target=0;
		}
	}
	public void saveScore(){
		PauseBackground.save();
	}
    public void prepare(){
        addObject(new Congratulate(),300,85 );
        addObject(new YourScoreIs(),345,174);
		addObject(new RememberYourID(),227,239);
		addObject(restart,445,365);
    }
}
