import greenfoot.*;

public class Level2 extends WorldLevel
{
    public Level2()
    {    
        super(); 
        prepare();
    }
	public Level2(int arrowX, int arrowY, int rocketX, int rockteX, int score) {
		super(arrowX, arrowY, rocketX, rockteX, score);
		nonDefaultPrepare();
	}
	public void prepare(){
		super.prepare();
		addObject(new Arrow(WorldLevel.score),40,632);
		
		for(int j=0;j<6;j++){
			addObject(new Wall2(), 393, 76+(107*j));
		}
				
		addObject( new NextLevel3(),466,32);
		
		addObject(new Rocket(),40,50);
	}
	public void nonDefaultPrepare(){
		super.prepare();
		addObject( new NextLevel3(),466,32);
		
        for(int j=0;j<6;j++){
			addObject(new Wall2(), 393, 76+(107*j));
		}
		
	}
}
