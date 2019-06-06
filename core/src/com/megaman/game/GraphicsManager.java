package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	Megaman megaman;
	Controller controller;
	float elapsed;
	
	public GraphicsManager (){
		elapsed = 0;
		controller = new Controller();
		megaman = new Megaman();
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
	}
	public void draw(SpriteBatch batch) {
		elapsed += Gdx.graphics.getDeltaTime();
		
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			batch.draw(graphicLoader.getInizioWalk(),megaman.getPositionX(),megaman.getPositionY());
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			batch.draw(graphicLoader.getWalk().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
		}
		else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			controller.muoviMegaman(Keys.SPACE, megaman);
			batch.draw(graphicLoader.getJump().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
		}
		else {
			batch.draw(graphicLoader.getIdle().getKeyFrame(elapsed/2,true), megaman.getPositionX(), megaman.getPositionY());
		}
		
	}
}









