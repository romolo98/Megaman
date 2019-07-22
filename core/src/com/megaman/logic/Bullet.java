package com.megaman.logic;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
	
	Vector2 vectorMegaman;
	boolean isShot;
	boolean direction;
	int speedMegamanBullet;
	int speedBossBullet;
	private boolean isDead = false;
	private boolean mustDie = false;
	static int bulletType1 = 0;
	static int bulletType2 = 0;
	private int existenceTime = 5;
	
	public Bullet(Boss boss) {
		super();
		if (!boss.getDirection())
			super.sensorCreator(boss.getBody().getPosition().x+0.35f, boss.getBody().getPosition().y+0.45f, 0.10f, 0.10f, false);
		else 
			super.sensorCreator(boss.getBody().getPosition().x-0.35f, boss.getBody().getPosition().y+0.45f, 0.10f, 0.10f, false);
		super.getBody().setGravityScale(0);
		super.getBody().setUserData("bossBullet" + bulletType2);
		speedBossBullet = 7;
		isShot = false;
		vectorMegaman = new Vector2();
		bulletType2++;
	}
	
	public Bullet(Megaman megaman) {
		super();
		super.sensorCreator(megaman.getBody().getPosition().x, megaman.getBody().getPosition().y, 0.15f, 0.15f, false);
		super.getBody().setGravityScale(0);
		super.getBody().setUserData("bullet"+ bulletType1);
		speedMegamanBullet = 7;
		bulletType1++;
	}
	
	public void setDirection (boolean direction) {
		this.direction = direction;
	}
	public void physics () {
		if (!direction)
			this.getBody().setLinearVelocity(speedMegamanBullet, 0);
		else
			this.getBody().setLinearVelocity(-speedMegamanBullet, 0);
	}
	
	public void physicsIA (Megaman megaman,Boss boss) {
		vectorMegaman.set(megaman.getBody().getPosition().x, megaman.getBody().getPosition().y);
		if (!direction) {
			if (!isShot)
				this.getBody().setLinearVelocity(this.getBody().getLocalPoint(vectorMegaman));                                      
		}
		else {
			if (!isShot)
				this.getBody().setLinearVelocity(this.getBody().getLocalPoint(vectorMegaman));
		}
	}
	
	public float getPositionX() {
		return this.getBody().getPosition().x;
	}
	
	public float getPositionY() {
		return this.getBody().getPosition().y;
	}
	
	public boolean getDirection() {
		return direction;
	}
	
	public void setShoot (boolean ok) {
		isShot = ok;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public boolean getMustDie () {
		return mustDie;
	}
	
	public void setMustDie() {
		mustDie = true;
	}
	
	public void setDeath() {
		mustDie = false;
		isDead = true;
	}
	
	
	public void setBodyNull() {
		this.entityBody.setUserData(null);
		this.entityBody = null;
	}
	
	public int getExistenceTime () {
		return existenceTime;
	}
	
	public void decreaseExistenceTime() {
		existenceTime--;
	}
}
