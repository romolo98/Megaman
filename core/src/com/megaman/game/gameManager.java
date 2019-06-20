package com.megaman.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class gameManager {
	
	Bullet bullet;
	int bulletCorrente;
	Array<Bullet> ammo;
	Controller controller;
	Megaman megaman;
	GraphicsManager gm;
	
	
	public gameManager() {
		controller = new Controller();
		megaman = new Megaman();
		gm = new GraphicsManager();
		bullet = new Bullet();
		ammo = new Array<Bullet>();
		ammo.add(new Bullet());
		bulletCorrente = 0;
	}
	public void run(SpriteBatch batch) {
		gm.drawMegaman(batch, controller, megaman);
		
		if (gm.updateBullet || gm.bulletShooting) {
			ammo.add(new Bullet());
			bulletCorrente = ammo.size-1;
		}
		if (!gm.destroyBullet)
			gm.drawBullet(batch, ammo.get(bulletCorrente), megaman);
		else {
			gm.destroyBullet = false;
			ammo.removeIndex(0);
		}
		


	}

}
