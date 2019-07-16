package com.megaman.game;

import static com.megaman.game.Utils.Constants.*;

public class Enemy extends Entity{

	public Enemy(int index) {
		super();
		super.bodyCreator(gameManager.getAxebotSpawn().get(index).x/PPM, gameManager.getAxebotSpawn().get(index).y/PPM, PPM/2/PPM/2, PPM/2/PPM/2, false, 200.0f);
		System.out.println(gameManager.getAxebotSpawn().get(index).x/PPM);
	}

}
