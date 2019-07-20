package com.megaman.game;

import static com.megaman.game.Utils.Constants.*;

public class Enemy extends Entity{

	private int HP = 5;
	private int maxHP = 5;
	boolean isDead = false;
	boolean mustDie = false;
	
	public Enemy(int index) {
		super();
		super.bodyCreator(gameManager.getAxebotSpawn().get(index).x/PPM, gameManager.getAxebotSpawn().get(index).y/PPM, PPM/2/PPM/2, PPM/2/PPM/2, false, 200.0f);
	}
	
	public int getLife() {
		return HP;
	}
	
	public int getMaxHP () {
		return maxHP;
	}
	
	public void removeLife() {
		if (HP > 0) {
			HP--;
		}
		if (HP == 0) {
			mustDie = true;
		}
	}
	
	public boolean getIsDead () {
		return isDead;
	}
	
	public void setDeath() {
		mustDie = false;
		isDead = true;
	}
	
	public boolean getMustDie() {
		return mustDie;
	}
	
	public void setBodyNull() {
		this.entityBody = null;
	}
	
}
