import greenfoot.*;

public class StartBackground extends World
{
	private Level01 level1 = new Level01();
	private Level02 level2 = new Level02();
	private Level03 level3 = new Level03();
	private ClickMe clickMe = new ClickMe();
	
    public StartBackground()
    {    
        super(750, 550, 1); 
        prepare();
		// Greenfoot.start(); //Run (or resume) the execution
    }
	
    public void act(){
			if(Greenfoot.mousePressed(level1))
				Greenfoot.setWorld(new Level1());
			else if(Greenfoot.mousePressed(clickMe))
				Greenfoot.setWorld(new Level1());
			else if(Greenfoot.mousePressed(level2))
				Greenfoot.setWorld(new Level2());
			else if(Greenfoot.mousePressed(level3))
				Greenfoot.setWorld(new Level3());
	}
	
    public void prepare(){
        addObject(new LoadPreviousGame(), 544, 192);
        addObject(new ChooseID4(), 652, 273);
        addObject(new ChooseID5(), 652, 368);
        addObject(new ChooseID6(), 652, 474);
        addObject(new ChooseID1(), 451, 273);
        addObject(new ChooseID2(), 451, 368);
        addObject(new ChooseID3(), 451, 474);
		addObject(clickMe, 371, 100);

        addObject(new ChooseALevel(),141,192);
        addObject(level1, 141, 273);
        addObject(level2, 141, 368);
        addObject(level3, 141, 474);
        addObject(new Arrow(), 50, 50);
    }
}
