package org.TheGivingChild.Engine.XML;

import org.TheGivingChild.Engine.Attributes.WinEnum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public enum LoseEnum {//Cannot have any labels in common with WinEnum
	TIMEOUT{
		private float time;
		public String getXMLDescription(){
			return "timeout";
		}
		public Array<String> getValues(){
			Array<String> temp = new Array<String>();
			temp.add(time+"");
			return temp;
		}
		public void setValues(Array<String> newValues){
			time = Float.parseFloat(newValues.first());
		}
		public boolean checkLose(){
			time = time - Gdx.graphics.getDeltaTime();
			if(time <= 0.0)
				return true;
			return false;
		}
	},
	COLLISIONWITHOBJECTLOSE{
		private int objectID1;
		private int objectID2;
		public String getXMLDescription(){
			return "collisionWithObjectLose";
		}
		public boolean checkLose(){
			return false;
		}
		public void setValues(Array<String> newValues){
			objectID1 = Integer.parseInt(newValues.get(0));
			objectID2 = Integer.parseInt(newValues.get(1));
		}
		public Array<String> getValues(){
			Array<String> temp = new Array<String>();
			temp.add(objectID1+"");
			temp.add(objectID2+"");
			return temp;
		}
	};
	public abstract String getXMLDescription();
	public abstract Array<String> getValues();
	public abstract void setValues(Array<String> newValues);
	public abstract boolean checkLose();
	
	public static LoseEnum newType(String type){
		return valueOf(type.toUpperCase());
	}
}
