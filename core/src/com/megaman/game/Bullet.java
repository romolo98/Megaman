package com.megaman.game;

public class Bullet extends Entity {
	
	boolean direction;
	int speedBullet;
	
	public Bullet() {
		super();
		speedBullet = 7;
	}
	
	public void setDirection (boolean direction) {
		this.direction = direction;
	}
	public void physics () {
		if (!direction)
			this.setPositionX(this.getPositionX()+speedBullet);
		else
			this.setPositionX(this.getPositionX()-speedBullet);
	}
	
	public boolean getDirection() {
		return direction;
	}
}
