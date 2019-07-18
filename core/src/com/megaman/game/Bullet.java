package com.megaman.game;


public class Bullet extends Entity {
	
	boolean direction;
	int speedMegamanBullet;
	int speedBossBullet;
	static int bull = 0;
	
	public Bullet(Boss boss) {
		super();
		super.sensorCreator(boss.getBody().getPosition().x, boss.getBody().getPosition().y, 0.10f, 0.10f, false);
		super.getBody().setGravityScale(0);
		super.getBody().setUserData("bossBullet");
		speedBossBullet = 1;
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
	
	public void physicsIA (Megaman megaman) {
		if (!direction)
			this.getBody().applyForce(speedBossBullet, megaman.getPositionY(), megaman.getPositionX(), megaman.getPositionY(), true);
		else
			this.getBody().applyForce(-speedBossBullet, megaman.getPositionY(), megaman.getPositionX(), megaman.getPositionY(), true);
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
}
