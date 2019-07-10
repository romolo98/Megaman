package com.megaman.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	
	private Rectangle boundingBox;
	private float speed;
	
	public Entity () {
		boundingBox = new Rectangle();
	}
	
	float getSpeed() {
		return speed; 
	}
	
	float getPositionX () {
		return boundingBox.getX();
	}
	
	float getPositionY () {
		return boundingBox.getY();
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







