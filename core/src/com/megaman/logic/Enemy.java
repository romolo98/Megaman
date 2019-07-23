package com.megaman.logic;

import static com.megaman.game.Utils.Constants.*;

import com.badlogic.gdx.utils.Array;

public class Enemy extends Entity{

	
	private Array<Bullet> bullets;
	private Array<Bullet> bulletsToDestroy;
	private int HP = 5;
	private int maxHP = 5;
	private int damage = 2;
	boolean isDead = false;
	boolean mustDie = false;
	boolean hasExploded = false;
	boolean direction;
	
	public Enemy(int index) {
		super();
		super.bodyCreator(gameManager.getAxebotSpawn().get(index).x/PPM, gameManager.getAxebotSpawn().get(index).y/PPM, PPM/2/PPM/2, PPM/2/PPM/2, false, 200.0f);
	}
	
	public Enemy(float index) {
		super();
		super.bodyCreator(gameManager.getCannonsSpawn().get((int) index).x/PPM, gameManager.getCannonsSpawn().get((int) index).y/PPM, 0.40f, PPM/2/PPM/2, false, 200.0f);
		direction = false;
		bullets = new Array<Bullet>();
		bulletsToDestroy = new Array<Bullet>();
	}
	
	public Array<Bullet> getBulletsToDestroy(){
		return bulletsToDestroy;
	}
	
	public Array<Bullet> getEnemiesBullets () {
		return bullets;
	}
	
	public void clearBulletsToDestroy() {
		bulletsToDestroy.clear();
	}
	
	public void addBulletsToDestroy (Bullet bullet) {
		bulletsToDestroy.add(bullet);
	}
	
	public void shoot () {
		bullets.add(new Bullet(this));
		bullets.peek().setDirection(direction);
	}
	
	public int getDamage () {
		return damage;
	}
	
	public int getLife() {
		return HP;
	}
	
	public int getMaxHP () {
		return maxHP;
	}
	
	public void removeLife() {
		if (HP > 0) {
			HP--;
		}
		if (HP == 0) {
			mustDie = true;
		}
	}
	
	public boolean getIsDead () {
		return isDead;
	}
	
	public void setDeath() {
		mustDie = false;
		isDead = true;
	}
	
	public boolean getMustDie() {
		return mustDie;
	}
	
	public void setBodyNull() {
		this.entityBody = null;
	}
	
	public boolean getExplosionState() {
		return hasExploded;
	}
	
	public void setHasExploded () {
		hasExploded = true;
	}
	
	public void setDirection (boolean ok) {
		direction = ok;
	}
	
	public boolean getDirection () {
		return direction;
	}
	
}
