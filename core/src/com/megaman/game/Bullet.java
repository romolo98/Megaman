package com.megaman.game;


public class Bullet extends Entity {
	
	boolean direction;
	int speedBullet;
	static int bull = 0;
	
	public Bullet(Megaman megaman) {
		super();
		super.sensorCreator(megaman.getBody().getPosition().x, megaman.getBody().getPosition().y, 0.15f, 0.15f, false);
		super.getBody().setGravityScale(0);
		super.getBody().setUserData("bullet"+ bull);
		speedBullet = 7;
		bull++;
	}
	
	public void setDirection (boolean direction) {
		this.direction = direction;
	}
	public void physics () {
		if (!direction)
			this.getBody().setLinearVelocity(speedBullet, 0);
		else
			this.getBody().setLinearVelocity(-speedBullet, 0);
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
