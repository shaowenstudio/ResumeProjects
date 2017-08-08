import greenfoot.*;

public class Level1 extends WorldLevel
{
    public Level1()
    {    
        super(); 
        prepare();
    }
	public Level1(int arrowX, int arrowY, int rocketX, int rockteX, int score) {
		super(arrowX, arrowY, rocketX, rockteX, score);
		nonDefaultPrepare();
	}
	public void prepare(){
		super.prepare();
		addObject(new NextLevel2(),27,22);
		addObject(new Arrow(WorldLevel.score),40,632);
        addObject(new Wall(), 253, 579);
        addObject(new Wall(), 524, 471);
        addObject(new Wall(), 253, 363);
        addObject(new Wall(), 253, 255);
        addObject(new Wall(), 524, 147);
        addObject(new Wall(), 253, 70);
		addObject(new Rocket(),40,50);
	}
	public void nonDefaultPrepare(){
		super.prepare();
		addObject(new NextLevel2(),27,22);
        addObject(new Wall(), 253, 579);
        addObject(new Wall(), 524, 471);
        addObject(new Wall(), 253, 363);
        addObject(new Wall(), 253, 255);
        addObject(new Wall(), 524, 147);
        addObject(new Wall(), 253, 70);
	}
}
