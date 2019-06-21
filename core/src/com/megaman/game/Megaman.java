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
		for (int i=0;i<10;i++) {
			ammo.add(new Bullet());
		}
	}
	
	public void increaseBullet() {
		bulletCorrente++;
		if (bulletCorrente == 10) {
			bulletCorrente = 0;
		}
	}
	
	public Bullet getBullet () {
		return ammo.get(bulletCorrente);
	}
	
	public void destroyBullet (int index) {
		ammo.removeIndex(index);
	}
}
