package com.megaman.game;

import com.badlogic.gdx.utils.Array;
import static com.megaman.game.Utils.Constants.*;

public class Boss extends Entity{
	
	private boolean[] bossAction;
	private float forceX;
	private float forceY;
	private boolean direction;
	private int life;
	private int totalLife;
	private int bulletDamage;
	private int punchDamage;
	private Array<Bullet> bullets;
	private Array<Bullet> bulletsToDestroy;
	private double lastTimeShoot;
	private double lastTimeJump;
	private int delayShoot;
	private int delayJump;
	private double actualTime;
	private static boolean mustDie = false;
	public static boolean isDead = false;
	
	public Boss () {
		super();
		super.bodyCreator(gameManager.getBossSpawn().x/PPM, gameManager.getBossSpawn().y/PPM, 0.40f, 0.60f, false, 200f);
		super.getBody().setUserData("boss");
		forceX = 0;
		forceY = 0;
		life = 50;
		totalLife = 50;
		lastTimeShoot = 0;
		lastTimeJump = 0;
		actualTime = 0;
		delayShoot = 1500;
		delayJump = 100000000;
		bulletDamage = 1;
		punchDamage = 2;
		direction = true;
		bullets = new Array<Bullet>();
		bulletsToDestroy = new Array<Bullet>();
		bossAction = new boolean[3];
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public int getTotalLife () {
		return totalLife;
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public int getPunchDamage() {
		return punchDamage;
	}
	
	public void removeLife() {
		if (life > 0)
		life--;
		if (life == 0) {
		mustDie = true;
		}
	}

	public void shoot () {
		
		bullets.add(new Bullet(this));
		bullets.peek().setDirection(direction);
		
		lastTimeShoot = System.currentTimeMillis();
	}
	
	public Array<Bullet> getBossBullets () {
		return bullets;
	}

	public void bossIA (Megaman megaman) {
		if (!isDead) {			
			
			actualTime = System.currentTimeMillis();
			forceX = 0;
			forceY = this.getBody().getLinearVelocity().y;
			
			if (megaman.getBody().getPosition().x > this.getBody().getPosition().x) {
				if ((megaman.getBody().getPosition().x - this.getBody().getPosition().x) < 13) {
					this.getBody().setActive(true);
					forceX += 1.5f;
					
					if (actualTime > delayShoot + lastTimeShoot) {
						shoot();
					}
					if (!isBossFalling() && actualTime > delayJump + lastTimeJump && !bossAction[BOSS_PUNCH]) {
						bossAction[BOSS_WALK] = false;
						bossAction[BOSS_JUMP] = true;
						lastTimeJump = System.currentTimeMillis();
						forceY += 7;
					}
					if ((megaman.getBody().getPosition().x - this.getBody().getPosition().x) < 1 && !bossAction[BOSS_JUMP]) {
						bossAction[BOSS_WALK] = false;
						bossAction[BOSS_PUNCH] = true;
					}
				}
				direction = false;
			}
			else {
				if ((this.getBody().getPosition().x - megaman.getBody().getPosition().x) < 13) {
					this.getBody().setActive(true);
					forceX += 1.5f;
					if (actualTime > delayShoot + lastTimeShoot) {
						shoot();
					}
					if (!isBossFalling() && actualTime > delayJump + lastTimeJump && !bossAction[BOSS_PUNCH]) {
						bossAction[BOSS_WALK] = false;
						bossAction[BOSS_JUMP] = true;
						lastTimeJump = System.currentTimeMillis();
						forceY += 7;
					}
					if ((this.getBody().getPosition().x - megaman.getBody().getPosition().x) < 1 && !bossAction[BOSS_JUMP]) {
						bossAction[BOSS_WALK] = false;
						bossAction[BOSS_PUNCH] = true;
					}
				}
				direction = true;
			}
			
			if (!bossAction[BOSS_JUMP] && !bossAction[BOSS_PUNCH]) {
				bossAction[BOSS_WALK] = true;
			}
			
			//MOVEMENTS
			if (!direction) {
				if ((megaman.getBody().getPosition().x - this.getBody().getPosition().x) > 4)
					this.getBody().setLinearVelocity(forceX+2,forceY);
				else
					this.getBody().setLinearVelocity(forceX,forceY);
			}
			else {
				if ((this.getBody().getPosition().x - megaman.getBody().getPosition().x) > 4)
					this.getBody().setLinearVelocity(-forceX-2,forceY);
				else
					this.getBody().setLinearVelocity(-forceX,forceY);
			}
		}
	}
	
	public boolean isBossFalling () {
		if (this.getBody().getLinearVelocity().y == 0) {
			return false;
		}
		return true;
	}
	
	public void setBossActionFalse (int index) {
		bossAction[index] = false;
	}
	
	public boolean getBossAction (int index) {
		return bossAction[index];
	}
	
	public boolean getDirection () {
		return direction;
	}
	
	public void addBulletsToDestroy (Bullet bullet) {
		bulletsToDestroy.add(bullet);
	}
	
	public Array<Bullet> getBulletsToDestroy(){
		return bulletsToDestroy;
	}
	
	public void clearBulletsToDestroy() {
		bulletsToDestroy.clear();
	}
	
	public static boolean getMustDie() {
		return mustDie;
	}
	
	public static void setDeath() {
		mustDie = false;
		isDead = true;
	}
	
	public static boolean getIsDead() {
		return isDead;
	}
	
	public void setBodyNull () {
		this.entityBody = null;
	}
}