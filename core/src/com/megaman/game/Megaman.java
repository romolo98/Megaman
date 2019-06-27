package com.megaman.game;

import com.badlogic.gdx.utils.Array;

public class Megaman extends Entity {
	
	Array<Bullet> ammo;
	int bulletCorrente;
	
	public Megaman (){
		super();
		setSpeed(3);
		ammo = new Array<Bullet>(50);
		bulletCorrente = 0;
		
		for (int i=0;i<50;i++) {
			ammo.add(new Bullet());
		}
	}
	
	public void reload (int index) {
		ammo.insert(index, new Bullet());
	}
	
	public void increaseBullet() {
		bulletCorrente++;
		if (bulletCorrente == 50) {
			bulletCorrente = 0;
			
		}
	}
	public int getBulletCorrente() {
		return bulletCorrente;
	}
	
	public Array<Bullet> getAmmo (){
		return ammo;
	}
	
	public Bullet getBullet (int index) {
		return ammo.get(index);
	}
	
	public void destroyBullet (int index) {
		ammo.removeIndex(index);
	}
}
