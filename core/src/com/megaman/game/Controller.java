package com.megaman.game;

import com.badlogic.gdx.Input;

public class Controller {
	
	public Controller () {
	}

	public void muoviMegaman (int keycode,Megaman megaman) {
		if (keycode == Input.Keys.SPACE) {
			megaman.setPositionY(megaman.getPositionY()+2);
		}
		else {
			megaman.setPositionY(megaman.getPositionY()-2);
		}
	}
}
