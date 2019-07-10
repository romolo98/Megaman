package com.megaman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.megaman.game.Utils.Constants.*;
public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	float elapsed;
	float salto;
	float caduta;
	float saltoMoto;
	float shoot;
	int cont;
	boolean destroyBullet;
	boolean firstspawn;
	boolean spawnBullet;
	boolean shootDone;
	boolean[] shootThisBullet;
	
	public GraphicsManager (){
		
		elapsed = 0;
		salto = 0;
		saltoMoto = 0;
		caduta = 0;
		shoot = 0;
		cont = 0;
		firstspawn = true;
		destroyBullet = false;
		spawnBullet = false;
		shootDone = false;
		shootThisBullet = new boolean[50];
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
		
	}

	public void drawMegaman(SpriteBatch batch, Controller controller, Megaman megaman) {
		
		elapsed += Gdx.graphics.getDeltaTime();
		shootDone = false;
		
		if (controller.controlli[WALK_START]) {
			drawImage(batch, megaman, graphicLoader.getInizioWalk(), controller.getDirection());
		}
		if (controller.getControlli(SHOOT)){
			drawImage(batch, megaman, graphicLoader.getShoot().getKeyFrame(elapsed), controller.getDirection());
			shoot += Gdx.graphics.getDeltaTime();
			if (graphicLoader.getShoot().isAnimationFinished(shoot)) {
				shoot = 0;
				shootDone = true;
			}
		}
		if (controller.getControlli(FALL)) {
			drawImage(batch, megaman, graphicLoader.getFall().getKeyFrame(elapsed), controller.getDirection());
		}
		
		if (controller.getControlli(WALK) && !controller.getControlli(FALL) && !controller.getControlli(JUMP)) {
			if (controller.getControlli(WALK_SHOOT)) {
				drawImage(batch, megaman, graphicLoader.getShooting().getKeyFrame(elapsed,true), controller.getDirection());
			}
			else if (controller.getControlli(WALK_JUMP)) {
				drawImage(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
			}
			
			else { 
				drawImage(batch, megaman, graphicLoader.getWalk().getKeyFrame(elapsed, true), controller.getDirection());
			}
		}
		if (controller.getControlli(JUMP)) {
			drawImage(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
		}
		if (controller.getControlli(IDLE)){
			drawImage(batch, megaman, graphicLoader.getIdle().getKeyFrame(elapsed, true), controller.getDirection());
		}
	
	}
	public void drawBullet (SpriteBatch batch, Bullet bullet, Megaman megaman, boolean dir) {
		batch.draw(graphicLoader.getBullet(), (int)bullet.getPositionX(),(int)bullet.getPositionY(), 64, 64, 0, 0, 64, 64, dir, false);	

}
	public void drawImage (SpriteBatch batch, Megaman megaman, Texture texture, boolean dir) {
			batch.draw(texture, megaman.getMegamanBody().getPosition().x * PPM - texture.getWidth()/2, megaman.getMegamanBody().getPosition().y * PPM - texture.getHeight()/2, 64, 64, 0, 0, 64, 64, dir, false);
	}
	public void drawHud (SpriteBatch batch, Megaman megaman, HUD hud) {
			batch.draw(graphicLoader.getHud(hud.getLife()), 0,5);
	}
	
	public GraphicLoader getGL() {
		return graphicLoader;
	}
	
	public boolean getShootDone () {
		return shootDone;
	}
}










