package com.megaman.game;

import static com.megaman.game.Utils.Constants.PPM;

public class Enemy extends Entity{

	public Enemy(int index) {
		super();
		super.bodyCreator(gameManager.getEnemySpawn().get(index).x/PPM, gameManager.getEnemySpawn().get(index).y/PPM, PPM/2/PPM/2, PPM/2/PPM/2, false, 200.0f);
		System.out.println(gameManager.getEnemySpawn().get(index).x/PPM);
	}

}
