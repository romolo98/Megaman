package com.megaman.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	
	private Rectangle boundingBox;
	private int speed;
	
	public Entity () {
		boundingBox = new Rectangle();
	}
	
	int getSpeed() {
		return speed; 
	}
	
	int getPositionX () {
		return (int)boundingBox.getX();
	}
	
	int getPositionY () {
		return (int)boundingBox.getY();
	}
	
	int getWidth () {
		return (int)boundingBox.getWidth();
	}
	
	int getHeight () {
		return (int)boundingBox.getHeight();
	}
	
	void setSpeed (int x) {
		speed = x;
	}
	
	void setPositionX (float x) {
		boundingBox.setX(x);
	}
	
	void setPositionY (float y) {
		boundingBox.setY(y);
	}
	
	void setWidth (int width) {
		boundingBox.setWidth(width);
	}
	
	void setHeight (int height) {
		boundingBox.setHeight(height);
	}
}







