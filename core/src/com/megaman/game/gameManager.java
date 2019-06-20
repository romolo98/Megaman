package com.megaman.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class gameManager {
	
	Bullet bullet;
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
	}
	
	public void run(SpriteBatch batch) {
		gm.drawMegaman(batch, controller, megaman);
		
		if (gm.bulletExistence) {
			ammo.add(new Bullet());
			gm.drawBullet(batch, ammo.peek(), megaman);	
		}
		
		if (gm.destroyBullet) {
			gm.destroyBullet = false;
			ammo.removeIndex(0);
		}
		


	}

}
