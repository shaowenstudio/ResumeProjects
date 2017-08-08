import greenfoot.*;

public class WorldLevel extends World
{
    public static Score score = new Score( );
    private int arrowX;
    private int arrowY;
    private int rocketX;
    private int rocketY;

    public WorldLevel()
    {    
        super(950, 670, 1); 
    }
    
    public WorldLevel(int arrowX, int arrowY, int rocketX, int rocketY, int currentScore) {
		super(950, 670, 1);
        this.arrowX = arrowX;
		this.arrowY = arrowY;
		this.rocketX = rocketX;
		this.rocketY = rocketY;
		Score.target = currentScore;
		addObject(new Arrow(score),arrowX,arrowY);		//prepare special object here
		addObject(new Rocket(),rocketX,rocketY);
	}


	public int getArrowX() {
		return arrowX;
	}

	public void setArrowX(int arrowX) {
		this.arrowX = arrowX;
	}

	public int getArrowY() {
		return arrowY;
	}

	public void setArrowY(int arrowY) {
		this.arrowY = arrowY;
	}

	public int getRocketX() {
		return rocketX;
	}

	public void setRocketX(int rocketX) {
		this.rocketX = rocketX;
	}

	public int getRocketY() {
		return rocketY;
	}

	public void setRocketY(int rocketY) {
		this.rocketY = rocketY;
	}

	public void prepare(){
        addObject(new MenuBoard(),911,337);
        addObject(score,877,18);
		addObject(new PauseOrSave(),880,58);
    }
}
