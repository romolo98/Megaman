package com.megaman.game;

import com.badlogic.gdx.utils.Array;

public class Megaman extends Entity {
	
	Array<Bullet> ammo;
	int bulletCorrente;
	
	public Megaman (){
		super();
		setSpeed(3);
		ammo = new Array<Bullet>(10);
		bulletCorrente = 0;
	}
	
	public void reload () {
		ammo.add(new Bullet());
	}
	
	public void increaseBullet() {
		bulletCorrente++;
		if (bulletCorrente == 10) {
			bulletCorrente = 0;
		}
	}
	
	public Bullet getBullet (int index) {
		return ammo.get(index);
	}
	
	public void destroyBullet (int index) {
		ammo.removeIndex(index);
	}
	
	public void bulletSpawn () {
		for (int i = 0; i < 10; i++) {
			ammo.get(i).setPositionX(this.getPositionX()+20);
		}
	}
}
