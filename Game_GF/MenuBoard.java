import greenfoot.*;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class MenuBoard extends LoadAndDraw
{
    private int [] topTen = new int[10000];  //size is big enough
	private boolean putTopTen; // update topTen every time game start
	
    public void act() 
    {
		if(!putTopTen){
			topTenScore();
			drawID(getImage(), topTen[9999], 52, 180);
			drawID(getImage(), topTen[9998], 52, 230);
			drawID(getImage(), topTen[9997], 52, 280);
			drawID(getImage(), topTen[9996], 52, 330);
			drawID(getImage(), topTen[9995], 52, 380);
			drawID(getImage(), topTen[9994], 52, 430);
			drawID(getImage(), topTen[9993], 52, 480);
			drawID(getImage(), topTen[9992], 52, 530);
			drawID(getImage(), topTen[9991], 52, 580);
			drawID(getImage(), topTen[9990], 52, 630);
			putTopTen = true;
		}

		
    }    
	public void topTenScore(){
		int lineNumber = 0;
		String thisLine="";
		int n = 0; //n is nth game, in order to find and store scores into array.
		try{
			File readFile = new File("objects_world.txt");
			Scanner scan = new Scanner(readFile);
			while(scan.hasNextLine()){	//check how many lines in this file so that we can know number of games
				thisLine = scan.nextLine();
				lineNumber++;
				if(lineNumber==(6+7*n)){
					topTen[n] = Integer.parseInt(thisLine);
					n++;
				}
			}	
		}catch(Exception e){
			System.out.println("Something is wrong with topTenScore");
		}
		Arrays.sort(topTen);
	}
}
