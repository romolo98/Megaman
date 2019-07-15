package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.megaman.game.Utils.Constants.*;
public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	float spawn;
	float fall;
	float elapsed;
	float salto;
	float caduta;
	float saltoMoto;
	float shoot;
	float levelStart;
	int cont;
	boolean destroyBullet;
	boolean firstSpawn;
	boolean spawnBullet;
	boolean shootDone;
	boolean start;
	boolean[] shootThisBullet;
	
	public GraphicsManager (){
		
		levelStart = 0;
		elapsed = 0;
		salto = 0;
		saltoMoto = 0;
		caduta = 0;
		shoot = 0;
		cont = 0;
		spawn = 0;
		firstSpawn = true;
		destroyBullet = false;
		spawnBullet = false;
		shootDone = false;
		start = false;
		shootThisBullet = new boolean[50];
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
		
	}

	public void drawMegaman(SpriteBatch batch, Controller controller, Megaman megaman) {
		elapsed += Gdx.graphics.getDeltaTime();
		shootDone = false;
		
		if (firstSpawn) {
			drawImage(batch, megaman, graphicLoader.getSpawn().getKeyFrame(spawn), controller.getDirection());
			spawn += Gdx.graphics.getDeltaTime();
			controller.direction = false;
			if (graphicLoader.getSpawn().isAnimationFinished(spawn)) {
				spawn = 0;
				firstSpawn = false;
				start = true;
			}
		}
		else {
			
			if (controller.controlli[WALK_START]) {
				drawImage(batch, megaman, graphicLoader.getInizioWalk(), controller.getDirection());
			}
			if (controller.getControlli(SHOOT)){
				drawImage(batch, megaman, graphicLoader.getShoot().getKeyFrame(shoot), controller.getDirection());
				shoot += Gdx.graphics.getDeltaTime();
				if (graphicLoader.getShoot().isAnimationFinished(shoot)) {
					shoot = 0;
					shootDone = true;
				}
			}
			if (controller.getControlli(FALL)) {
				drawImage(batch, megaman, graphicLoader.getFall().getKeyFrame(fall), controller.getDirection());
				fall+= Gdx.graphics.getDeltaTime();
			}
			
			if (controller.getControlli(WALK) && !controller.getControlli(FALL) && !controller.getControlli(JUMP) && !controller.getControlli(JUMP_SHOOT) && !controller.getControlli(FALL_SHOOT)) {
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
			if (controller.getControlli(JUMP) && !controller.getControlli(JUMP_SHOOT) && !controller.getControlli(FALL)) {
				drawImage(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
			}
		    if (controller.getControlli(WALK_JUMP) && !controller.getControlli(JUMP_SHOOT) && !controller.getControlli(FALL)) {
				drawImage(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
			}
			
			if (controller.getControlli(JUMP_SHOOT)) {
				drawImage(batch, megaman, graphicLoader.getJumpShoot().getKeyFrame(elapsed), controller.getDirection());
			}
			
			if (controller.getControlli(FALL_SHOOT)) {
				drawImage(batch, megaman, graphicLoader.getFallShoot().getKeyFrame(fall), controller.getDirection());
				fall += Gdx.graphics.getDeltaTime();
			}
			
			if (controller.getControlli(IDLE)){
				drawImage(batch, megaman, graphicLoader.getIdle().getKeyFrame(elapsed, true), controller.getDirection());
			}
		}
	}
	
	public void drawBullet (SpriteBatch batch, Bullet bullet, boolean dir) {
		batch.draw(graphicLoader.getBullet(), bullet.getBody().getPosition().x * PPM - PPM / 2, bullet.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, dir, false);
		System.out.println("movimento in x "+bullet.getBody().getPosition().x);
	}
	public void drawImage (SpriteBatch batch, Megaman megaman, Texture texture, boolean dir) {
			batch.draw(texture, megaman.getBody().getPosition().x * PPM - PPM / 2, megaman.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, dir, false);
	}
	public void drawHud (SpriteBatch batch, Megaman megaman, HUD hud) {
			batch.draw(graphicLoader.getHud(hud.getLife()), gameManager.getCamera().position.x - gameManager.getLevelWidth()*7.5f, gameManager.getCamera().position.y + gameManager.getLevelHeight()*16);
			batch.draw(graphicLoader.getAnimatedHUD().getKeyFrame(elapsed, true), gameManager.getCamera().position.x - gameManager.getLevelWidth()*7.5f, gameManager.getCamera().position.y + gameManager.getLevelHeight()*16);
	}
	
	public void drawEnemy (SpriteBatch batch, Enemy axeBot) {
		batch.draw(graphicLoader.getAxebot().getKeyFrame(elapsed, true), axeBot.getBody().getPosition().x * PPM - PPM / 2, axeBot.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, false, false);
	}
	
	/*public void drawLevelStart (SpriteBatch batch) {
		batch.draw(graphicLoader.getStartText().getKeyFrame(elapsed), gameManager.getCamera().position.x - PPM*4, gameManager.getCamera().position.y);
	}
	*/
	
	public GraphicLoader getGL() {
		return graphicLoader;
	}
	
	public boolean getShootDone () {
		return shootDone;
	}
	
	public void resetFirstSpawn() {
		firstSpawn = true;
		start = false;
	}
	
	public boolean getFirstSpawn() {
		return firstSpawn;
	}
	
	public boolean getStart () {
		return start;
	}
	
	public void setFallAnimationZero () {
		fall = 0;
	}
}










