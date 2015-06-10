package org.TheGivingChild.Screens;

import com.badlogic.gdx.math.Rectangle;

public class MinigameRectangle extends Rectangle {

	private boolean occupied;
	private ChildSprite occupant;
	
	public MinigameRectangle(float x, float y, float width, float height) {
		super(x,y,width,height);
	}
	
	public boolean isOccupied()
	{
		return occupied;
	}
	
	public ChildSprite getOccupant()
	{
		//return new ChildSprite(occupant);
		return occupant;
	}
	
	public void setOccupied(ChildSprite s)
	{
		//occupant = new ChildSprite(s);
		occupant = s;
		occupied = true;
	}
	
	public void empty()
	{
		occupied = false;
		occupant = null;
	}
	
}
