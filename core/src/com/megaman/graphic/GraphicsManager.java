package com.megaman.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megaman.logic.Boss;
import com.megaman.logic.Bullet;
import com.megaman.logic.Controller;
import com.megaman.logic.Enemy;
import com.megaman.logic.HUD;
import com.megaman.logic.Megaman;
import com.megaman.logic.gameManager;

import static com.megaman.game.Utils.Constants.*;
public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	float spawn;
	float fall;
	float elapsed;
	float bossElapsed;
	float salto;
	float caduta;
	float saltoMoto;
	float shoot;
	float levelStart;
	float bossPunch;
	float bossJump;
	float explosion;
	float dead;
	int cont;
	boolean destroyBullet;
	boolean firstSpawn;
	boolean spawnBullet;
	boolean shootDone;
	boolean start;
	boolean animationBossJumpDone;
	boolean animationBossPunchDone;
	boolean animationDeadDone;
	
	public GraphicsManager (){
		
		levelStart = 0;
		elapsed = 0;
		bossElapsed = 0;
		salto = 0;
		saltoMoto = 0;
		caduta = 0;
		shoot = 0;
		cont = 0;
		spawn = 0;
		bossJump = 0;
		bossPunch = 0;
		explosion = 0;
		dead = 0;
		firstSpawn = true;
		destroyBullet = false;
		spawnBullet = false;
		shootDone = false;
		start = false;
		animationBossJumpDone = false;
		animationBossPunchDone = false;
		animationDeadDone = false;
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
		
	}

	public void drawBossX(SpriteBatch batch, Boss boss) {
		bossElapsed += Gdx.graphics.getDeltaTime();
		
		if (boss.getBossAction(BOSS_WALK) && !boss.getBossAction(BOSS_PUNCH) && !boss.getBossAction(BOSS_JUMP)) {
			drawBoss(batch, boss, graphicLoader.getBossWalk().getKeyFrame(bossElapsed,true), boss.getDirection());
		}
		if (boss.getBossAction(BOSS_PUNCH) && !boss.getBossAction(BOSS_WALK) && !boss.getBossAction(BOSS_JUMP)) {
			drawBoss(batch, boss, graphicLoader.getBossPunch().getKeyFrame(bossPunch), boss.getDirection());
			bossPunch += Gdx.graphics.getDeltaTime();
			if (graphicLoader.getBossPunch().isAnimationFinished(bossPunch)) {
				animationBossPunchDone = true;
			}
		}
		if (boss.getBossAction(BOSS_JUMP) && !boss.getBossAction(BOSS_WALK) && !boss.getBossAction(BOSS_PUNCH)) {
			drawBoss(batch, boss, graphicLoader.getBossJump().getKeyFrame(bossJump), boss.getDirection());
			bossJump += Gdx.graphics.getDeltaTime();
			if (graphicLoader.getBossJump().isAnimationFinished(bossJump)) {
				animationBossJumpDone = true;
			}
		}
	}
	
	public void drawMegamanX(SpriteBatch batch, Controller controller, Megaman megaman) {
		elapsed += Gdx.graphics.getDeltaTime();
		shootDone = false;
		
		if (firstSpawn) {
			drawMegaman(batch, megaman, graphicLoader.getSpawn().getKeyFrame(spawn), controller.getDirection());
			spawn += Gdx.graphics.getDeltaTime();
			controller.setDirection(false);
			if (graphicLoader.getSpawn().isAnimationFinished(spawn)) {
				spawn = 0;
				firstSpawn = false;
				start = true;
			}
		}
		else if (megaman.isDead()) {
			drawMegaman(batch, megaman, graphicLoader.getDead().getKeyFrame(dead), controller.getDirection());
			dead += Gdx.graphics.getDeltaTime();
			if (graphicLoader.getDead().isAnimationFinished(dead)) {
				dead = 0;
				animationDeadDone = true;
			}
		}
		else {
			
			if (controller.getControlli(WALK_START)) {
				drawMegaman(batch, megaman, graphicLoader.getInizioWalk(), controller.getDirection());
			}
			if (controller.getControlli(SHOOT)){
				drawMegaman(batch, megaman, graphicLoader.getShoot().getKeyFrame(shoot), controller.getDirection());
				shoot += Gdx.graphics.getDeltaTime();
				if (graphicLoader.getShoot().isAnimationFinished(shoot)) {
					shoot = 0;
					shootDone = true;
				}
			}
			if (controller.getControlli(FALL)) {
				drawMegaman(batch, megaman, graphicLoader.getFall().getKeyFrame(fall), controller.getDirection());
				fall+= Gdx.graphics.getDeltaTime();
				if (graphicLoader.getFall().isAnimationFinished(fall)) {
					fall = 0;
				}
			}
			
			if (controller.getControlli(WALK) && !controller.getControlli(FALL) && !controller.getControlli(JUMP) && !controller.getControlli(JUMP_SHOOT) && !controller.getControlli(FALL_SHOOT)) {
				if (controller.getControlli(WALK_SHOOT)) {
					drawMegaman(batch, megaman, graphicLoader.getShooting().getKeyFrame(elapsed,true), controller.getDirection());
				}
				else if (controller.getControlli(WALK_JUMP)) {
					drawMegaman(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
				}
				
				else { 
					drawMegaman(batch, megaman, graphicLoader.getWalk().getKeyFrame(elapsed, true), controller.getDirection());
				}
			}
			if (controller.getControlli(JUMP) && !controller.getControlli(JUMP_SHOOT) && !controller.getControlli(FALL)) {
				drawMegaman(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
			}
		    if (controller.getControlli(WALK_JUMP) && !controller.getControlli(JUMP_SHOOT) && !controller.getControlli(FALL)) {
				drawMegaman(batch, megaman, graphicLoader.getJump().getKeyFrame(elapsed), controller.getDirection());
			}
			
			if (controller.getControlli(JUMP_SHOOT)) {
				drawMegaman(batch, megaman, graphicLoader.getJumpShoot().getKeyFrame(elapsed), controller.getDirection());
			}
			
			if (controller.getControlli(FALL_SHOOT)) {
				drawMegaman(batch, megaman, graphicLoader.getFallShoot().getKeyFrame(fall), controller.getDirection());
				fall += Gdx.graphics.getDeltaTime();
				if (graphicLoader.getFall().isAnimationFinished(fall)) {
					fall = 0;
				}
			}
			
			if (controller.getControlli(IDLE)){
				drawMegaman(batch, megaman, graphicLoader.getIdle().getKeyFrame(elapsed, true), controller.getDirection());
			}
		}
	}
	
	public void drawBullet (SpriteBatch batch, Bullet bullet, boolean dir) {
		batch.draw(graphicLoader.getBullet(), bullet.getBody().getPosition().x * PPM - PPM / 2, bullet.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, dir, false);
	}
	public void drawBossBullet (SpriteBatch batch, Bullet bullet, boolean dir) {
		if (!Boss.isDead)
			batch.draw(graphicLoader.getBossBullet(), bullet.getBody().getPosition().x * PPM - PPM / 2, bullet.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, dir, false);
		}
	
	public void drawEnemiesBullet (SpriteBatch batch, Bullet bullet, boolean dir, Enemy cannon) {
		if (!cannon.getIsDead())
			batch.draw(graphicLoader.getBossBullet(), bullet.getBody().getPosition().x * PPM - PPM / 2, bullet.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, dir, false);
		}
	
	public void drawBoss (SpriteBatch batch, Boss boss, Texture texture, boolean dir) {
		if (!boss.getIsDead()) {
		batch.draw(texture, boss.getBody().getPosition().x * PPM - 48, boss.getBody().getPosition().y * PPM - 48, 96, 96, 0, 0, 96, 96, dir, false);
		
		//HP BAR
		if (boss.getLife() != boss.getTotalLife()) {
			float healthValue = (float)boss.getTotalLife() / (float)boss.getLife();
			batch.draw(graphicLoader.getEmptyHP(), boss.getBody().getPosition().x * PPM - PPM / 3.15f, boss.getBody().getPosition().y * PPM - PPM / 4, 64, 64, 0, 0, 64, 64, false, false);
			batch.draw(graphicLoader.getFullHP(), boss.getBody().getPosition().x * PPM - PPM / 3.15f, boss.getBody().getPosition().y * PPM - PPM / 4, 64 / healthValue, 64, 0, 0, 64, 64, false, false);	
			}
		}
	}
	
	public void drawMegaman (SpriteBatch batch, Megaman megaman, Texture texture, boolean dir) {
			batch.draw(texture, megaman.getBody().getPosition().x * PPM - PPM / 2, megaman.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, dir, false);
	}
	public void drawHud (SpriteBatch batch, Megaman megaman, HUD hud) {
			batch.draw(graphicLoader.getHud(hud.getLife()), gameManager.getCamera().position.x - gameManager.getLevelWidth()*3.75f, gameManager.getCamera().position.y + gameManager.getLevelHeight()*16);
			batch.draw(graphicLoader.getAnimatedHUD().getKeyFrame(elapsed, true), gameManager.getCamera().position.x - gameManager.getLevelWidth()*3.75f, gameManager.getCamera().position.y + gameManager.getLevelHeight()*16);
			if (hud.getHealthBars() != 0)
			batch.draw(graphicLoader.getHealthBars(hud.getHealthBars()), gameManager.getCamera().position.x - gameManager.getLevelWidth()*3.75f, gameManager.getCamera().position.y + gameManager.getLevelHeight()*16);
	}
	
	public void drawExplosion (SpriteBatch batch, float posX, float posY) {
		batch.draw(graphicLoader.getExplosion().getKeyFrame(explosion), posX * PPM - PPM / 2, posY * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, false, false);
		explosion += Gdx.graphics.getDeltaTime();
		if (graphicLoader.getExplosion().isAnimationFinished(explosion)) {
			explosion = 0;
		}
	}
	
	public void drawEnemy (SpriteBatch batch, Enemy axeBot) {
		batch.draw(graphicLoader.getAxebot().getKeyFrame(elapsed, true), axeBot.getBody().getPosition().x * PPM - PPM / 2, axeBot.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, false, false);
		
		//HP BAR
		if (axeBot.getLife() != axeBot.getMaxHP()) {
		float healthValue = (float)axeBot.getMaxHP() / (float)axeBot.getLife();
		batch.draw(graphicLoader.getEmptyHP(), axeBot.getBody().getPosition().x * PPM - PPM / 2, axeBot.getBody().getPosition().y * PPM - PPM / 1.75f, 64, 64, 0, 0, 64, 64, false, false);
		batch.draw(graphicLoader.getFullHP(), axeBot.getBody().getPosition().x * PPM - PPM / 2 , axeBot.getBody().getPosition().y * PPM - PPM / 1.75f, 64 / healthValue, 64, 0, 0, 64, 64, false, false);	
		}
	
	}
	
	public void drawEnemies2 (SpriteBatch batch, Enemy cannons) {
		batch.draw(graphicLoader.getCannon().getKeyFrame(elapsed,true), cannons.getBody().getPosition().x * PPM - PPM / 2, cannons.getBody().getPosition().y * PPM - PPM / 2, 64, 64, 0, 0, 64, 64, cannons.getDirection(), false);
		
		
		if (cannons.getLife() != cannons.getMaxHP()) {
			float healthValue = (float)cannons.getMaxHP() / (float)cannons.getLife();
			batch.draw(graphicLoader.getEmptyHP(), cannons.getBody().getPosition().x * PPM - PPM / 2, cannons.getBody().getPosition().y * PPM - PPM / 1.75f, 64, 64, 0, 0, 64, 64, false, false);
			batch.draw(graphicLoader.getFullHP(), cannons.getBody().getPosition().x * PPM - PPM / 2 , cannons.getBody().getPosition().y * PPM - PPM / 1.75f, 64 / healthValue, 64, 0, 0, 64, 64, false, false);	
			}	
	}
	
	public void drawMenu (SpriteBatch batch, int contMenu) {
		batch.draw(graphicLoader.getMenu(),0, 0, gameManager.getCameraMenu().viewportWidth, gameManager.getCameraMenu().viewportHeight);
		if (contMenu == 0) {
			batch.draw(graphicLoader.getCursor(), gameManager.getCameraMenu().viewportWidth/60, gameManager.getCameraMenu().viewportHeight/2.5f, gameManager.getCameraMenu().viewportWidth/10, gameManager.getCameraMenu().viewportHeight/10);
		}
		else if (contMenu == 1) {
			batch.draw(graphicLoader.getCursor(), gameManager.getCameraMenu().viewportWidth/60, gameManager.getCameraMenu().viewportHeight/3.5f, gameManager.getCameraMenu().viewportWidth/10, gameManager.getCameraMenu().viewportHeight/10);
		}
		else if (contMenu == 2) {
			batch.draw(graphicLoader.getCursor(), gameManager.getCameraMenu().viewportWidth/60, gameManager.getCameraMenu().viewportHeight/6.5f, gameManager.getCameraMenu().viewportWidth/10, gameManager.getCameraMenu().viewportHeight/10);
		}
	}
	
	public void drawCredits (SpriteBatch batch) {
		batch.draw(graphicLoader.getCredits(), 0, 0, gameManager.getCameraMenu().viewportWidth, gameManager.getCameraMenu().viewportHeight);
	}
	
	public void drawWinningScreen (SpriteBatch batch) {
		batch.draw(graphicLoader.getGiocoCompleto(), gameManager.getCameraMenu().viewportWidth, gameManager.getCameraMenu().viewportHeight);
	}
	
	public boolean getAnimationBossJumpDone () {
		return animationBossJumpDone;
	}
	
	public boolean getAnimationBossPunchDone () {
		return animationBossPunchDone;
	}
	
	public void setAnimationBossPunchDoneFalse () {
		animationBossPunchDone = false;
	}
	
	public void setAnimationBossJumpDoneFalse () {
		animationBossJumpDone = false;
	}
	
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
	
	public void setBossJumpFrame () {
		bossJump = 0;
	}
	
	public void setBossPunchFrame () {
		bossPunch = 0;
	}
	
	public boolean getAnimationDeadDone () {
		return animationDeadDone;
	}
	
	public void setAnimationDeadDone () {
		animationDeadDone = false;
	}
	
	public float getExplosion () {
		return explosion;
	}
}









