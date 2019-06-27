package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	float elapsed;
	float salto;
	float caduta;
	float saltoMoto;
	boolean destroyBullet;
	boolean spawnBullet;
	boolean[] shootThisBullet;
	
	public GraphicsManager (){
		elapsed = 0;
		salto = 0;
		saltoMoto = 0;
		caduta = 0;
		destroyBullet = false;
		spawnBullet = false;
		shootThisBullet = new boolean[50];
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
	}

	public void drawMegaman(SpriteBatch batch, Controller controller, Megaman megaman) {
		elapsed += Gdx.graphics.getDeltaTime();
		controller.muoviMegaman(megaman);
		batch.draw(graphicLoader.getBackground(), 0, -200);
		
		
		if (controller.controlli[controller.WALK_START]) {
			drawImage(batch, megaman, graphicLoader.getInizioWalk(), controller.getDirection());
			controller.setControlliFalse(controller.WALK_START);
		}
		else if (controller.getControlli(controller.SHOOT)){
			drawImage(batch, megaman, graphicLoader.getShoot().getKeyFrame(elapsed, true), controller.getDirection());
			spawnBullet = true;
			megaman.increaseBullet();
			controller.setControlliFalse(controller.SHOOT);
		}
		else if (controller.getControlli(controller.FALL)) {
			controller.setControlliFalse(controller.JUMP);
			drawImage(batch, megaman, graphicLoader.getFall().getKeyFrame(elapsed), controller.getDirection());
			caduta+= Gdx.graphics.getDeltaTime();
			if (graphicLoader.getFall().isAnimationFinished(caduta)) {
				caduta = 0;
			}
		}
		
		else if (controller.getControlli(controller.WALK_SHOOT)) {
			controller.setControlliFalse(controller.WALK);
			spawnBullet = true;
			drawImage(batch, megaman, graphicLoader.getShooting().getKeyFrame(elapsed,true), controller.getDirection());
			controller.setControlliFalse(controller.WALK_SHOOT);
		}

		else if (controller.getControlli(controller.WALK) && !controller.getControlli(controller.FALL)) {
			if (controller.getControlli(controller.WALK_JUMP)) {
				controller.setControlliFalse(controller.WALK);
				saltoMoto+= Gdx.graphics.getDeltaTime();
				drawImage(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
				if (graphicLoader.getJump().isAnimationFinished(saltoMoto)) {
					controller.setControlliFalse(controller.WALK_JUMP);
					saltoMoto = 0;
					controller.setFallTrue();
				}
			}
			
			else { 
					drawImage(batch, megaman, graphicLoader.getWalk().getKeyFrame(elapsed, true), controller.getDirection());
					controller.setControlliFalse(controller.WALK_JUMP);					
					controller.setControlliFalse(controller.WALK);
			}
		}
		else if (controller.getControlli(controller.JUMP)) {
			drawImage(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
			salto+= Gdx.graphics.getDeltaTime();
			if (graphicLoader.getJump().isAnimationFinished(salto)) {
				controller.setControlliFalse(controller.JUMP);
				salto = 0;
				controller.setFallTrue();
			}
		}
		else {
			controller.setTuttiControlliFalse();
			drawImage(batch, megaman, graphicLoader.getIdle().getKeyFrame(elapsed, true), controller.getDirection());
		}
	
		
	}
	public void drawBullet (SpriteBatch batch, Megaman megaman) {
		if (spawnBullet) {
			megaman.getBullet(megaman.getBulletCorrente()).setPositionX(megaman.getPositionX());
			megaman.getBullet(megaman.getBulletCorrente()).setPositionY(megaman.getPositionY()+10);
			spawnBullet = false;
			shootThisBullet[megaman.getBulletCorrente()] = true;
		}
		for (int i=0;i<megaman.getAmmo().size;i++) {
			if (shootThisBullet[i]) {
				batch.draw(graphicLoader.getBullet(), megaman.getBullet(i).getPositionX(), megaman.getBullet(i).getPositionY());
				megaman.getBullet(i).physics(true);
				//System.out.println("Position x " + megaman.getBullet(i).getPositionX() + " di bullet " + i );
				
			}
			if (megaman.getBullet(i).getPositionX() >= Gdx.graphics.getWidth()) {
				//System.out.println("mi distruggo " + i);
				shootThisBullet[i] = false;
				megaman.destroyBullet(i);
				megaman.reload(i);
			}
		}
	
	}
	
	public void drawImage (SpriteBatch batch, Megaman megaman, Texture texture, boolean dir) {
		batch.draw(texture, megaman.getPositionX(), megaman.getPositionY(), 64, 64, 0, 0, 64, 64, dir, false);
	}

}










