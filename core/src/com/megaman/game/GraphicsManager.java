package com.megaman.game;

import java.awt.DisplayMode;

import javax.management.monitor.Monitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	float elapsed;
	float salto;
	boolean bulletShooting;
	boolean destroyBullet;
	boolean updateBullet;
	
	public GraphicsManager (){
		elapsed = 0;
		salto = 0;
		bulletShooting = false;
		destroyBullet = false;
		updateBullet = false;
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
	}

	public void drawMegaman(SpriteBatch batch, Controller controller, Megaman megaman) {
		elapsed += Gdx.graphics.getDeltaTime();
		controller.muoviMegaman(megaman);
		
		batch.draw(graphicLoader.getBackground(), 0, -200);
		
		
		if (controller.controlli[controller.WALK_START]) {
			batch.draw(graphicLoader.getInizioWalk(),megaman.getPositionX(),megaman.getPositionY());
			controller.setControlliFalse(controller.WALK_START);
		}
		else if (controller.getControlli(controller.SHOOT)){
			batch.draw(graphicLoader.getShoot().getKeyFrame(elapsed,true), megaman.getPositionX(), megaman.getPositionY());
			bulletShooting = true;
			controller.setControlliFalse(controller.SHOOT);
		}
		else if (controller.getControlli(controller.FALL)) {
			batch.draw(graphicLoader.getFall().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
		}
		
		else if (controller.getControlli(controller.WALK_SHOOT)) {
			controller.setControlliFalse(controller.WALK);
			bulletShooting = true;
			batch.draw(graphicLoader.getShooting().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
			controller.setControlliFalse(controller.WALK_SHOOT);
		}
		
		else if (controller.getControlli(controller.WALK) && !controller.getControlli(controller.FALL)) {
			if (controller.getControlli(controller.WALK_JUMP)) {
				controller.setControlliFalse(controller.WALK);
				salto+= Gdx.graphics.getDeltaTime();
				batch.draw(graphicLoader.getJump().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
				if (graphicLoader.getJump().isAnimationFinished(salto)) {
					controller.setControlliFalse(controller.WALK_JUMP);
					salto = 0;
					controller.setFallTrue();
				}
			}
			
			else if (!controller.getControlli(controller.FALL)){ 
					batch.draw(graphicLoader.getWalk().getKeyFrame(elapsed, true), megaman.getPositionX(),megaman.getPositionY());
					controller.setControlliFalse(controller.WALK_JUMP);					
					controller.setControlliFalse(controller.WALK);
			}
		}
		else if (controller.getControlli(controller.JUMP)) {
			batch.draw(graphicLoader.getJump().getKeyFrame(elapsed), megaman.getPositionX(), megaman.getPositionY());
			salto+= Gdx.graphics.getDeltaTime();
			if (graphicLoader.getJump().isAnimationFinished(salto)) {
				controller.setControlliFalse(controller.JUMP);
				salto = 0;
				controller.setFallTrue();
			}
		}
		else {
			controller.setTuttiControlliFalse();
			batch.draw(graphicLoader.getIdle().getKeyFrame(elapsed/2,true), megaman.getPositionX(), megaman.getPositionY());
		}
	
		
	}
	public void drawBullet (SpriteBatch batch, Bullet bullet, Megaman megaman) {
		if (bulletShooting) {
			bullet.setPositionX(megaman.getPositionX()+10);
			bullet.setPositionY(megaman.getPositionY());
			updateBullet = true;
			bulletShooting = false;
		}
		
		bullet.physics(updateBullet);
		batch.draw(graphicLoader.getBullet(), bullet.getPositionX(), bullet.getPositionY());
		
		if (bullet.getPositionX() > Gdx.graphics.getWidth()) {
			destroyBullet = true;
			updateBullet = false;
		}
		
	}

}










