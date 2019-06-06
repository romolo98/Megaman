package com.megaman.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	
	private Rectangle boundingBox;
	
	public Entity () {
		boundingBox = new Rectangle();
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
	
	void setPositionX (int x) {
		boundingBox.setX(x);
	}
	
	void setPositionY (int y) {
		boundingBox.setY(y);
	}
	
	void setWidth (int width) {
		boundingBox.setWidth(width);
	}
	
	void setHeight (int height) {
		boundingBox.setHeight(height);
	}
}







