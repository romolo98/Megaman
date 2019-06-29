package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class gameManager {
	
	Controller controller;
	Megaman megaman;
	GraphicsManager gm;
	HUD hud;
	Array<Bullet> ammunition;
	int bulletCorrente;
	boolean[] shootThisBullet;
	
	public gameManager(GraphicsManager graphicManager) {
		ammunition = new Array<Bullet>(50);
		shootThisBullet = new boolean[50];
		controller = new Controller();
		megaman = new Megaman();
		gm = graphicManager;
		hud = new HUD();
		bulletCorrente = 0;
		for (int i=0;i<50;i++) {
			ammunition.add(new Bullet());
		}
	}
	public void run(SpriteBatch batch) {
		gm.drawMegaman(batch, controller, megaman);
		gm.drawHud(batch, megaman, hud);
		if (controller.getControlli(controller.SHOOT)) {
			ammunition.get(bulletCorrente).setDirection(controller.getDirection());
			if (ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getPositionX()-10);
			else if (!ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getPositionX()+10);
			ammunition.get(bulletCorrente).setPositionY(megaman.getPositionY());
			shootThisBullet[bulletCorrente] = true;
			increaseBullet();
			controller.setControlliFalse(controller.SHOOT);
		}
		for (int i=0;i<ammunition.size;i++) {
			if (shootThisBullet[i]) {
				gm.drawBullet(batch, ammunition.get(i),megaman, ammunition.get(i).getDirection());
				ammunition.get(i).physics();
			}
			if (ammunition.get(i).getPositionX()-ammunition.get(i).getSpeed() < -64 || ammunition.get(i).getPositionX()+ammunition.get(i).getSpeed() > 630) {
				System.out.println(ammunition.get(i).getPositionX());
				ammunition.removeIndex(i);
				addBullet(i);
				shootThisBullet[i] = false;
			}
		}
	}
	
	
	public void addBullet (int index) {
		ammunition.insert(index, new Bullet());
	}
	
	public void increaseBullet() {
		bulletCorrente++;
		if (bulletCorrente == 50) {
			bulletCorrente = 0;
			
		}
	}
}




















