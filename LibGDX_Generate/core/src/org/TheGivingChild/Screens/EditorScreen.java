package org.TheGivingChild.Screens;

import java.awt.GridBagLayoutInfo;

import org.TheGivingChild.Engine.TGC_Engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Select;
import com.sun.xml.internal.ws.encoding.policy.SelectOptimalEncodingFeatureConfigurator;

public class EditorScreen extends ScreenAdapter{
	private OrthographicCamera camera;
	
	private TextButton ballButton;
	private TextButton backButton;
	private TextButtonStyle textButtonStyleBack;

	private BitmapFont font;
	private Skin skinBack;
	private Skin skinTable;
	
	private TextureAtlas buttonAtlas;
	private Table editorTable;
	private TGC_Engine mainGame;
	private Texture ballImage;
	private Texture objectImage;
	private Texture boxImage;
	
	private SpriteBatch batch;
	private Array<Rectangle> balls;
	private Array<Rectangle> boxes;
	
//	private Array<String> objBox;
//	private Skin skinBox;
//	private SelectBox<String> selection;

	private Array<Rectangle> grid;
	private Texture gridImage;
	
	private boolean ballOrBox = true;
	
	private float objectSize;
	private float gridSize;
	
	public EditorScreen(final TGC_Engine mainGame) {
		this.mainGame = mainGame;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, mainGame.getHeight(), mainGame.getWidth());
		//createStage();
		createEditorTable();
		//textureSize();
		ballImage = new Texture(Gdx.files.internal("ball.png"));
		batch = new SpriteBatch();
		balls = new Array<Rectangle>();
		boxes = new Array<Rectangle>();
		boxImage = new Texture(Gdx.files.internal("BoxHalf.png"));
		gridImage = new Texture(Gdx.files.internal("Grid.png"));
		grid = new Array<Rectangle>();
		
		selectImage();
		
		objectSize = objectImage.getHeight();
		gridSize = gridImage.getHeight();
		
		fillGrid();
	}
	
	@Override
	public void hide() {
		mainGame.removeTable(editorTable);
	}
	
	@Override
	public void render(float delta) {
//		//		super.render(delta);
//		//		stage.draw();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		if(Gdx.input.isTouched()) {
			System.out.println("X: " + Gdx.input.getX());
			System.out.println("Y: " + Gdx.input.getY());

			spawnObject();
		}
//
		batch.begin();
		
		//		backButton.draw(batch, 1);
		for (Rectangle ball : balls) {
			batch.draw(ballImage, ball.x, ball.y);
		}
		for (Rectangle box : boxes) {
			batch.draw(boxImage, box.x, box.y);
		}
		for (Rectangle gridPiece : grid) {
			batch.draw(gridImage, gridPiece.x, gridPiece.y);
			//System.out.println("Height: " + gridImage.getHeight());
			//System.out.println(gridPiece.height + " " + gridPiece.getAspectRatio() + " " + gridPiece.getX());
		}
		batch.end();

	};
	
	@Override
	public void show() {
		mainGame.addTable(editorTable);
	};
	
//	@Override
//	public void dispose() {
//		batch.dispose();
//		ballImage.dispose();
//
//	}

	private void createEditorTable() {
		editorTable = new Table();
		font = new BitmapFont();
		skinTable = new Skin();
		buttonAtlas = new TextureAtlas("Packs/ButtonsEditor.pack");
		skinTable.addRegions(buttonAtlas);
		TextButton button = createButtons();
		button.setSize(150,300);
		editorTable.add(button);
		editorTable.setPosition(0, 0);
	}
	
	private TextButton createButtons() {
		font = new BitmapFont();
		skinBack = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("Packs/ButtonsEditor.pack"));
		skinBack.addRegions(buttonAtlas);
		textButtonStyleBack = new TextButtonStyle();
		textButtonStyleBack.font = font;
		textButtonStyleBack.up = skinBack.getDrawable("Button_Editor_Back");
		textButtonStyleBack.down = skinBack.getDrawable("ButtonPressed_Editor_Back");
		TextButton button = new TextButton("", textButtonStyleBack);
		
		button.addListener(new ChangeListener() { 			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mainGame.setScreen(mainGame.screens[3]);
			}
		});
		
		TextButtonStyle styleBall = new TextButtonStyle();
		styleBall.font = font;
		styleBall.up = skinBack.getDrawable("Button_Editor_Ball");
		styleBall.down = skinBack.getDrawable("ButtonPressed_Editor_Ball");
		TextButton ballButton = new TextButton("", styleBall);
		
		ballButton.addListener(new ChangeListener() { 			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				selectImage();
			}
		});		
		editorTable.add(ballButton);
		return button;
	}
	
	private void fillGrid() {
		for (int i=0; i*gridSize<Gdx.graphics.getWidth(); i++) {
			for (int j=(int) Gdx.graphics.getHeight(); j>150; j-=gridSize) {
				//System.out.println("grid X" + i*gridSize);
				//System.out.println("grid Y" + j);

				Rectangle gridPiece = new Rectangle(i*gridSize,j, gridSize, gridSize);
				grid.add(gridPiece);
			}
		}
	}
	
	private void spawnObject() {
		Rectangle object = new Rectangle();
		boolean inGrid = false;
		object.width = objectSize ;
		object.height = objectSize;
		object.x = Gdx.input.getX(); //- object.getWidth()/2;
		object.y = Gdx.graphics.getHeight()-Gdx.input.getY(); // - object.getHeight()/2;
		for (Rectangle gridPos : grid) {
			//System.out.println(gridPos.toString());
			if (gridPos.contains(object.x, object.y)) {
				System.out.println("Tripped Square: " + gridPos.toString());
				System.out.println("Mouse Pos:" + object.toString());
				object.x = gridPos.x;
				object.y = gridPos.y;
				inGrid = true;
				break;
			}
		}
		if (inGrid) {
			if (ballOrBox) 
				balls.add(object);
			else
				boxes.add(object);
		}
	}
	
	private void selectImage() {
		if (ballOrBox) {
			objectImage = ballImage;
			ballOrBox = !ballOrBox;
		}
		else {
			objectImage = boxImage;
			ballOrBox = !ballOrBox;
		}
	}
	
//	private void textureSize() {
//		float x = Gdx.graphics.getWidth();
//		float y = Gdx.graphics.getHeight();
//		
//		float changeX = x / mainGame.getWidth();
//		float changeY = y / mainGame.getHeight();
//		
//		objectSize = objectSize * changeX; 
//		gridSize =  gridSize * changeX;
//	}
}
