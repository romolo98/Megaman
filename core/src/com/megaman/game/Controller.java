package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public class Controller {
	
	public static final int WALK = 0;
	public static final int JUMP = 1;
	public static final int FALL = 2;
	public static final int SHOOT = 3;
	public static final int WALK_SHOOT = 4;
	public static final int WALK_JUMP = 5;
	public static final int WALK_START = 6;
	
	boolean[] controlli;
	int speed;
	int contJump;
	
	public Controller () {
		controlli = new boolean[7];
		speed = 2;
		contJump = 0;
	}

	public void muoviMegaman (Megaman megaman) {
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			megaman.setPositionX(megaman.getPositionX()+1);
			controlli[WALK_START] = true;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				megaman.setPositionX(megaman.getPositionX()+speed);
				megaman.setPositionY(megaman.getPositionY()+speed);
				controlli[WALK_JUMP] = true;
			}
			else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
				megaman.setPositionX(megaman.getPositionX()+speed);
				controlli[WALK_SHOOT] = true;
			}
			else {
				megaman.setPositionX(megaman.getPositionX()+speed);
				controlli[WALK] = true;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			megaman.setPositionX(megaman.getPositionX()-1);
			controlli[WALK_START] = true;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				megaman.setPositionX(megaman.getPositionX()-speed);
				megaman.setPositionY(megaman.getPositionY()-speed);
				controlli[WALK_JUMP] = true;
			}
			else {
				megaman.setPositionX(megaman.getPositionX()-speed);
				controlli[WALK] = true;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			controlli[JUMP] = true;
		}
		
		if (controlli[JUMP]) {
			megaman.setPositionY(megaman.getPositionY()+speed);
		}
			
		if (controlli[FALL]) {
			controlli[JUMP] = false;
			megaman.setPositionY(megaman.getPositionY()-speed);
			if (megaman.getPositionY() < 0) {
				controlli[FALL] = false;
			}
		}

	}
	
	void setTuttiControlliFalse () {
		for (int i=0;i<6;i++)
		controlli[i] = false;
	}
	void setControlliFalse (int index) {
		controlli[index] = false;
	}
	
	boolean getControlli (int index) {
		return controlli[index];
	}
	
	void setFallTrue () {
		controlli[FALL] = true;
	}
	
}
