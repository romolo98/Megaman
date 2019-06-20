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
	int contJump;
	
	public Controller () {
		controlli = new boolean[7];
		contJump = 0;
	}

	public void muoviMegaman (Megaman megaman) {
		
		//RIGHT
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && !controlli[JUMP]) {
			megaman.setPositionX(megaman.getPositionX()+megaman.getSpeed());
			controlli[WALK_START] = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			megaman.setPositionX(megaman.getPositionX()+megaman.getSpeed());

			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				megaman.setPositionY(megaman.getPositionY()+megaman.getSpeed());
				controlli[WALK_JUMP] = true;
			}
			else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
				controlli[WALK_SHOOT] = true;
			}
			else {
				controlli[WALK] = true;
			}
		}
		
		//LEFT
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			megaman.setPositionX(megaman.getPositionX()-megaman.getSpeed());
			controlli[WALK_START] = true;
			}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			megaman.setPositionX(megaman.getPositionX()-megaman.getSpeed());
					
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				megaman.setPositionY(megaman.getPositionY()+megaman.getSpeed());
				controlli[WALK_JUMP] = true;
				}
			else {
				controlli[WALK] = true;
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			controlli[SHOOT] = true;
		}
		
		
		if (controlli[FALL]) {
			
			controlli[JUMP] = false;
			controlli[WALK_JUMP] = false;
			
			megaman.setPositionY(megaman.getPositionY()-megaman.getSpeed());
			if (megaman.getPositionY() <= 0) {
				controlli[FALL] = false;
			}
		}
		
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			controlli[JUMP] = true;
			
		}
		
		if (controlli[JUMP] && !controlli[FALL]) {
			setFalseExcept(JUMP);
			
			megaman.setPositionY(megaman.getPositionY()+megaman.getSpeed());
		}

	}
	
	void setTuttiControlliFalse () {
		for (int i=0;i<6;i++)
		controlli[i] = false;
	}
	void setControlliFalse (int index) {
		controlli[index] = false;
	}
	
	void setFalseExcept (int index) {
		for (int i = 0; i < 6; i++)
			if (i != index)
			controlli[i] = false;
	}
	
	boolean getControlli (int index) {
		return controlli[index];
	}
	
	void setFallTrue () {
		controlli[FALL] = true;
	}
	
}
