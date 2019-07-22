package com.megaman.logic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.megaman.graphic.GraphicsManager;

import static com.megaman.game.Utils.Constants.PPM;

import java.awt.DisplayMode;
public class Game extends ApplicationAdapter {
	
	GraphicsManager graphicsManager;
	SpriteBatch batch;
	gameManager gameManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		graphicsManager = new GraphicsManager();
		gameManager = new gameManager(graphicsManager);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		gameManager.run(batch);
		batch.end();
	}
	
	@Override
	public void resize (int width, int height) {
		gameManager.getCamera().setToOrtho(false, width/com.megaman.logic.gameManager.getScale(), height/com.megaman.logic.gameManager.getScale()); // MODIFICANDO QUESTO VALORE, SI ALLONTANA LA CAMERA O LA SI AVVICINA
		gameManager.getCameraMenu().setToOrtho(false, width/com.megaman.logic.gameManager.getScale(), height/com.megaman.logic.gameManager.getScale());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameManager.disposer();
	}
	
	public void update (float delta) {
		
		gameManager.getWorld().step(1/60f, 6, 2);
		gameManager.bossDestroyer();
		gameManager.enemiesDestroyer();
		gameManager.bossBulletDestroyer();
		gameManager.MegamanBulletDestroyer();
		
		if (gameManager.getGame()) {
			gameManager.cameraUpdate(delta);
			gameManager.getRenderer().setView(gameManager.getCamera());
			batch.setProjectionMatrix(gameManager.getCamera().combined);
		}
		else {
			gameManager.cameraMenuUpdate();
			gameManager.getRenderer().setView(gameManager.getCameraMenu());
			batch.setProjectionMatrix(gameManager.getCameraMenu().combined);
		}
	}


}
