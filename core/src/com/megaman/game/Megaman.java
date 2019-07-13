package com.megaman.game;

import static com.megaman.game.Utils.Constants.PPM;;
public class Megaman extends Entity {
	
	public Megaman (){
		super();
		super.bodyCreator(gameManager.getSpawn().x/PPM, gameManager.getSpawn().y/PPM, PPM/2/PPM/2, PPM/2/PPM/2, false, 1.0f);
		setSpeed(3);
	}
	
	public float getSpeed() {
		return super.getSpeed()/PPM;
	}
	
	public float getPositionX () {
		return this.getBody().getLinearVelocity().x;
	}
	
	public float getPositionY () {
		return this.getBody().getLinearVelocity().y;
	}
	
	public void setPositionX (float x) {
		this.getBody().setLinearVelocity(x, getPositionY());
	}
	
	public void setPositionY (float y) {
		this.getBody().setLinearVelocity(getPositionX(), y);
	}
	
	public void respawn () {
		this.getBody().setTransform(gameManager.getSpawn().x/PPM, gameManager.getSpawn().y/PPM, 0);
	}
	
}







