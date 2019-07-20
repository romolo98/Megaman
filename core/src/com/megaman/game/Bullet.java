package com.megaman.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
	
	Vector2 vectorBoss;
	Vector2 vectorMegaman;
	boolean isShot;
	boolean direction;
	int speedMegamanBullet;
	int speedBossBullet;
	static int bull = 0;
	
	public Bullet(Boss boss) {
		super();
		if (!boss.getDirection())
			super.sensorCreator(boss.getBody().getPosition().x+0.35f, boss.getBody().getPosition().y+0.45f, 0.10f, 0.10f, false);
		else 
			super.sensorCreator(boss.getBody().getPosition().x-0.35f, boss.getBody().getPosition().y+0.45f, 0.10f, 0.10f, false);
		super.getBody().setGravityScale(0);
		super.getBody().setUserData("bossBullet");
		speedBossBullet = 7;
		isShot = false;
		vectorBoss = new Vector2();
		vectorMegaman = new Vector2();
	}
	
	public Bullet(Megaman megaman) {
		super();
		super.sensorCreator(megaman.getBody().getPosition().x, megaman.getBody().getPosition().y, 0.15f, 0.15f, false);
		super.getBody().setGravityScale(0);
		super.getBody().setUserData("bullet"+ bull);
		speedMegamanBullet = 7;
		bull++;
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
}
