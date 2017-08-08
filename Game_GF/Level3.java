import greenfoot.*;

public class Level3 extends WorldLevel
{
    public Level3()
    {    
        super(); 
        prepare();
    }
	public Level3(int arrowX, int arrowY, int rocketX, int rockteX, int score) {
		super(arrowX, arrowY, rocketX, rockteX, score);
		nonDefaultPrepare();
	}
    public void prepare(){
		super.prepare();
        addObject(new Arrow(WorldLevel.score),40,632);
        addObject(new FinishFlag(),767,38);
        
        for(int j=0;j<3;j++){
            addObject(new Wall2(), 296, 183+(214*j));
        }
        for(int j=0;j<3;j++){
            addObject(new Wall(), 504, 76+(214*j));
        }
        addObject(new Rocket(),40,50);
    }
	public void nonDefaultPrepare(){
		super.prepare();
        addObject(new FinishFlag(),767,38);
        for(int j=0;j<3;j++){
            addObject(new Wall2(), 296, 183+(214*j));
        }
        for(int j=0;j<3;j++){
            addObject(new Wall(), 504, 76+(214*j));
        }
	}
}
