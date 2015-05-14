package org.TheGivingChild.Engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TGC_Engine extends Game {
	final static int BUTTON_STATES = 2;//corresponds to how many states each button has for the Buttons.pack textures pack.
	//create the stage for our actors
	Stage stage;
	String[] buttonAtlasNamesArray = {"ButtonPressed_MainScreen_Play", "Button_MainScreen_Play",/* "ButtonPressed_MainScreen_Editor", "Button_MainScreen_Editor",*/ "ButtonPressed_MainScreen_Options", "Button_MainScreen_Options"};
	ScreenAdapter[] screens = {new ScreenLevelManager(), new OptionsScreen(this)};
	//fonts
    BitmapFont font;
    
    //button styling	
    TextButtonStyle buttonStyleMainMenyPlay;
    TextButtonStyle buttonStyleMainMenyEditor;
    TextButtonStyle buttonStyleMainMenyOptions;
    Skin skin;
    TextureAtlas buttonAtlas;
    //buttons
    //TextButton buttonMainMenuPlay;
    //TextButton buttonMainMenuOptions;
    //TextButton buttonMainMenuEditor;
    //create tables for the UI
    Table rootTable;
    
	//create needed textures
	Texture mainScreen_Splash;
	
	//testing git pulls comment
	@Override
	public void create () {
		stage = new Stage();
		//create main menu images
		Gdx.input.setInputProcessor(stage);
		//initialize root Table
		rootTable = new Table();
		//button stuff
        font = new BitmapFont();
        skin = new Skin();
        //make an atlas using the button texture pack
        buttonAtlas = new TextureAtlas("Packs/Buttons.pack");
        //define the regions
        skin.addRegions(buttonAtlas);
        //create a table for the buttons
        Table table = new Table();
        //variable to keep track of button height for table positioning
        float buttonHeight = 0;
        int widthDividider = (buttonAtlasNamesArray.length/2);
        //iterate over button pack names in order to check
        for(int i = 0; i < buttonAtlasNamesArray.length-1; i+=BUTTON_STATES){
        	TextButtonStyle bs = new TextButtonStyle();
        	bs.font = font;
        	bs.down = skin.getDrawable(buttonAtlasNamesArray[i]);
        	bs.up = skin.getDrawable(buttonAtlasNamesArray[i+1]);
        	TextButton b = new TextButton("", bs);
        	b.setSize(Gdx.graphics.getWidth()/widthDividider, Gdx.graphics.getHeight()/3);
        	table.add(b).size(Gdx.graphics.getWidth()/widthDividider, Gdx.graphics.getHeight()/3);
        	buttonHeight = b.getHeight();
        	final int j = i;
        	//button to transition to different screens.
        	b.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					setScreen(screens[j/2]);
				}
        	});
        }
        rootTable.add(table);
        rootTable.setPosition(Gdx.graphics.getWidth()/2, buttonHeight/2);
        stage.addActor(rootTable);
	}

	@Override
	public void render () {
		super.render();
		stage.draw();
	}
	
}
