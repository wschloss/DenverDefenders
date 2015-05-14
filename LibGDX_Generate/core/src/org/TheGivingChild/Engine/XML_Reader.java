package org.TheGivingChild.Engine;

import java.util.ArrayList;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;

import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

//Use this to read the XML File into a Level
//will read in XML fle, translate into LevelGoals, GameObjects, and other data, compile them into a level, then pass that level up 
public class XML_Reader {	
	
	private XmlReader reader = new XmlReader();
	private String xml_file;
	private Element root;//this is the root of the tree that is created by reader.parse(xml_file)
	
	//the main method is for testing only
	public static void main(String cheese[]){
		XML_Reader swag = new XML_Reader();
		String filename = "testMinigame.xml";
		swag.setupNewFile(filename);
		ArrayList<GameObject> testObjects = swag.compileGameObjects();
		System.out.println(testObjects.size());
		for(GameObject current:testObjects){
			System.out.println("HP: " + current.getHealth() + "\nSpeed: " + current.getSpeed() + "Path: ");
			for(Point currentPoint:current.getPath()){
				System.out.println(currentPoint.getX() + ", " + currentPoint.getY());
			}
			
		}
	}
	
	public void setupNewFile(String XML_Filename){//will read in a new XML file as a big string, will try to leave space for the DHD, needs to be called each time you want to read in a minigame
		xml_file ="";
		try{
			BufferedReader fileReader = new BufferedReader(new FileReader(XML_Filename));
			while(fileReader.ready()){
				xml_file+=fileReader.readLine();//might need to clean up the xml here
			}
		}catch(Exception e){
			System.out.println("Error opening xml file. Filename: " + XML_Filename + "Exception: " + e);
		}
		root = reader.parse(xml_file);
	}
	
	private ArrayList<GameObject> compileGameObjects(){//will parse through xml_file and get all game objects and their attributes
		ArrayList<GameObject> listOfObjects = new ArrayList<GameObject>();
		com.badlogic.gdx.utils.Array<Element> gameObjects = root.getChildrenByName("GameObject");
		for(Element child:gameObjects){
			int health = Integer.parseInt(child.getAttribute("health"));
			double speed = Double.parseDouble(child.getAttribute("speed"));
			ArrayList<Point> path = stringToPath(child.getAttribute("path"));
			//GameObject temp = new GameObject(health,path,speed);//ASSUMING PATH IS NOT EMPTY
			listOfObjects.add(new GameObject(health,path,speed));
		}
		return listOfObjects;
	}
	
	//helper method for compileGameObjects
	private ArrayList<Point> stringToPath(String sPath){//Working
		ArrayList<Point> newPath = new ArrayList<Point>();
		String points[] = sPath.split(";");
		String temp[];
		for(int i = 0; i < points.length; i++){
			temp = points[i].split(",");
			Point temp_P = new Point();//herd u liek temps.
			temp_P.setLocation(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
			newPath.add(temp_P);
		}
		return newPath;
	}
	
	private LevelGoal compileLevelGoal(){//will parse through xml_file and get the win/loss conditions
		LevelGoal levelGoal = new LevelGoal();
		//that code tho
		return levelGoal;
	}
	
	public Level getLevel(){//compiles all the data into a level and returns it
		//return new Level("PLACEHOLDER","PLACEHOLDER",new LevelGoal(),new ArrayList<GameObject>());
		return null;
	}
}
