package com.megaman.game;

import static com.megaman.game.Utils.Constants.*;


public class Bullet extends Entity {
	
	boolean direction;
	int speedBullet;
	
	public Bullet(Megaman megaman) {
		super();
		super.sensorCreator(megaman.getBody().getPosition().x, megaman.getBody().getPosition().y, 0.25f, 0.25f, false);
		speedBullet = 7;
	}
	
	public void setDirection (boolean direction) {
		this.direction = direction;
	}
	public void physics () {
		if (!direction)
			this.getBody().setLinearVelocity(4, 0);
		else
			this.getBody().setLinearVelocity(-4, 0);
	}
	
	public float getPositionX() {
		return this.getBody().getPosition().x;
	}
	
	public float getPositionY() {
		return this.getBody().getPosition().y;
	}
	
	public void setPositionX (float x) {
		this.getBody().setTransform(x, this.getBody().getPosition().y, 0);
	}
	
	public void setPositionY (float y) {
		this.getBody().setTransform(this.getBody().getPosition().x, y, 0);
	}
	
	public boolean getDirection() {
		return direction;
	}
}
