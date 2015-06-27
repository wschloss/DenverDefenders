package org.TheGivingChild.Engine.Attributes;

import org.TheGivingChild.Engine.XML.GameObject;
import org.TheGivingChild.Engine.XML.Level;

import com.badlogic.gdx.utils.ObjectMap;

// Attributes belong to a game object and are allowed to change its properties every frame in the update call
public abstract class Attribute {
	// List of values this attribute needs to operate
	protected ObjectMap<String, String> args;
	// Reference to the object this attribute modifies
	protected GameObject myObject;
	// The condition that this attribute runs on, if any
	protected String trigger;
	// The condition this attribute throws, if any
	protected String throwCondition;
	
	public Attribute(ObjectMap<String, String> args) { 
		this.args = args;
		trigger = args.get("on");
		throwCondition = args.get("throws");
	}
	
	/**
	 * Called once within the GameObject's constructor, used to setup initial values
	 * @param	myObject	the GameObject that currently holds this attribute
	 */
	public void setup(GameObject myObject) {
		this.myObject = myObject;
	};
	/**
	 * Used to simulate the specific behavior for each game object, called each frame
	 * @param	myObject	the GameObject that currently holds the given attribute
	 * @param	allObjects	all of the gameObjects within the given level
	 */
	public abstract void update(Level level);
	
	public String getTrigger() { return trigger; }
}
