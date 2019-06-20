package com.megaman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	
	GraphicsManager graphicsManager;
	SpriteBatch batch;
	gameManager gm;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		graphicsManager = new GraphicsManager();
		gm = new gameManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		gm.run(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
