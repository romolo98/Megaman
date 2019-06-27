package com.megaman.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class gameManager {
	
	Controller controller;
	Megaman megaman;
	GraphicsManager gm;
	
	
	public gameManager() {
		controller = new Controller();
		megaman = new Megaman();
		gm = new GraphicsManager();
	}
	public void run(SpriteBatch batch) {
		gm.drawMegaman(batch, controller, megaman);
		gm.drawBullet(batch, megaman);
	}
}




















