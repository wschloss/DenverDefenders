package org.TheGivingChild.Engine.XML;

import org.TheGivingChild.Engine.TGC_Engine;
import org.TheGivingChild.Engine.Attributes.Attribute;
import org.TheGivingChild.Screens.ScreenAdapterManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Used to store all information on each object within the game<br>
 * extends Actor
 * @author Mostly Kevin D
 */
public class GameObject extends Actor implements Disposable{
	/** Unique ID assigned to each GameObject*/
	private int id;
	private String imageFilename;
	/** Two element velocity array<br> First element is X velocity, second is Y velocity */
	private float[] velocity;
	private float[] initialVelocity;
	private float[] position;
	private float[] initialPosition;
	private TGC_Engine game;
	private AssetManager manager;
	private boolean disposed;
	private Texture texture;
	private float objectScaleX, objectScaleY;
	// Attributes which run every frame
	private Array<Attribute> continuousAttributes;
	// Attributes which update on a triggered condition
	private Array<Attribute> triggeredAttributes;

	public GameObject(ObjectMap<String, String> args, Array<Attribute> continuousAttributes, Array<Attribute> triggeredAttributes){
		disposed = false;
		//set values from args
		id = Integer.parseInt(args.get("id"));
		imageFilename = "LevelImages/" + args.get("image");
		position = new float[] { Float.parseFloat(args.get("x")), Float.parseFloat(args.get("y")) };
		
		this.continuousAttributes = continuousAttributes;
		this.triggeredAttributes = triggeredAttributes;
		
		//initialize a velocity of 0
		velocity = new float[] { 0, 0 };
		//get the reference to the game
		game = ScreenAdapterManager.getInstance().game;
		//load the required texture for this object
		manager = game.getAssetManager();
		manager.load(imageFilename, Texture.class);
		manager.finishLoadingAsset(imageFilename);
		texture = manager.get(imageFilename, Texture.class);
		//scale the object to the width of the new screen
		objectScaleX = Gdx.graphics.getWidth()/1024f;
		//scale the object to the height of the new screen
		objectScaleY = Gdx.graphics.getHeight()/600f;
		//scale small objects an additional 50%
		if(texture.getWidth() <= 32 && texture.getHeight() <= 32){
			objectScaleX *= 2;
			objectScaleY *= 2;
		}
		
		//set the bounds to be as large as the textures size
		setBounds(getX(), getY(), texture.getWidth()*objectScaleX, texture.getHeight()*objectScaleY);
		
		setPosition( position[0], position[1]);
		initialPosition = position;
		// Run any setup necessary for attributes
		for(Attribute att : continuousAttributes) {
			att.setup(this);
		}
		for (Attribute att : triggeredAttributes) {
			att.setup(this);
		}
		
		initialVelocity = new float[] {velocity[0],velocity[1]};
	}

	public void update(Level level){
		for (Attribute att : continuousAttributes)
			att.update(level);
	}
	
	// Registers the triggered attributes under the condition in the map.  Adds the condition if it is not set yet.
	public void register(ObjectMap<String, Array<Attribute> > observerMap) {
		for (Attribute att : triggeredAttributes) {
			String trigger = att.getTrigger();
			if (!observerMap.containsKey(trigger))
				observerMap.put(trigger, new Array<Attribute>());
			observerMap.get(trigger).add(att);
		}
	}

	public int getID() {
		return id;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	@Override
	public void dispose(){
		disposed = true;
	}
	public void resetObject(){
		setVelocity(initialVelocity);
		setPosition(initialPosition[0], initialPosition[1]);
		disposed = false;
	}
	public boolean isDisposed(){
		return disposed;
	}
	public float[] getVelocity() {
		return velocity;
	}
	public void setVelocity(float[] newVelocity) {
		velocity[0] = newVelocity[0];
		velocity[1] = newVelocity[1];
	}
	public Texture getTexture(){
		return texture;
	}
	public float getTextureWidth(){
		return texture.getWidth()*objectScaleX;
	}
	public float getTextureHeight(){
		return texture.getHeight()*objectScaleY;
	}
	public float[] getInitialPosition(){
		return initialPosition;
	}
}
