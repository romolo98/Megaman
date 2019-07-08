package com.megaman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;

import static com.megaman.game.Utils.Constants.PPM;
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
		com.megaman.game.gameManager.getCamera().setToOrtho(false, width/com.megaman.game.gameManager.getScale()*1f, height/com.megaman.game.gameManager.getScale()*1f); // MODIFICANDO QUESTO VALORE, SI ALLONTANA LA CAMERA O LA SI AVVICINA
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public void update (float delta) {
		com.megaman.game.gameManager.getWorld().step(1/60f, 6, 2);
		
		inputUpdate(delta);
		gameManager.cameraUpdate(delta);
		gameManager.getRenderer().setView(gameManager.getCamera());
		batch.setProjectionMatrix(gameManager.getCamera().combined);
	}
	
	public void inputUpdate (float delta) {
		int force = 0;
		
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			force-=1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			force+=1;
		}
		gameManager.getMegaman().getMegamanBody().setLinearVelocity(force*5,gameManager.getMegaman().getMegamanBody().getLinearVelocity().y);
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
			gameManager.getMegaman().getMegamanBody().setLinearVelocity(gameManager.getMegaman().getMegamanBody().getLinearVelocity().x, (gameManager.getMegaman().getMegamanBody().getLinearVelocity().y)+4);
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			gameManager.getMegaman().getMegamanBody().setLinearVelocity(gameManager.getMegaman().getMegamanBody().getLinearVelocity().x, (gameManager.getMegaman().getMegamanBody().getLinearVelocity().y)-1);

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();	
	}

}
