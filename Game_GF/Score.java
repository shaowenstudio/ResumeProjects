import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class Score extends Actor {
    private static final Color TRANSPARENT = new Color(0,0,0,0);
    private GreenfootImage background;
    public  static int target ;
	private int musicCount;
	private boolean playMainMusic;
	
    public Score() {
        background = getImage();  // get image from class
        target = 0;
        updateImage();
    }
    
    public void act() {
		if(!playMainMusic ){
			Greenfoot.playSound("Main.mp3");
			playMainMusic = true;
		}
		musicCount++;
		if(musicCount==13500 && !(getWorld() instanceof OverBackground) ){ 
		 // restart music if not in OverBackground
			Greenfoot.playSound("Main.mp3");
			musicCount=0;
        }
		updateImage();
    }

    public void add(int score) {
        target += score;
    }
	
    private void updateImage() {
        GreenfootImage image = new GreenfootImage(background);
        GreenfootImage text = new GreenfootImage("" + target, 22, Color.BLACK, TRANSPARENT);

        image.drawImage(text, 2*(image.getWidth()-text.getWidth())/3,(image.getHeight()-text.getHeight())/2);
        setImage(image);		
    }
}